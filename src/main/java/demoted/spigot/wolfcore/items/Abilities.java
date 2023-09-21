package demoted.spigot.wolfcore.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import demoted.spigot.wolfcore.main;

public class Abilities {
    public static List<String> validEffects = Arrays.asList("effect","randomeffect","daggerpassive");
    public static List<PotionEffectType> negativeEffects = Arrays.asList(PotionEffectType.BLINDNESS,
            PotionEffectType.DARKNESS, PotionEffectType.HUNGER, PotionEffectType.WEAKNESS, PotionEffectType.LEVITATION,
            PotionEffectType.WITHER, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.CONFUSION);

    public static PotionEffectType randomNegativeEffect() {
        return negativeEffects.get(new Random().nextInt(negativeEffects.size()));
    }

    public static HashMap<String, Runnable> damageEvents = new HashMap<String, Runnable>();
    public static void deathEventAbility(LivingEntity enemy, LivingEntity self, String abilityData) {
        main plugin = main.getPlugin(main.class);
        plugin.getLogger().info(abilityData);
        String[] abilities = abilityData.split(",");
        for (String ability : abilities) {
            String[] meta = ability.split(":");
            LivingEntity target = self;
            if (meta.length > 1 && meta[1].equalsIgnoreCase("enemy"))
                target = enemy;
            if (meta.length > 1 && meta[1].equalsIgnoreCase("self"))
                target = self;
            if (target instanceof LivingEntity) {
                switch (meta[0]) {
                    case "effect":
                        effect(target, meta);
                        break;
                    case "randomeffect":
                        randomPotion(target, meta);
                        break;
                    case "daggerpassive":
                        daggerpassive(self, meta);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public static void damageEventAbilityEnemy(LivingEntity enemy, LivingEntity self, String abilityData) {
        main plugin = main.getPlugin(main.class);
        plugin.getLogger().info(abilityData);
        String[] abilities = abilityData.split(",");
        for (String ability : abilities) {
            String[] meta = ability.split(":");
            LivingEntity target = self;
            if (meta.length > 1 && meta[1].equalsIgnoreCase("enemy"))
                target = enemy;
            if (meta.length > 1 && meta[1].equalsIgnoreCase("self"))
                target = self;
            if (target instanceof LivingEntity) {
                switch (meta[0]) {
                    case "effect":
                        effect(target, meta);
                        break;
                    case "randomeffect":
                        randomPotion(target, meta);
                        break;
                    case "daggerpassive":
                        daggerpassive(self, meta);
                        break;
                    default:
                        break;
                }
            }
        }
    }
    public static void effect(LivingEntity entity, String meta[]) {
        if (meta.length >= 3) {
            Integer duration;
            try {
                duration = Integer.parseInt(meta[3]);
            } catch (NumberFormatException e) {
                duration = 10;
            }
            PotionEffectType type = PotionEffectType.getByName(meta[2].toUpperCase());
            if (type != null) {
                entity.addPotionEffect(new PotionEffect(type, duration, 0, false, false, false));
            }
        }
    }

    public static void daggerpassive(LivingEntity entity, String meta[]) {
        EntityEquipment equipment = entity.getEquipment();
        ItemStack offhand = equipment.getItemInOffHand().clone();
        ItemStack mainhand = equipment.getItemInMainHand().clone();
        if (!itemDictionary.isEmpty(offhand)) {
            equipment.setItemInMainHand(offhand);
            equipment.setItemInOffHand(mainhand);
        }
        ;
    }
    
    public static void randomPotion(LivingEntity entity, String meta[]) {
        Integer duration = 200;
        if (meta.length >= 3) {
            try {
                duration = Integer.parseInt(meta[3]);
            } catch (NumberFormatException e) {
                duration = 200;
            }
        }
        entity.addPotionEffect(new PotionEffect(randomNegativeEffect(), duration, 0, false, false));
    }
}
