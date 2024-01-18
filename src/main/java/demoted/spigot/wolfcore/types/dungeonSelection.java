package demoted.spigot.wolfcore.types;

import org.bukkit.entity.Player;

public class dungeonSelection {
    public Player player;
    public dungeon dungeon;
    public dungeonSelection(Player player, dungeon dungeon) {
        this.player = player;
        this.dungeon = dungeon;
    }
}
