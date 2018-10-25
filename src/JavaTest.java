import fr.rhaz.minecraft.Direction;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static fr.rhaz.minecraft.GUIKt.gui;
import static fr.rhaz.minecraft.kotlin.Kotlin4MC.listener;

public class JavaTest extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onBlockBreak(BlockBreakEvent e){
                test();
            }
        }, this);

    }

    public void test(){
        gui(this, getServer().getPlayer("Test"), "Menu", 6, listener((gui) -> {

            gui.item(1,1, listener((item) -> {
                item.setType(Material.ANVIL);
                item.setName("&6My button");
                item.getLore().add("&cThis is the lore");
                item.setAmount(32);
                item.setOnclick(listener((e, player) -> {
                    e.setCancelled(true);
                    player.sendMessage("§bYou clicked");
                    item.move(Direction.right);
                    if(item.getX() == 1) item.move(Direction.down);
                    gui.refresh();
                }));
            }));

            gui.item(9,6, listener((item) -> {
                item.setType(Material.WOOD_DOOR);
                item.setName("&cExit");
                item.setOnclick(listener((e, player) -> {
                    player.closeInventory();
                }));
            }));
        }));
    }
}
