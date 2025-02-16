package Command;

import Util.ConfigManager;
import Util.MySqlStorage;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class CommandRTPHistory implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return false;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("Использование: /rtphist <ник>");
            return true;
        }
        String targetPlayerName = args[0];
        List<Map<String, Object>> records = MySqlStorage.getLastRTPLocations(targetPlayerName);
        if (!records.isEmpty()) {
            player.sendMessage("Последние телепортаций игрока " + targetPlayerName + ":");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            for (int i = 0; i < records.size(); i++) {
                Map<String, Object> record = records.get(i);
                String name = (String) record.get("player_name");
                double x = (double) record.get("x");
                double y = (double) record.get("y");
                double z = (double) record.get("z");
                String world = (String) record.get("world");
                String time = dateFormat.format(record.get("timestamp"));
                player.sendMessage((i + 1) + ". §6[" + time + "]§f" +
                        " §c" + name + " " +
                        ", §6X§f: " + String.format("%.1f", x) +
                        ", §6Y§f: " + String.format("%.1f", y) +
                        ", §6Z§f: " + String.format("%.1f", z) + " " + world);
            }
        } else {
            player.sendMessage("У игрока " + targetPlayerName + " нет сохраненных телепортаций.");
        }
        return true;
    }
}
