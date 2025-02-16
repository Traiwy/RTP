package Util;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.traiwy.rtp.RTP;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RtpLongInProcessing {
    private final JavaPlugin plugin;
    private final LocationGenerator locationGenerator;
    public RtpLongInProcessing(RTP plugin){
        this.plugin = plugin;
        this.locationGenerator = plugin.getLocationGenerator();
    }
    final static Map<UUID, Boolean> rtpInProcessing = new HashMap<>();

    public boolean isRtpLongInProgress(Player player) {
        return rtpInProcessing.getOrDefault(player.getUniqueId(), false);
    }
    public void startRtpLong(Player player) {
        rtpInProcessing.put(player.getUniqueId(), true);
    }
    public void cancelRtpLong(Player player) {
        rtpInProcessing.remove(player.getUniqueId());
    }
    public LocationGenerator locationGenerator() {
        return locationGenerator;
    }

}
