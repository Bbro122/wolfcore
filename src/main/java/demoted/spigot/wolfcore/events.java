package demoted.spigot.wolfcore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import demoted.spigot.wolfcore.items.Abilities;
import demoted.spigot.wolfcore.items.itemDictionary;

public class events implements Listener {
    public main plugin;

    public events(main plugin) {
        this.plugin = plugin;
    }
    public void onEntityDeath(EntityDamageByEntityEvent event) {
        Abilities.deathEventAbility(null, null, null);
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onBlockExplode(BlockExplodeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Double critPercent = Math.random()<=0.1?2.0:1.0;
        Entity self = event.getDamager();
        Entity enemy = event.getEntity();
        if (event.getCause() == DamageCause.ENTITY_ATTACK || event.getCause() == DamageCause.ENTITY_SWEEP_ATTACK) {
            Double totalArmor = 0.0;
            if (self instanceof LivingEntity && enemy instanceof LivingEntity) {
                EntityEquipment selfEquipment = ((LivingEntity) self).getEquipment();
                EntityEquipment enemyEquipment = ((LivingEntity) enemy).getEquipment();
                ItemMeta mainHand = selfEquipment.getItemInMainHand().getItemMeta();
                if (mainHand != null) {
                    PersistentDataContainer container = mainHand.getPersistentDataContainer();
                    String abilityString = container.get(itemDictionary.abilityKey,
                            PersistentDataType.STRING);
                    String weaponType = container.get(itemDictionary.typeKey, PersistentDataType.STRING);
                    if (weaponType!=null&&weaponType.equalsIgnoreCase(itemDictionary.CLAYMORE)) {
                        if (!itemDictionary.isEmpty(selfEquipment.getItemInOffHand())) {
                            self.sendMessage("§cYou attempt to swing to no avail, this is a 2 handed weapon.");
                            event.setCancelled(true);
                            return;
                        }
                    }
                    if (abilityString != null) {
                        Abilities.damageEventAbilityEnemy((LivingEntity) enemy, (LivingEntity) self, abilityString);
                    }
                }
                totalArmor += itemDictionary.getItemArmor(enemyEquipment.getHelmet());
                totalArmor += itemDictionary.getItemArmor(enemyEquipment.getLeggings());
                totalArmor += itemDictionary.getItemArmor(enemyEquipment.getChestplate());
                totalArmor += itemDictionary.getItemArmor(enemyEquipment.getBoots());
                totalArmor *= 0.04;
                if (itemDictionary.isEmpty(selfEquipment.getItemInOffHand())) totalArmor*=.95;
                totalArmor = 1 - totalArmor;
            }
            event.setDamage((event.getDamage()*critPercent) * totalArmor);
            if (event.getFinalDamage()>=((LivingEntity) enemy).getHealth()) onEntityDeath(event);
            TextDisplay TextDisplay = (TextDisplay) enemy.getWorld().spawnEntity(enemy.getLocation(),
                    EntityType.TEXT_DISPLAY);
            if (critPercent == 1) {
                TextDisplay.setText("§c" + Math.round(event.getDamage() * 100.0) / 100.0);
            } else {
                TextDisplay.setText("§4CRIT " + Math.round(event.getDamage() * 100.0) / 100.0);
            }
            TextDisplay.setBillboard(Billboard.CENTER);
            TextDisplay.teleport(
                    TextDisplay.getLocation().add(Math.random() * 2 - 1, Math.random(), Math.random() * 2 - 1));
            BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
                @Override
                public void run() {
                    Location currentLocation = TextDisplay.getLocation();
                    TextDisplay.teleport(currentLocation.add(0, 0.1, 0));
                }
            }, 0, 1);
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    task.cancel();
                    TextDisplay.remove();
                }
            }, 20);
        }
    }
}
