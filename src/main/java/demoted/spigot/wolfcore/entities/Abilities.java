package demoted.spigot.wolfcore.entities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import demoted.spigot.wolfcore.main;

public class Abilities {
    static public void spawnAbilities(Entity enemy) {
        List<ItemDisplay> entities = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ItemDisplay entity = (ItemDisplay) enemy.getWorld().spawnEntity(enemy.getLocation(),
                    EntityType.ITEM_DISPLAY);
            entity.setTransformation(new Transformation(new Vector3f(-0.5f, 2f, -0.5f), new Quaternionf(0, 0, 0, 1),
                    new Vector3f(1f, 1f, 1f), new Quaternionf(0, 0, 0, 1)));
            entity.setItemStack(new ItemStack(Material.TRIDENT));
            entities.add(entity);
        }
        main plugin = main.getPlugin(main.class);
        List<Double> angle = Arrays.asList(0.0);
        new BukkitRunnable() {
            @Override
            public void run() {
                angle.set(0, angle.get(0)+0.1);
                for (int i = 0; i < entities.size(); i++) {
                    ItemDisplay itemDisplay = entities.get(i);
                    if (enemy.isDead()) {
                        itemDisplay.remove();
                    } else {
                        itemDisplay.teleport(enemy.getLocation().add(Math.cos(angle.get(0) + (Math.PI / 2) * i), 0,
                                Math.sin(angle.get(0) + (Math.PI / 2) * i)));
                    }
                }
                if (enemy.isDead()) {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }
    public void abilityRunner() {
        
    }
}
