package Util;

import org.bukkit.Location;

public class FormatString {
    public static String formatString(Location location, String message) {
        return message.replace("%x%", String.valueOf(location.getBlockX()))
                .replace("%y%", String.valueOf(location.getBlockY()))
                .replace("%z%", String.valueOf(location.getBlockZ()));
    }

}
