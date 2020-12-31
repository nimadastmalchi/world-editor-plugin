package com.gmail.nimadastmalchi.editor.commands;

import com.gmail.nimadastmalchi.editor.Main;
import com.gmail.nimadastmalchi.editor.Size;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {
    private Main plugin;

    public SetCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("set").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command.");
            return true;
        }

        Player p = (Player) sender;
        if (args.length != 3) {
            p.sendMessage(ChatColor.RED + "Incorrect number of arguments.");
            return true;
        }

        try {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            Main.players.put(p.getName(), new Size(x, y, z));

            p.sendMessage("X: " + x + "\nY: " + y + "\nZ: " + z);
        } catch (NumberFormatException ex) {
            p.sendMessage(ChatColor.RED + "All parameters (x, y, z) must be integers. Try again.");
        }

        return true;
    }
}
