package org.man10.deathdeleter.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.man10.deathdeleter.Deathdeleter;
import java.util.List;

public class ChestGui {
    public static void setGUI(Inventory gui){
        List<ItemStack> list = Deathdeleter.Stack;
        ItemStack[] itemsToSet = new ItemStack[45];
        for (int i = 0; i < 45; i++) {
            int index = i + (Deathdeleter.page * 45);
            if (index < list.size()) {
                itemsToSet[i] = list.get(index);
            }
        }
        gui.setContents(itemsToSet);
        ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glass.getItemMeta();
        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta arrowMeta = glass.getItemMeta();
        glassMeta.setDisplayName(" ");
        glass.setItemMeta(glassMeta);
        for (int i = 46; i <= 52; i++){
            gui.setItem(i,glass);
        }
        if(Deathdeleter.page > 0){
            arrowMeta.setDisplayName("§a前のページ§g");
            arrow.setItemMeta(arrowMeta);
            gui.setItem(45,arrow);
        }else{
            gui.setItem(45,glass);
        }
        if(list.size() > 45 * (Deathdeleter.page + 1)){
            arrowMeta.setDisplayName("§a次のページ§g");
            arrow.setItemMeta(arrowMeta);
            gui.setItem(53,arrow);
        }else{
            gui.setItem(53,glass);
        }
    }
    public static void openChestGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§r§l§aDD_登録アイテム§r§g§f");
        player.openInventory(inv);
        setGUI(inv);
    }

}
