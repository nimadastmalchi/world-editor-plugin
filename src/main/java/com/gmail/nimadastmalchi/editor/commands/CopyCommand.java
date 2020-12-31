package com.gmail.nimadastmalchi.editor.commands;

import com.gmail.nimadastmalchi.editor.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CopyCommand implements CommandExecutor {
    private Main plugin;

    public CopyCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("copy").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command.");
            return true;
        }

        Player p = (Player) sender;
        if (args.length != 0) {
            p.sendMessage(ChatColor.RED + "Invalid command syntax. Try again.");
            return true;
        }

        // Toggle on copy
        if (!Main.clipboard.containsKey(p.getName())) {
            p.sendMessage(ChatColor.WHITE + "Copy:" + ChatColor.GREEN + " [ON]" + ChatColor.RED + " [OFF]");
            Main.clipboard.put(p.getName(), new Location[2]);
        }

        // Toggle off copy
        else {
            p.sendMessage(ChatColor.WHITE + "Copy:" + ChatColor.RED + " [ON]" + ChatColor.GREEN + " [OFF]");
            Main.clipboard.remove(p.getName()); // now that pasting is complete, remove from clipboard.
        }

        return true;
    }
}
