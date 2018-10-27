package fr.rhaz.minecraft

import fr.rhaz.minecraft.Direction.*
import fr.rhaz.minecraft.kotlin.BukkitPlugin
import fr.rhaz.minecraft.kotlin.listen
import org.bukkit.Material
import org.bukkit.Material.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventPriority
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.collections.set
import kotlin.reflect.KProperty


enum class Direction{ up, down, left, right }

@JvmOverloads
fun BukkitPlugin.gui(
    player: Player? = null,
    name: String = "Menu",
    rows: Int = 6,
    builder: GUI.() -> Unit
) = GUI(this, name, rows).apply(builder).apply{player?.let(::open)}

class GUI(
    val plugin: BukkitPlugin,
    var title: String,
    var rows: Int
){

    init{ regen() }
    lateinit var inventory: Inventory private set
    fun regen(){
        inventory = plugin.server.createInventory(null, rows * 9, title.replace("&", "§"))
    }

    fun refresh() = inventory.apply {
        clear()
        items.forEach { k, v -> setItem(k, v.item) }
    }

    init{
        plugin.listen<InventoryClickEvent>(EventPriority.LOWEST){
            if(it.clickedInventory != this@GUI.inventory) return@listen
            val player = it.whoClicked as? Player ?: return@listen
            val item = items[it.slot] ?: return@listen
            item.onclick(it, player)
        }
    }

    fun open(player: Player) {
        refresh()
        player.openInventory(inventory)
    }

    val items = mutableMapOf<Int, Item>()

    inner class Item{
        var slot: Int
            get() = items.keys.first{items[it]==this}
            set(value) {
                items.remove(slot)
                items[value] = this
            }
        var x: Int
            get() = unslot(slot).first
            set(value){ slot = slot(value, y) }
        var y: Int
            get() = unslot(slot).second
            set(value) { slot = slot(x, value) }

        fun move(direction: Direction) = move(this.x, this.y, direction)
        fun move(x: Int, y: Int) = move(this.x, this.y, x, y)

        var type = AIR
        var amount = 1
        var name: String? = null
        val lore = mutableListOf<String>()
        val enchants = mutableMapOf<Enchantment, Int>()
        var onclick = fun InventoryClickEvent.(player: Player){}
        val item get() =
            ItemStack(type, amount).apply {
                val meta = itemMeta
                meta.displayName = name?.replace("&","§")
                meta.lore = lore.map{it.replace("&","§")}
                enchants.forEach { e, l -> meta.addEnchant(e, l, true) }
                itemMeta = meta
            }
    }

    fun slot(x: Int, y: Int) = (x-1)+(y-1)*9
    fun unslot(s: Int) = Pair((s-(s/9)*9)+1, (s/9)+1)

    fun item(x: Int, y: Int) = items[slot(x,y)]
    fun item(x: Int, y: Int, builder: GUI.Item.() -> Unit)
            = Item().apply {
        items[slot(x,y)] = this
        builder(); refresh()
    }

    fun fill(
        x1: Int, y1: Int, x2: Int, y2: Int,
        builder: GUI.Item.() -> Unit
    ){
        for(x in x1..x2) for(y in y1..y2)
            item(x, y, builder)
    }

    fun background(builder: GUI.Item.() -> Unit) = fill(1,1, 9,rows, builder)

    fun move(x1: Int, y1: Int, x2: Int, y2: Int){
        items[slot(x2, y2)] = items.remove(slot(x1, y1)) ?: return
    }

    fun move(x: Int, y: Int, direction: Direction) =
        when(direction){
            up -> move(x, y, x, if(y == 1) rows else y-1)
            down -> move(x, y, x, if(y == rows) 1 else y+1)
            left -> move(x, y, if(x == 1) 9 else x-1, y)
            right -> move(x, y, if(x == 9) 1 else x+1, y)
        }

    @JvmOverloads
    fun exit(builder: Item.() -> Unit = {}) = item(9, 1){
        type = WOODEN_DOOR
        name = "&cExit"
        onclick = {it.closeInventory()}
    }.apply(builder)

    @JvmOverloads
    fun back(gui: GUI, builder: Item.() -> Unit = {}) = item(8, 1){
        type = ARROW
        name = "&cBack"
        onclick = {
            isCancelled = true
            gui.open(it)
        }
    }

    @JvmOverloads
    fun yes(action: (Player) -> Unit, builder: Item.() -> Unit = {}){
        fill(2,3, 3,4){
            type = CONCRETE
            onclick = {
                isCancelled = true
                action(it)
            }
            apply(builder)
        }
    }

    @JvmOverloads
    fun no(action: (Player) -> Unit, builder: Item.() -> Unit = {}){
        fill(7,3, 8,4){
            type = CONCRETE
            onclick = {
                isCancelled = true
                action(it)
            }
            apply(builder)
        }
    }

    val cancelled: InventoryClickEvent.(Player) -> Unit = {isCancelled = true}

    @JvmOverloads
    fun info(text: String, builder: Item.() -> Unit = {}){
        item(5,2){
            type = SIGN
            onclick = cancelled
            name = "&bInfo"
            lore += wrap(text)
            apply(builder)
        }
    }
}

@JvmOverloads
fun wrap(text: String, max: Int = 32)
    = text.split("\n").flatMap{
        mutableListOf<String>().apply {
            val words = it.split(" ")
            var line = words[0]
            for(word in words.drop(1)){
                val pre = "$line $word"
                if(pre.length > max){
                    this += line
                    line = word
                } else line = pre
            }
            this += line
        }
    }.toMutableList()