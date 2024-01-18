package demoted.spigot.wolfcore.types;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class playerParty {
    public Boolean joinBlocked = false;
    public ArrayList<Player> players = new ArrayList<>();
    public Player hostPlayer;
    public ArrayList<Player> invitedPlayers = new ArrayList<>();
    public playerParty(Player player) {
        players.add(player);
        hostPlayer = player;
    }
}
