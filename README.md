<h3 align=center>
    <img src="https://i.imgur.com/2sbdoFJ.png"/>
</h3>

:red_circle: This is currently is development!

### Usage

##### Kotlin
```kotlin
// Create and open a GUI named "Menu" with 6 rows
gui(player, "Menu", 6){
    // Place an item at column 3 and row 2
    item(3,2){
        type = Material.CHEST
        amount = 16
        name = "&cTest item"
        lore += "&bLorem ipsum"
        // When this item is clicked, do the following
        onclick = { player ->
            // Prevent taking the item
            isCancelled(true)
            // Send a message with the location of the item
            player.msg("&bYou clicked the item $name &bat $x $y!")
        }
    }
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.ANVIL);
        item.setName("§6My button");
        item.getLore().add("§cThis is the lore");
        item.setAmount(32);
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            player.sendMessage("§bYou clicked");
        }));
    }));

    gui.item(9,6, listener((item) -> {
        item.setType(Material.WOOD_DOOR);
        item.setName("&cExit");
        item.setOnclick(listener((e, player2) -> {
            player.closeInventory();
        }));
    }));
}));
```

### Coming soon
- Item data
- 1.13 support
