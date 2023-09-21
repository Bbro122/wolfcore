package demoted.spigot.wolfcore.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import demoted.spigot.wolfcore.main;

public class itemDictionary {

  // Item Tag Keys
  public static final NamespacedKey armorKey = new NamespacedKey(main.getPlugin(main.class), "armorlevel");
  public static final NamespacedKey abilityKey = new NamespacedKey(main.getPlugin(main.class), "abilities");
  public static final NamespacedKey typeKey = new NamespacedKey(main.getPlugin(main.class), "type");

  // Item Type Presets
  public static final String CLAYMORE = "CLAYMORE";
  public static final String DAGGER = "DAGGER";
  public static final String SWORD = "SWORD";
  public static final String SPEAR = "SPEAR";
  public static final String HELMET = "HELMET";
  public static final String CHESTPLATE = "CHESTPLATE";
  public static final String LEGGINGS = "LEGGINGS";
  public static final String BOOTS = "BOOTS";

  // Item Check Lists
  public static final HashMap<String,EquipmentSlot> armorToEquipment = new HashMap<>();
  public static final List<String> weaponTypes = Arrays.asList(CLAYMORE, DAGGER, SWORD, SPEAR);
  public static final List<String> armorTypes = Arrays.asList(HELMET, CHESTPLATE, LEGGINGS, BOOTS);
  public static final List<String> validTypes = Arrays.asList(HELMET, CHESTPLATE, LEGGINGS, BOOTS, CLAYMORE, DAGGER, SPEAR, SWORD);
  public static final HashMap<String, Double> armorPercent = new HashMap<String, Double>();
  public static final HashMap<Integer, String> materialMap = new HashMap<Integer, String>();
  static {
    armorToEquipment.put(BOOTS, EquipmentSlot.FEET);
    armorToEquipment.put(LEGGINGS, EquipmentSlot.LEGS);
    armorToEquipment.put(CHESTPLATE, EquipmentSlot.CHEST);
    armorToEquipment.put(HELMET, EquipmentSlot.HEAD);
    armorPercent.put(HELMET, 0.15);
    armorPercent.put(CHESTPLATE, .40);
    armorPercent.put(LEGGINGS, .3);
    armorPercent.put(BOOTS, .15);
    materialMap.put((Integer) 10, "IRON_");
    materialMap.put((Integer) 25, "GOLDEN_");
    materialMap.put((Integer) 40, "DIAMOND_");
    materialMap.put((Integer) 80, "NETHERITE_");
  }

  // Name Generation
  public static final String[] adjectives = { "Blazing", "Shadowy", "Vicious", "Ethereal", "Crushing", "Venemous",
      "Furious", "Gleaming", "Savage", "Mystical", "Shattering", "Toxic" };
  public static final String[] names = { "Fury", "Whisper", "Claw", "Dream", "Force", "Bite", "Storm", "Star", "Beast",
      "Vision", "Blast", "Spore", "Flame", "Secret", "Fang", "Light", "Hammer", "Sting", "Rage", "Jewel", "Roar",
      "Power", "Strike", "Venom" };

  public static List<Material> getMaterials(int level, String type) {
    List<Material> finalMats = new ArrayList<Material>();
    if (armorTypes.contains(type)) {
      finalMats.add(Material.getMaterial("LEATHER_" + type));
    } else if (weaponTypes.contains(type)) {
      type = "SWORD";
      finalMats.add(Material.getMaterial("WOODEN_" + type));
    } else {
      main.getPlugin(main.class).getLogger().info("SOMETHING IS FUCKED!");
      return null;
    }
    for (Integer mLevel : materialMap.keySet()) {
      String mat = materialMap.get(mLevel);
      if (level >= mLevel) {
        finalMats.add(Material.getMaterial(mat + type));
      }
    }
    return finalMats;
  }

  public static Material getRandomMaterial(int level, String type) {
    List<Material> mats = getMaterials(level, type);
    int index = new Random().nextInt(mats.size());
    Material mat = mats.get(index);
    return mat;
  }

  public static String getName() {
    return adjectives[new Random().nextInt(adjectives.length)] + " " + names[new Random().nextInt(names.length)];
  }
  
  public static Double getItemArmor(ItemStack item) {
    if (item == null) {
      return 0.0;
    }
    ItemMeta itemMeta = item.getItemMeta();
    if (itemMeta == null) return 0.0;
    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
    Double armorPoints = container.get(armorKey, PersistentDataType.DOUBLE);
    if (armorPoints == null) {
      return 0.0;
    } else {
      return armorPoints;
    }
  }
  public static boolean isEmpty(ItemStack item) {
    return item.getType() == Material.AIR;
  }
}
