package Util;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ConfigManager {
    private final JavaPlugin plugin;
    private int minDistance;
    private int maxDistance;
    private int maxLongDistance;
    private int minLongDistance;
    private Set<Material> unsafeBlocks;


    public static class Message {
        public static class Title {
            public static boolean enable;
            public static String secondLine;
        }

        public static class Title2 {
            public static boolean enable;
            public static String firstLine;
        }

        public static class Chat {
            public static boolean enabled;
            public static String message;
        }

        public static class Chat2 {
            public static boolean enable;
            public static String message;
        }

        public static class Chat3 {
            public static boolean enable;
            public static String message;
        }
    }

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        load(plugin.getConfig());
        plugin.saveDefaultConfig();
    }

    public int getMinDistance() {
        return minDistance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public Set<Material> getUnsafeBlocks() {
        return unsafeBlocks;
    }


    public void reloadConfig() {
        plugin.reloadConfig();
        load(plugin.getConfig());
    }

    private void load(FileConfiguration file) {

        final var messageSection = file.getConfigurationSection("message");
        if (messageSection != null) {
            ConfigurationSection titleSection = messageSection.getConfigurationSection("title");
            if (titleSection != null) {
                Message.Title.enable = titleSection.getBoolean("enable", true);
                Message.Title.secondLine = titleSection.getString("secondLine");
            }

            ConfigurationSection title2Section = messageSection.getConfigurationSection("title2");
            if (title2Section != null) {
                Message.Title2.enable = title2Section.getBoolean("enable", true);
                Message.Title2.firstLine = title2Section.getString("firstLine");
            }

            ConfigurationSection chatSection = messageSection.getConfigurationSection("chat");
            if (chatSection != null) {
                Message.Chat.enabled = chatSection.getBoolean("enabled", true);
                Message.Chat.message = chatSection.getString("message");
            }

            ConfigurationSection chat2Section = messageSection.getConfigurationSection("chat2");
            if (chat2Section != null) {
                Message.Chat2.enable = chat2Section.getBoolean("enable", true);
                Message.Chat2.message = chat2Section.getString("message");
            }

            ConfigurationSection chat3Section = messageSection.getConfigurationSection("chat3");
            if (chat3Section != null) {
                Message.Chat3.enable = chat3Section.getBoolean("enable", true);
                Message.Chat3.message = chat3Section.getString("message");
            }

            minDistance = plugin.getConfig().getInt("min-distance", 1000);
            maxDistance = plugin.getConfig().getInt("max-distance", 5000);
            maxLongDistance = plugin.getConfig().getInt("max-long-distance", 10000);
            minLongDistance = plugin.getConfig().getInt("min-long-distance", 2000);


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
            MySQL.HOST = file.getString("mysql.host");
            MySQL.PORT = file.getInt("mysql.port");
            MySQL.USER = file.getString("mysql.user");
            MySQL.PASSWORD = file.getString("mysql.password");
            MySQL.DATABASE = file.getString("mysql.database");
        }

    }
    public class MySQL {
        public static String HOST = "localhost";
        public static int PORT = 3306;
        public static String USER = "root";
        public static String PASSWORD = "root";
        public static String DATABASE = "rtp";
    }
}
