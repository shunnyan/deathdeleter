package org.man10.deathdeleter.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.man10.deathdeleter.Deathdeleter;

public class itemremove {
    public static void remove(Player player){
        Deathdeleter.Stack.forEach(item -> {
            ItemStack[] contents = player.getInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                ItemStack itemStack = contents[i];
                if (itemStack != null && itemStack.isSimilar(item)) {
                    if (player.getItemOnCursor() != null && player.getItemOnCursor().isSimilar(item)) {
                        player.setItemOnCursor(null);
                    }
                    player.getInventory().setItem(i, null);
                }
            }
        });
    }
}
