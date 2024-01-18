package demoted.spigot.wolfcore.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class customItem {
    public Material mat;
    public int customModelData;
    public Integer level;
    public String type;
    public String name;
    public ArrayList<String> lore = new ArrayList<>();
    public ItemStack item;
    
    public customItem(String type) {
        this.type = type;
    }

    // Level Management
    public customItem setLevel(Integer newLevel) {
        level = newLevel;
        return this;
    }

    public customItem setCustomModelData(Integer newLevel) {
        customModelData = newLevel;
        return this;
    }

    public customItem setMaterial(Material mat) {
        this.mat = mat;
        return this;
    }

    public customItem addLevelVariation() {
        Integer change = (int) Math.round(Math.random() * 6 - 3);
        level += change;
        return this;
    }

    public customItem setName(String newName) {
        name = newName;
        return this;
    }

    // Lore Management
    public customItem addLore(String newLore) {
        lore.add(newLore);
        return this;
    }

    public customItem addLore(String newLore, Integer index) {
        lore.add(index, newLore);
        return this;
    }

    public customItem removeLore(int index) {
        lore.remove(index);
        return this;
    }

    public void addAttribute(ItemMeta meta, Attribute attribute, Double amount, EquipmentSlot slot) {
        meta.addAttributeModifier(attribute,
                new AttributeModifier(UUID.randomUUID(), "skycore." + attribute.toString(), amount,
                        Operation.ADD_NUMBER, slot));
    }

    public void addAttribute(ItemMeta meta, Attribute attribute, Integer amount) {
        meta.addAttributeModifier(attribute,
                new AttributeModifier(UUID.randomUUID(), "skycore." + attribute.toString(), amount,
                        Operation.ADD_NUMBER));
    }

    public ItemStack create() {
        return create(false);
    }

    public ItemStack create(Boolean defaultLore) {
        if (mat == null)
            item = new ItemStack(itemDictionary.getRandomMaterial(level, type));
        else
            item = new ItemStack(mat);
        // Meta Modification
        ItemMeta meta = item.getItemMeta();

        if (customModelData > 0)
            meta.setCustomModelData(customModelData);
        meta.setDisplayName(name);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); // Hide default lore from item
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(itemDictionary.typeKey, PersistentDataType.STRING, type);
        if (itemDictionary.armorTypes.contains(type)) {
            Double armorAmount = (level - 1.5) / (level * 0.04) * itemDictionary.armorPercent.get(type);
            addAttribute(meta, Attribute.GENERIC_ARMOR, 0);
            if (defaultLore) {
                meta.setLore(Arrays.asList(
                        "§7Level: §9" + level,
                        "§7Armor:§9 " + Math.round(armorAmount * 100.0) / 100.0,
                        "",
                        "§7When Equipped:",
                        "§9+" + Math.round(armorAmount * 100.0) / 100.0 + " Armor"));
            } else if (lore.size() > 0) {
                meta.setLore(lore);
            }
            NamespacedKey armorLevelTag = itemDictionary.armorKey;
            container.set(armorLevelTag, PersistentDataType.DOUBLE, armorAmount);
        } else {
            Double damage = (level - 1.0);
            Double attackSpeed = 1.6;
            if (type.equalsIgnoreCase(itemDictionary.DAGGER)) {
                damage *= 0.75;
                attackSpeed = 2.4;
                setCustomModelData(2);
                addAttribute(meta, Attribute.GENERIC_MOVEMENT_SPEED, 0.0125, EquipmentSlot.HAND);
                addAttribute(meta, Attribute.GENERIC_ATTACK_SPEED, 0.8, EquipmentSlot.OFF_HAND);
                container.set(itemDictionary.abilityKey, PersistentDataType.STRING, "daggerpassive");
            } else if (type.equalsIgnoreCase(itemDictionary.CLAYMORE)) {
                attackSpeed = 0.8;
                damage *= 2;
            }
            addAttribute(meta, Attribute.GENERIC_ATTACK_SPEED, (4.0 - attackSpeed) * -1, EquipmentSlot.HAND);
            addAttribute(meta, Attribute.GENERIC_ATTACK_DAMAGE, damage, EquipmentSlot.HAND);
            if (defaultLore) {
                meta.setLore(Arrays.asList(
                        "§7Type: §2" + type.toLowerCase(),
                        "§7Level: §2" + level,
                        "§7DPS:§2 " + Math.round(100.0 * (damage / (1 / attackSpeed))) / 100.0,
                        "",
                        "§7When in Main Hand:",
                        "§2" + damage + " Attack Damage",
                        "§2" + attackSpeed + " Attack Speed"));
            } else if (lore.size() > 0) {
                meta.setLore(lore);
            }
        }
        item.setItemMeta(meta);
        return item;
    }
}