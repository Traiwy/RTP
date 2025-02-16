package Command;

import Util.ConfigManager;
import Util.LocationGenerator;
import Util.MySqlStorage;
import Util.RtpInProcessing;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;
import ru.traiwy.rtp.RTP;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import static Util.FormatString.formatString;


public class CommandRTP implements CommandExecutor {
    private final RTP plugin;
    private final LocationGenerator locationGenerator;
    private final RtpInProcessing rtpInProcessing;
    public CommandRTP(RTP plugin) {
        this.plugin = plugin;
        this.locationGenerator = plugin.getLocationGenerator();
        this.rtpInProcessing = plugin.getRtpInProcessing();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return true;
        }
        Player player = (Player) sender;
        plugin.getRtpInProcessing().startRtp(player);
        player.sendMessage(ConfigManager.Message.Chat2.message);
        sendTitle(player);
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.runTaskLater(plugin, () -> {
        if (plugin.getRtpInProcessing().isRtpInProgress(player)) {
            Location generatedLocation = locationGenerator.generateSafeLocation(plugin, player.getWorld());
            if (generatedLocation == null) {
                player.sendMessage("Не удалось сгенерировать безопасную локацию.");
                plugin.getRtpInProcessing().cancelRtp(player);
                return;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 10));
            player.teleport(generatedLocation);
            MySqlStorage.CreateTable();
            MySqlStorage.logRTP(player, generatedLocation);

            if (ConfigManager.Message.Chat.enabled) {
                ConfigManager.Message.Chat.message = ChatColor.translateAlternateColorCodes('&', formatString(generatedLocation, ConfigManager.Message.Chat.message));
                player.sendMessage(ConfigManager.Message.Chat.message);

            }
            plugin.getRtpInProcessing().cancelRtp(player);
        }
        },100L);
        return true;
    }
    private void sendTitle(Player player){
        String secondLine = ConfigManager.Message.Title.secondLine;
        String firstLine = ConfigManager.Message.Title2.firstLine;
        if (secondLine == null || firstLine == null) {
            player.sendMessage("Ошибка: не найдены строки для заголовка.");
            return;
        }
        Component secondLineComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(secondLine);
        Component firstLineComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(firstLine);
        Title title = Title.title(secondLineComponent, firstLineComponent);
        player.showTitle(title);
    }

}


