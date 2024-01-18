package demoted.spigot.wolfcore.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import demoted.spigot.wolfcore.main;
import demoted.spigot.wolfcore.types.playerParty;
import net.md_5.bungee.api.ChatColor;

public class party implements CommandExecutor {
    public main plugin;
    public ArrayList<playerParty> parties = new ArrayList<>();

    public party(main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            Player player = (Player) sender;
            switch (args[0].toLowerCase()) {
                case "invite": {
                    if (args.length > 1) {
                        Player invitee = Bukkit.getPlayer(args[1]);
                        if (invitee != null) {
                            playerParty party = getParty(player);
                            if (party != null) {
                                if (party.invitedPlayers.contains(invitee)) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&d&lParty &8> &dPlayer has already been invited to your group."));
                                } else if (party.players.contains(invitee)) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&d&lParty &8> &dPlayer is already in your group."));
                                } else if (getParty(invitee) != null) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&d&lParty &8> &dPlayer is already in another group."));
                                } else {
                                    party.invitedPlayers.add(invitee);
                                    sendParty(party,
                                            "&d&lParty &8> &dSuccessfully invited &e" + invitee.getDisplayName()
                                                    + " &dto the party.");
                                }
                            } else {
                                if (getParty(invitee) != null) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&d&lParty &8> &dPlayer is already in another group."));
                                } else {
                                    party = new playerParty(player);
                                    parties.add(party);
                                    party.invitedPlayers.add(invitee);
                                    sendParty(party,
                                            "&d&lParty &8> &dSuccessfully invited &e" + invitee.getDisplayName()
                                                    + " &dto the party.");
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&d&lParty &8> &dPlayer could not be found."));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                "&d&lParty &8> &dA player name must be provided."));
                    }
                }  break;
                case "accept": {
                    if (args.length > 1) {
                        if (getParty(player) != null) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&d&lParty &8> &dYou are already in a party."));
                        } else {
                            Player host = Bukkit.getPlayer(args[1]);
                            if (host != null && host != player) {
                                playerParty party = getParty(host);
                                if (party != null) {
                                    if (party.invitedPlayers.contains(player)) {
                                        party.players.add(player);
                                        party.invitedPlayers.remove(player);
                                        sendParty(party, "&d&lParty &8> &e" + player.getDisplayName()
                                                + " &dhas joined the party.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&d&lParty &8> &dThis player is not in a party."));
                                }
                            } else {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                            "&d&lParty &8> &dNo player could be found."));
                            }
                        }
                    }
                } break;
                case "list": {
                    playerParty party = getParty(player);
                    if (party!=null) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&d&lParty &8> &dList of players:"));
                        for (Player plr : party.players) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&8- &d"+plr.getDisplayName()));
                        }
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                    "&d&lParty &8> &dYou are not in a party."));
                    }
                } break;
            }
        }
        return false;
    }

    playerParty getParty(Player player) {
        playerParty party = parties.stream()
                .filter(party2 -> party2.players.contains(player))
                .findAny()
                .orElse(null);
        return party;
    }

    void sendParty(playerParty party, String message) {
        for (Player player : party.players) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
