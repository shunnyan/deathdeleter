package org.man10.deathdeleter.util;

import org.bukkit.inventory.ItemStack;
import org.man10.deathdeleter.Deathdeleter;

import java.util.List;

public class data {
    public static boolean add(ItemStack Item){
        ItemStack CloneItem = Item.clone();
        CloneItem.setAmount(1);
        List<ItemStack> list = Deathdeleter.Stack;
        if(!list.contains(CloneItem)){
            list.add(CloneItem);
            Deathdeleter.config.set("data",list);
            return true;
        }else{
            return false;
        }
    }
    public static boolean remove(ItemStack Item){
        ItemStack CloneItem = Item.clone();
        CloneItem.setAmount(1);
        List<ItemStack> list = Deathdeleter.Stack;
        if(list.contains(CloneItem)){
            list.remove(CloneItem);
            Deathdeleter.config.set("data",list);
            return true;
        }else{
            return false;
        }
    }
}
