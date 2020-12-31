package com.gmail.nimadastmalchi.editor.listeners;

import com.gmail.nimadastmalchi.editor.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class BreakBlockListener implements Listener {

    private Main plugin;

    public BreakBlockListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (Main.clipboard.containsKey(p.getName())) {
            Location start = Main.clipboard.get(p.getName())[0];
            Location end = Main.clipboard.get(p.getName())[1];

            if (start == null) {
                start = e.getBlock().getLocation();
                Main.clipboard.get(p.getName())[0] = start;
                p.sendMessage(ChatColor.GREEN + "Location #1 saved.");
            } else if (end == null) {
                end = e.getBlock().getLocation();
                Main.clipboard.get(p.getName())[1] = end;
                p.sendMessage(ChatColor.GREEN + "Location #2 saved.");
            } else {
                Location paste = e.getBlock().getLocation();

                float yaw = p.getLocation().getYaw();
                yaw = (yaw %= 360) >= 0 ? yaw : (yaw + 360);

                // Clear the existing HashMap:
                Main.recentlyChanged.clear();
                paste(start, end, paste, yaw);
            }
            e.setCancelled(true);
        }
    }

    public static void paste(Location start, Location end, Location paste, float yaw) {
        int xMin = min(start.getBlockX(), end.getBlockX());
        int xMax = max(start.getBlockX(), end.getBlockX());

        int yMin = min(start.getBlockY(), end.getBlockY());
        int yMax = max(start.getBlockY(), end.getBlockY());

        int zMin = min(start.getBlockZ(), end.getBlockZ());
        int zMax = max(start.getBlockZ(), end.getBlockZ());

        // Normalize the starting and ending locations:
        start = new Location(start.getWorld(), xMax, yMin, zMin);
        end = new Location(end.getWorld(), xMin, yMax, zMax);

        // These values are positive:
        int xDiff = start.getBlockX() - end.getBlockX();
        int yDiff = end.getBlockY() - start.getBlockY();
        int zDiff = end.getBlockZ() - start.getBlockZ();

        for (int i = 0; i <= xDiff; i++) {
            for (int j = 0; j <= yDiff; j++) {
                for (int k = 0; k <= zDiff; k++) {
                    Location currentCopy = start.clone().add(-i, j, k);
                    Location currentPaste;
                    if (yaw <= 45) {
                        currentPaste = paste.clone().add(-i, j, k);
                    } else if (yaw <= 90) {
                        currentPaste = paste.clone().add(-k, j, i);
                    } else if (yaw <= 135) {
                        currentPaste = paste.clone().add(-k, j, -i);
                    } else if (yaw <= 180) {
                        currentPaste = paste.clone().add(-i, j, -k);
                    } else if (yaw <= 225) {
                        currentPaste = paste.clone().add(i, j, -k);
                    } else if (yaw <= 270) {
                        currentPaste = paste.clone().add(k, j, -i);
                    } else if (yaw <= 315) {
                        currentPaste = paste.clone().add(k, j, i);
                    } else {
                        currentPaste = paste.clone().add(i, j, k);
                    }
                    Main.recentlyChanged.put(currentPaste, currentPaste.getBlock().getType());
                    currentPaste.getBlock().setType(currentCopy.getBlock().getType());
                }
            }
        }
    }

}