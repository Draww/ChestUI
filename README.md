<h3 align=center>
    <img src="https://i.imgur.com/3Sk1buV.png"/>
</h3>

:red_circle: This is currently is development!

### Coming soon
- Item data
- 1.13 support

### Simple menu

##### Kotlin
```kotlin
// Create and open a GUI named "Menu" with 6 rows
gui(player, "Menu", 6){
    // Place an item at column 3 and row 2
    item(3,2){
        type = Material.CHEST
        amount = 16
        name = "&cTest item"
        // When this item is clicked, do the following
        onclick = { player ->
            // Prevent taking the item
            isCancelled = true
            // Send a message with the location of the item
            player.msg("&bYou clicked the item $name &bat $x $y!")
        }
    }
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(3,2, listener((item) -> {
        item.setType(Material.ANVIL);
        item.setName("&6My button");
        item.getLore().add("&cThis is the lore");
        item.setAmount(32);
        // When the item is clicked, call this listener
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            player.sendMessage("§bYou clicked");
        }));
    }));
}));
```

### Modify item on click

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.CHEST
        name = "&cClick me"
        onclick = { player ->
            // Prevent taking the item
            isCancelled = true
            // Modify the item
            type = Material.DIAMOND
            name = "&bI am a diamond"
            // Refresh the GUI
            refresh()
        }
    }
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.CHEST);
        item.setName("&cClick me");
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            item.setType(Material.DIAMOND);
            item.setName("&bI am a diamond")
            gui.refresh();
        }));
    }));
}));
```

### Move item on click

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.CHEST
        name = "&cClick me"
        onclick = { player ->
            isCancelled = true
            // Move the item to the right
            move(Direction.right)
            // Refresh the GUI
            refresh()
        }
    }
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.CHEST);
        item.setName("&cClick me");
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            item.move(Direction.right);
            gui.refresh();
        }));
    }));
}));
```

### Move item to a fixed location on click

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.CHEST
        name = "&cClick me"
        onclick = { player ->
            isCancelled = true
            // Move the item to the bottom-right of the GUI
            move(9,6)
            // Refresh the GUI
            refresh()
        }
    }
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.CHEST);
        item.setName("&cClick me");
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            item.move(9, 6);
            gui.refresh();
        }));
    }));
}));
```

### Animate item on click

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.CHEST
        name = "&cClick me"
        onclick = { player ->
            isCancelled = true
            // Each 1 second, the item will move to the right until the right edge
            schedule(period = 1, unit = TimeUnit.SECONDS){
                // Move the item to the right
                move(Direction.right)
                // If the item has reached the right edge, cancel the task
                if(x == 9) cancel()
                // Refresh the GUI
                refresh()
            }
        }
    }
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.CHEST);
        item.setName("&cClick me");
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            schedule(this, false, null, 1L, TimeUnit.SECONDS, listener((task) -> {
                item.move(Direction.right);
                if(item.getX() == 9) task.cancel();
                gui.refresh();
            }));
        }));
    }));
}));
```

### Set a background

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    // This builder will apply to all items in the GUI
    background{
        type = Material.BRICKS
        name = "&cI am part of the background"
        onclick = { player ->
            // Prevent taking the item
            isCancelled = true
        }
    }
    
    // Add other items
    item(1,1){...}
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.background(listener((item) -> {
        item.setType(Material.BRICKS);
        item.setName("&cI am part of the background");
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
        }));
    }));
    
    gui.item(1,1, ...);
}));
```

### Fill a certain zone

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    // This builder will apply to all items between item (1,1) and item (2,2)
    fill(1,1, 2,2){
        type = Material.DIAMOND
        name = "&bTake me!"
    }
    
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.fill(1,1, 2,2, listener((item) -> {
        item.setType(Material.DIAMOND);
        item.setName("&bTake me!");
    }));

}));
```

### Set the lore line by line

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.DIAMOND
        name = "&bTake me!"
        lore += "&6Here is a diamond for you!"
    }
    
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.DIAMOND);
        item.getLore().add("&6Here is a diamond for you!");
    }));

}));
```

### Set the lore as a text

##### Kotlin
```kotlin
val text = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit.
Suspendisse fermentum accumsan sapien vitae ultrices.
Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.
""".trimIndent()

gui(player, "Menu", 6){

    item(1,1){
        type = Material.DIAMOND
        name = "&bTake me!"
        lore += wrap(text)
    }
    
}
```

##### Java
```java
String text =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
    "Suspendisse fermentum accumsan sapien vitae ultrices.\n" +
    "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos.";

gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.DIAMOND);
        item.getLore().addAll(wrap(text));
    }));

}));
```

### Set enchantments

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.IRON_SWORD
        name = "&bThe Excalibur"
        // Set the enchantment Knockback III
        enchants[Enchantment.KNOCKBACK] = 3
    }
    
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.IRON_SWORD);
        item.setName("&bThe Excalibur");
        item.getEnchants().put(Enchantment.KNOCKBACK, 3);
    }));

}));
```

### Modify the title of the GUI on click

##### Kotlin
```kotlin
gui(player, "Menu", 6){

    item(1,1){
        type = Material.CHEST
        name = "&cClick me"
        onclick = { player ->
            isCancelled = true
            // Change the title
            title = "The new title"
            // Reopen the GUI
            regen()
            open(player)
        }
    }
    
}
```

##### Java
```java
gui(plugin, player, "Menu", 6, listener((gui) -> {

    gui.item(1,1, listener((item) -> {
        item.setType(Material.CHEST);
        item.setName("&cClick me");
        item.setOnclick(listener((e, player2) -> {
            e.setCancelled(true);
            gui.setTitle("The new title");
            gui.regen();
            gui.open(player);
        }));
    }));

}));
```
