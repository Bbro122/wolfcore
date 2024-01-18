package demoted.spigot.wolfcore.types;

import java.io.File;
import java.util.List;

import org.bukkit.Location;

import demoted.spigot.wolfcore.main;
import demoted.spigot.wolfcore.utils;

public class dungeon {
    public File file;
    public Location startLocation;
    public Location endLocation;
    public main plugin;
    //public Triggers triggers;
    public dungeon() {
    }
    static public dungeon constructFromFile(String file) {
        main plugin = main.getPlugin(main.class);
        File[] files = plugin.dungeons.listFiles();
        if (utils.stringAllowedValues(file, null, utils.arrayToStringArray(files))==null) return null;
        return null;
    }
}
