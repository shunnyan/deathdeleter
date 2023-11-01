package org.man10.deathdeleter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.man10.deathdeleter.command.deathdeleter;
import org.man10.deathdeleter.util.ChestGui;
import org.man10.deathdeleter.util.data;
import org.man10.deathdeleter.util.itemremove;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class Deathdeleter extends JavaPlugin implements Listener {
    public static FileConfiguration config;
    public static List<ItemStack> Stack;
    public static JavaPlugin plugin;
    public static int page = 0;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("deathdeleter")).setExecutor(new deathdeleter());
        saveDefaultConfig();
        config = getConfig();
        plugin = this;
        configReload();
    }

    @Override
    public void onDisable(){
        saveConfig();
    }

    public static void configReload(){
        List<ItemStack> list = new ArrayList<>();
        if (config.contains("data")) {
            Object data = config.get("data");
            if (data instanceof List) {
                List<?> itemStackList = (List<?>) data;
                for (Object item : itemStackList) {
                    if (item instanceof ItemStack) {
                        list.add((ItemStack) item);
                    }
                }
            }
        }
        Stack = list;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        itemremove.remove(player);
        for (ItemStack itemToRemove : Deathdeleter.Stack) {
            for (Iterator<ItemStack> iterator = event.getDrops().iterator(); iterator.hasNext(); ) {
                ItemStack drop = iterator.next();
                if (drop.isSimilar(itemToRemove)) {
                    iterator.remove();
                }
            }
        }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        itemremove.remove(player);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) {
            return;
        }
        if(event.getView().getTitle().equals("§r§l§aDD_登録アイテム§r§g§f")){
            ItemStack clickItem = event.getCurrentItem();
            if(clickItem == null){
                return;
            }
            if(event.getClickedInventory().equals(player.getInventory())){
                data.add(clickItem);
            }else{
                if(clickItem.getItemMeta().getDisplayName().equals("§a次のページ§g")){
                    page++;
                }else if(clickItem.getItemMeta().getDisplayName().equals("§a前のページ§g")){
                    page--;
                }else{
                    data.remove(clickItem);
                }
            }
            configReload();
            event.setCancelled(true);
            Inventory gui = event.getView().getTopInventory();
            ChestGui.setGUI(gui);
        }
    }
    @EventHandler
    public void closeInventory(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        if(event.getView().getTitle().equals("§r§l§aDD_登録アイテム§r§g§f")){
            player.sendMessage("§b[DD] 保存しました。");
            configReload();
        }
    }

}
