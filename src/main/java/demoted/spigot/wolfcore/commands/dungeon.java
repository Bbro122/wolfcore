package demoted.spigot.wolfcore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import demoted.spigot.wolfcore.main;

public class dungeon implements CommandExecutor {
    public main plugin;
    public dungeon(main plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            Player player = (Player) sender;
            switch (args[0].toLowerCase()) {
                case "edit": {
                    switch (args[1].toLowerCase()) {
                        case "setstart": {
                            Location startPos = player.getLocation();
                        } break;
                        case "setend": {
                            
                        } break;
                        case "select": {

                        } break;
                        case "create": {
                            
                        } break;
                        default:
                            break;
                    }
                } break;
            }
        }
        return false;
    }
}
