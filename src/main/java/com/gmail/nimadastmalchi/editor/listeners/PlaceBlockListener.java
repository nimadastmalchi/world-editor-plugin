package com.gmail.nimadastmalchi.editor.listeners;

import com.gmail.nimadastmalchi.editor.Main;
import com.gmail.nimadastmalchi.editor.Size;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.HashMap;

public class PlaceBlockListener implements Listener {
    private Main plugin;

    public PlaceBlockListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (Main.players.containsKey(p.getName())) {
            // Reset recentlyChanged:
            Main.recentlyChanged = new HashMap<>();

            Location l = e.getBlockPlaced().getLocation();
            Material m = e.getBlock().getType();

            float yaw = p.getLocation().getYaw();
            yaw = (yaw %= 360) >= 0 ? yaw : (yaw + 360);

            Size s = Main.players.get(p.getName());
            int x = s.getX();
            int y = s.getY();
            int z = s.getZ();

            int xMult;
            int yMult = (y < 0) ? -1 : 1;
            int zMult;
            if (yaw <= 45) {
                xMult = -1;
                zMult = 1;
            } else if (yaw <= 90) {
                x += z - (z = x);
                xMult = -1;
                zMult = 1;
            } else if (yaw <= 135) {
                x += z - (z = x);
                xMult = -1;
                zMult = -1;
            } else if (yaw <= 180) {
                xMult = -1;
                zMult = -1;
            } else if (yaw <= 225) {
                xMult = 1;
                zMult = -1;
            } else if (yaw <= 270) {
                x += z - (z = x);
                xMult = 1;
                zMult = -1;
            } else if (yaw <= 315) {
                x += z - (z = x);
                xMult = 1;
                zMult = 1;
            } else {
                xMult = 1;
                zMult = 1;
            }

            xMult *= (s.getX() < 0) ? -1 : 1;
            zMult *= (s.getZ() < 0) ? -1 : 1;

            Main.recentlyChanged.clear();
            Main.recentlyChanged.put(l, e.getBlockReplacedState().getType());

            for (int i = 0; i < Math.abs(x); i++) {
                for (int j = 0; j < Math.abs(y); j++) {
                    for (int k = 0; k < Math.abs(z); k++) {
                        Location current = l.clone().add(i * xMult, j * yMult, k * zMult);
                        if (!(i == 0 && j == 0 && k == 0)) {
                            Main.recentlyChanged.put(current, current.getBlock().getType());
                            current.getBlock().setType(m);
                        }
                    }
                }
            }
        }
    }
}
