package fr.rhaz.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

import static fr.rhaz.minecraft.GUIKt.gui;
import static fr.rhaz.minecraft.GUIKt.wrap;
import static fr.rhaz.minecraft.kotlin.Kotlin4MC.*;

public class JavaTest extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onBlockBreak(BlockBreakEvent e){
                test(e.getPlayer());
            }
        }, this);

    }

    String text =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
        "Suspendisse fermentum accumsan sapien vitae ultrices.\n" +
        "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.";

    public void test(Player player){

        gui(this, player, "Menu", 6, listener((gui) -> {

            gui.item(1,1, listener((item) -> {
                item.setType(Material.ANVIL);
                item.setName("&6My button");
                item.getLore().add("&cThis is the lore");
                item.setAmount(32);
                item.setOnclick(listener((e, player2) -> {
                    e.setCancelled(true);
                    player.sendMessage("§cYou clicked");
                    item.move(Direction.right);
                    if(item.getX() == 1) item.move(Direction.down);
                    schedule(this, true, null, 1L, TimeUnit.SECONDS, listener((task) -> {
                        item.move(Direction.right);
                        if(item.getX() == 9) task.cancel();
                        gui.refresh();
                    }));
                    gui.refresh();
                }));
            }));

            gui.item(9,6, listener((item) -> {
                item.setType(Material.WOOD_DOOR);
                item.setName("&cExit");
                item.getEnchants().put(Enchantment.KNOCKBACK, 3);
                item.setOnclick(listener((e, player2) -> {
                    player.closeInventory();
                }));
            }));

            String info = "On sait depuis longtemps que travailler avec du texte lisible et contenant du sens est source de distractions.\n" +
                    "Et empeche de se concentrer sur la mise en page elle-même.\n" +
                    "On sait depuis longtemps que travailler avec du texte lisible et contenant du sens est source de distractions.\n" +
                    "On sait depuis longtemps que travailler avec du texte lisible et contenant du sens est source de distractions.\n" +
                    "Et empeche de se concentrer sur la mise en page elle-même.";
            gui.info(info);
        }));
    }
}
