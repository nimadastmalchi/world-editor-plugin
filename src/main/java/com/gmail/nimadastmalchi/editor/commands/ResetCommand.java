package com.gmail.nimadastmalchi.editor.commands;

import com.gmail.nimadastmalchi.editor.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ResetCommand implements CommandExecutor {
    private Main plugin;

    public ResetCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("reset").setExecutor(this);
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

        p.sendMessage(ChatColor.GREEN + "The size has been reset to (1, 1, 1).");
        Main.players.remove(p.getName());
        return true;
    }
}
