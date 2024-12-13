package Command;

import Util.LocationGenerator;
import Util.RtpInProcessing;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
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
        String chat2Message = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.chat2.message"));
        player.sendMessage(chat2Message);
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
            if (plugin.getConfig().getBoolean("message.chat.enabled")) {
                String chatMessage = ChatColor.translateAlternateColorCodes('&', formatString(generatedLocation, plugin.getConfig().getString("message.chat.message")));
                player.sendMessage(chatMessage);

            }
            plugin.getRtpInProcessing().cancelRtp(player);
        }

        },100L);
        return true;
    }
    private void sendTitle(Player player){
        String secondLine = plugin.getConfig().getString("message.title.secondLine");
        String firstLine = plugin.getConfig().getString("message.title2.firstLine");
        Component secondLineComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(secondLine);
        Component firstLineComponent = LegacyComponentSerializer.legacyAmpersand().deserialize(firstLine);
        Title title = Title.title(secondLineComponent, firstLineComponent);
        player.showTitle(title);
    }
}


