package fr.rhaz.minecraft

import fr.rhaz.minecraft.kotlin.*
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

class UltimateGUI: BukkitPlugin(){

    override fun onEnable() {
        update(61895, ChatColor.AQUA)
        //listen(callback = blockbreak)
    }

    var blockbreak = fun(e: BlockBreakEvent) {
        gui(e.player) {
            item(1,1){
                type = e.block.type
                amount = 3
                name = "Test"
                enchants[Enchantment.ARROW_FIRE] = 1
                onclick = { player ->
                    type = Material.ANVIL
                    name = "It works!"
                    isCancelled = true
                    schedule(async = true, period = 10){
                        move(Direction.down)
                        refresh()
                        if(y == 1) cancel()
                    }
                }
            }
            item(2,3){
                type = Material.APPLE
                name = "&b&lGive me an apple!"
                lore += "&6This will give you an apple"
                onclick = {
                    it.inventory.addItem(ItemStack(Material.APPLE))
                    isCancelled = true
                    refresh()
                }
            }
            item(2,1){
                type = Material.COMMAND_CHAIN
                onclick = bricks
                move(9, 6)
            }
        }
    }

    val text = """
    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
    Suspendisse fermentum accumsan sapien vitae ultrices.
    Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.
    """.trimIndent()

    var bricks = fun InventoryClickEvent.(player: Player){
        gui(player){
            background{
                type = Material.BRICK
                onclick = { isCancelled = true }
                enchants[Enchantment.KNOCKBACK] = 3
            }
            item(5, 3){
                type = Material.BED
                onclick = {
                    isCancelled = true
                    it.execute("sethome")
                }
            }
        }
    }
}