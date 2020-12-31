package com.gmail.nimadastmalchi.editor.commands;

import com.gmail.nimadastmalchi.editor.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class UndoCommand implements CommandExecutor {
    private Main plugin;

    public UndoCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("undo").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players may execute this command.");
            return true;
        }

        Player p = (Player) sender;
        if (args.length != 0) {
            p.sendMessage("Incorrect number of arguments.");
            return true;
        }

        for (Map.Entry entry : Main.recentlyChanged.entrySet()) {
            Location l = (Location) entry.getKey();
            Material m = (Material) entry.getValue();
            l.getBlock().setType(m);
        }

        // Possibly pointless
        Main.recentlyChanged = new HashMap<>();

        return true;
    }
}
