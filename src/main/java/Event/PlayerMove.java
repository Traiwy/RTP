package Event;

import Util.LocationGenerator;
import Util.RtpInProcessing;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import ru.traiwy.rtp.RTP;

import static Util.FormatString.formatString;

public class PlayerMove implements Listener {
    private  final RTP plugin;
    private RtpInProcessing rtpInProcessing;
    private  LocationGenerator locationGenerator;
    public PlayerMove(RTP plugin){
        this.plugin = plugin;
        this.rtpInProcessing = plugin.getRtpInProcessing();
        this.locationGenerator = plugin.getLocationGenerator();
    }
    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();

        if(rtpInProcessing.isRtpInProgress(player)){
            if (event.getFrom().getBlockX() != event.getTo().getBlockX() ||
                    event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
                rtpInProcessing.cancelRtp(player);
                String chat3Message = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message.chat3.message"));
                player.sendMessage(chat3Message);
            }
        }

    }
}
