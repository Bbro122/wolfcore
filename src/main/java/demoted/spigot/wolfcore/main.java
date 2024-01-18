package demoted.spigot.wolfcore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import demoted.spigot.wolfcore.commands.Subcommand;
import demoted.spigot.wolfcore.commands.party;
import demoted.spigot.wolfcore.commands.wc;
import demoted.spigot.wolfcore.items.itemDictionary;

/**
 * Hello world!
 *
 */
public class main extends JavaPlugin {
    public List<Subcommand> wcList;
    public List<Subcommand> partyList;
    public List<Subcommand> dungeonList;
    public File customItems;
    public File customMobs;
    public File dungeons;

    @Override
    public void onEnable() {
        new itemDictionary();
        File file = getDataFolder();
        if (!file.exists())
            file.mkdir();
        File config = new File(file, "config.yml");
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                getLogger().info("Config Unable to be created.");
            }
        }
        File customMobs = new File(file, "customMobs");
        if (!customMobs.exists())
            customMobs.mkdir();
        this.customMobs = customMobs;
        File customItems = new File(file, "customItems");
        if (!customItems.exists())
            customItems.mkdir();
        this.customItems = customItems;
        File dungeons = new File(file, "customItems");
        if (!dungeons.exists())
            dungeons.mkdir();
        this.dungeons = dungeons;
        getServer().getPluginManager().registerEvents(new events(this), this);
        getCommand("wc").setTabCompleter(this);
        getCommand("wc").setExecutor(new wc(this));
        getCommand("party").setExecutor(new party(this));
        wcList = Arrays.asList(
                new Subcommand("giveitem")
                        .addStaticOption(itemDictionary.validTypes)
                        .addStaticOption(utils.intList),
                new Subcommand("romantest"),
                new Subcommand("setabilitydata"),
                new Subcommand("spawnelite")
                        .addStaticOption(utils.arrayToStringArray(EntityType.values()))
                        .addStaticOption(utils.intList),
                new Subcommand("givecustomitem")
                        .addDynamicOption(() -> {
                            return utils.listFiles(customItems);
                        }));
        partyList = Arrays.asList(
                new Subcommand("giveitem")
                        .addStaticOption(itemDictionary.validTypes)
                        .addStaticOption(utils.intList),
                new Subcommand("romantest"),
                new Subcommand("setabilitydata"),
                new Subcommand("spawnelite")
                        .addStaticOption(utils.arrayToStringArray(EntityType.values()))
                        .addStaticOption(utils.intList),
                new Subcommand("givecustomitem")
                        .addDynamicOption(() -> {
                            return utils.listFiles(customItems);
                        }));
    }

    @Override
    public void onDisable() {
        getLogger().info("See you again, SpigotMC!");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (command.getName().equalsIgnoreCase("wc")) {
            if (args.length == 1) {
                List<String> ls = new ArrayList<>();
                for (Subcommand subcommand : wcList) {
                    if (subcommand.command.toLowerCase().startsWith(args[0].toLowerCase()))
                        ls.add(subcommand.command);
                }
                return ls;
            }
            if (args.length > 1) {
                for (Subcommand subcommand : wcList) {
                    if (subcommand.command.equalsIgnoreCase(args[0]))
                        return subcommand.returnData(args);
                }
            }
        } else if (command.getName().equalsIgnoreCase("party")) {

        } else if (command.getName().equalsIgnoreCase("dungeon")) {
            if (args.length == 1) {
                List<String> ls = new ArrayList<>();
                for (Subcommand subcommand : dungeonList) {
                    if (subcommand.command.toLowerCase().startsWith(args[0].toLowerCase()))
                        ls.add(subcommand.command);
                }
                return ls;
            }
        }
        return null;
    }

}
