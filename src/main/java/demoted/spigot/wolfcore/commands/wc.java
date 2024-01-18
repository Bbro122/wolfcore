package demoted.spigot.wolfcore.commands;

import java.io.File;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import demoted.spigot.wolfcore.main;
import demoted.spigot.wolfcore.returnValue;
import demoted.spigot.wolfcore.utils;
import demoted.spigot.wolfcore.entities.Abilities;
import demoted.spigot.wolfcore.entities.Elite;
import demoted.spigot.wolfcore.items.customItem;
import demoted.spigot.wolfcore.items.itemDictionary;

public class wc implements CommandExecutor {
    public main plugin;

    public wc(main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            Player player = (Player) sender;
            switch (args[0].toLowerCase()) {
                case "spawnelite": {
                    EntityType type = EntityType.valueOf(utils.stringAllowedValues(args[1].toUpperCase(), "ZOMBIE",
                            utils.arrayToStringArray(EntityType.values())));
                    LivingEntity entity = new Elite(type, player.getLocation())
                            .createEntity();
                    Abilities.spawnAbilities(entity);
                }
                    break;
                case "giveitem": {
                    Integer level = 10;
                    String type = itemDictionary.SWORD;
                    if (args.length >= 2 && itemDictionary.validTypes.contains(args[1].toUpperCase())) {
                        type = args[1].toUpperCase();
                        player.sendMessage(type);
                    }
                    if (args.length == 3) {
                        try {
                            level = Integer.parseInt(args[2]);
                        } catch (NumberFormatException e) {
                        }
                    }
                    customItem item = new customItem(type)
                            .setLevel(level)
                            .setName("§a" + itemDictionary.getName())
                            .addLevelVariation();
                    if (type.equalsIgnoreCase(itemDictionary.CLAYMORE)) {
                        item.setCustomModelData(1);
                    } else if (type.equalsIgnoreCase(itemDictionary.DAGGER)) {
                        item.setCustomModelData(2);
                    }
                    player.getInventory().addItem(item.create());
                }
                    break;
                case "setabilitydata": {
                    EntityEquipment equipment = player.getEquipment();
                    ItemStack item = equipment.getItemInMainHand();
                    if (item.getType() != Material.AIR && args[1] != null) {
                        ItemMeta meta = item.getItemMeta();
                        meta.getPersistentDataContainer().set(itemDictionary.abilityKey, PersistentDataType.STRING,
                                args[1]);
                        item.setItemMeta(meta);
                    }
                }
                    break;
                case "givecustomitem": {
                    File[] validFiles = plugin.customItems.listFiles();
                    if (validFiles.length == 0)
                        return false;
                    List<String> validFileNames = utils.listFiles(plugin.customItems);
                    player.sendMessage(validFileNames.toString());
                    String fileName = utils.stringAllowedValues(args[1], null, validFileNames);
                    if (fileName == null) {
                        player.sendMessage("File defaulted");
                        return false;
                    }
                    returnValue<File> fileReturnValue = utils.getFile(validFiles, fileName);
                    player.sendMessage(fileReturnValue.result.toString());
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(fileReturnValue.result);
                    config.addDefault("material", "");
                    player.sendMessage(config.getString("material"));
                    Material mat = Material.getMaterial(config.getString("material").toUpperCase());
                    String itemType = utils.stringAllowedValues(config.getString("itemType"), itemDictionary.SWORD,
                            itemDictionary.validTypes);
                    int customModelData = config.getInt("customModelData");
                    int defaultLevel = config.getInt("defaultLevel");
                    if (mat == null) {
                        player.sendMessage("Material not found");
                        return false;
                    }
                    config.getStringList("abilities");
                    ItemStack item = new customItem(itemType)
                            .setLevel(defaultLevel)
                            .setCustomModelData(customModelData)
                            .setMaterial(mat)
                            .create(true);
                    player.getInventory().addItem(item);

                }
                    break;
                default:
                    sender.sendMessage("Unrecognized message");
                    break;
            }
        }
        return true;
    }

}
