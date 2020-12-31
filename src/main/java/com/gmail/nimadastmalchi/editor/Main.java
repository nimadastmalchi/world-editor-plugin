package com.gmail.nimadastmalchi.editor;

import com.gmail.nimadastmalchi.editor.commands.CopyCommand;
import com.gmail.nimadastmalchi.editor.commands.ResetCommand;
import com.gmail.nimadastmalchi.editor.commands.SetCommand;
import com.gmail.nimadastmalchi.editor.commands.UndoCommand;
import com.gmail.nimadastmalchi.editor.listeners.BreakBlockListener;
import com.gmail.nimadastmalchi.editor.listeners.PlaceBlockListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {
    public static HashMap<String, Size> players; // String -> player name , Point -> x, y, z size of the chuck to paste
    public static HashMap<Location, Material> recentlyChanged;
    public static HashMap<String, Location[]> clipboard;

    @Override
    public void onEnable() {
        players = new HashMap<>();
        recentlyChanged = new HashMap<>();
        clipboard = new HashMap<>();

        // Commands and Listeners:
        new SetCommand(this);
        new ResetCommand(this);
        new UndoCommand(this);
        new CopyCommand(this);
        new PlaceBlockListener(this);
        new BreakBlockListener(this);
    }
}