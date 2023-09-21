package demoted.spigot.wolfcore.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class Elite {
    public static HashMap<Integer, List<Material>> validArmorLevels = new HashMap<>();
    static {
        validArmorLevels.put(0, Arrays.asList(Material.AIR,Material.AIR,Material.AIR,Material.AIR));
        validArmorLevels.put(20, Arrays.asList(Material.LEATHER_BOOTS,Material.LEATHER_LEGGINGS,Material.LEATHER_CHESTPLATE,Material.LEATHER_HELMET));
        validArmorLevels.put(40, Arrays.asList(Material.GOLDEN_BOOTS,Material.GOLDEN_LEGGINGS,Material.GOLDEN_CHESTPLATE,Material.GOLDEN_HELMET));
        validArmorLevels.put(60, Arrays.asList(Material.IRON_BOOTS,Material.IRON_LEGGINGS,Material.IRON_CHESTPLATE,Material.IRON_BOOTS));
        validArmorLevels.put(80, Arrays.asList(Material.DIAMOND_BOOTS,Material.DIAMOND_LEGGINGS,Material.DIAMOND_CHESTPLATE,Material.DIAMOND_HELMET));
        validArmorLevels.put(100, Arrays.asList(Material.NETHERITE_BOOTS,Material.NETHERITE_LEGGINGS,Material.NETHERITE_CHESTPLATE,Material.NETHERITE_HELMET));
    }
    public static List<List<Material>> getArmorLists(Integer level) {
        List<List<Material>> returnList = new ArrayList<>();
        for (Integer key : validArmorLevels.keySet()) {
            if (level > key) {
                returnList.add(validArmorLevels.get(key));
            }
        }
        return returnList;
    }
    public static List<Material> getRandomArmorSet(Integer level) {
        List<List<Material>> armorSets = getArmorLists(level);
        List<Material> armorSet = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int index = new Random().nextInt(armorSets.size());
            List<Material> armorType = armorSets.get(index);
            armorSet.add(armorType.get(i));
        }
        return armorSet;
    }

    // Constructed Methods

    public Location location;
    public EntityType type;
    public Integer level;
    public List<Material> armorSet = Arrays.asList(Material.AIR,Material.AIR,Material.AIR,Material.AIR);
    public Elite(EntityType type,Location location) {
        this.type = type;
        this.location = location;
    }
    public Elite setLevel(Integer level) {
        this.level = level;
        return this;
    }
    public Elite addArmor() {
        armorSet = getRandomArmorSet(level);
        return this;
    }
    public LivingEntity createEntity() {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        EntityEquipment equipment = entity.getEquipment();
        equipment.setBoots(new ItemStack(armorSet.get(0)));
        equipment.setLeggings(new ItemStack(armorSet.get(1)));
        equipment.setChestplate(new ItemStack(armorSet.get(2)));
        equipment.setHelmet(new ItemStack(armorSet.get(3)));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20+(level*6));
        entity.setHealth(20+(level*6));
        return entity;
    }
}
