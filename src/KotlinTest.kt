package fr.rhaz.minecraft

import fr.rhaz.minecraft.kotlin.*
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitTask

class UltimateGUI: BukkitPlugin(){

    override fun onEnable() {
        listen(callback = blockbreak)
    }

    var blockbreak = fun(e: BlockBreakEvent) {
        gui(e.player) {
            item(1,1){
                type = e.block.type
                amount = 3
                name = "Test"
                enchants[Enchantment.ARROW_FIRE] = 1
                onclick = {
                    type = Material.ANVIL
                    name = "It works!"
                    isCancelled = true
                    lateinit var task: BukkitTask
                    task = schedule(async = true, period = 10){
                        move(Direction.down)
                        refresh()
                        if(y == 1) task.cancel()
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
            }
        }
    }

    var bricks = fun InventoryClickEvent.(player: Player){
        gui(player){
            background{
                type = Material.BRICK
                onclick = { isCancelled = true }
            }
            item(5, 3){
                type = Material.BED
                onclick = {
                    it.execute("sethome")
                }
            }
        }
    }
}