package org.man10.deathdeleter.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.man10.deathdeleter.Deathdeleter;
import org.man10.deathdeleter.util.ChestGui;
import org.man10.deathdeleter.util.data;

import java.util.ArrayList;
import java.util.List;

public class deathdeleter implements CommandExecutor , TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("deathdeleter")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if(!player.hasPermission("deathdeleter.op")){
                    player.sendMessage("§cこのコマンドはOP以上の権限者のみ使用可能です。");
                    return true;
                }
                if (args.length >= 1) {
                    if (args[0].equalsIgnoreCase("gui")) {
                        ChestGui.openChestGUI(player);
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("reload")) {
                        Deathdeleter.configReload();
                        player.sendMessage("§a[DD] リロード完了。");
                        return true;
                    }
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")){
                        if(player.getInventory().getItemInMainHand().getAmount() == 0){
                            player.sendMessage("§c[DD] 削除するアイテムを手に持ってください。");
                            return true;
                        }
                    }
                    if (args[0].equalsIgnoreCase("add")) {
                        if(data.add(item)){
                            player.sendMessage("§a[DD] 追加しました。");
                            Deathdeleter.configReload();
                        }else{
                            player.sendMessage("§c[DD] 既にこのアイテムは追加されています。");
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("remove")) {
                        if(data.remove(item)){
                            player.sendMessage("§6[DD] 削除しました。");
                            Deathdeleter.configReload();
                        }else{
                            player.sendMessage("§c[DD] このアイテムは登録されていません。");
                        }
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("save")) {
                        Deathdeleter.plugin.saveConfig();
                        player.sendMessage("§e[DD] Configに書き込みました。");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("help")) {
                        Deathdeleter.plugin.saveConfig();
                        player.sendMessage("§6----- DeathDeleter -----");
                        player.sendMessage("§eプレイヤーが死亡時、又は退出時に登録されているアイテムを\n所持していた場合、自動削除を行うプラグインです。");
                        player.sendMessage("§bコマンド - 説明");
                        player.sendMessage("§a/deathdeleter add\n- 手に持っているアイテムを登録します。");
                        player.sendMessage("§a/deathdeleter remove\n- 手に持っているアイテムが登録されている場合解除します。");
                        player.sendMessage("§a/deathdeleter gui\n- チェストGUI形式で登録を管理します。");
                        player.sendMessage("§a/deathdeleter reload\n- 登録状態をリロードします。(登録,登録解除時に自動で行われます)");
                        player.sendMessage("§a/deathdeleter save\n- 強制的に登録状態をセーブします。(サーバー停止時に自動セーブされます)");
                        return true;
                    }
                } else {
                    player.sendMessage("§c[DD] コマンドの引数が異常です。");
                    return false;
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("deathdeleter")) {
            if(sender.hasPermission("deathdeleter.op")){
                if (args.length == 1) {
                    completions.add("gui");
                    completions.add("add");
                    completions.add("remove");
                    completions.add("reload");
                    completions.add("save");
                    completions.add("help");
                }
            }
        }
        return completions;
    }
}