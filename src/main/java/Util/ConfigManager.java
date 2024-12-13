package Util;

import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {
    private final JavaPlugin plugin;
    private int minDistance;
    private int maxDistance;
    private Set<Material> unsafeBlocks;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    private void loadConfig() {
        plugin.saveDefaultConfig();

        minDistance = plugin.getConfig().getInt("min-distance", 1000);
        maxDistance = plugin.getConfig().getInt("max-distance", 5000);

        unsafeBlocks = new HashSet<>();
        List<String> unsafeBlockNames = plugin.getConfig().getStringList("unsafe-blocks");
        for (String blockName : unsafeBlockNames) {
            Material material = Material.matchMaterial(blockName);
            if (material != null) {
                unsafeBlocks.add(material);
            } else {
                plugin.getLogger().warning("Неизвестный блок в конфигурации: " + blockName);
            }
        }
    }
}

