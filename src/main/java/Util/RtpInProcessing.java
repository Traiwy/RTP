package Util;

import org.bukkit.entity.Player;
import ru.traiwy.rtp.RTP;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RtpInProcessing {
    private final RTP plugin;
    private final LocationGenerator locationGenerator;
    public RtpInProcessing(RTP plugin){
        this.plugin = plugin;
        this.locationGenerator = plugin.getLocationGenerator();
    }
    final static Map<UUID, Boolean> rtpInProcessing = new HashMap<>();

    public boolean isRtpInProgress(Player player) {
        return rtpInProcessing.getOrDefault(player.getUniqueId(), false);
    }
    public void startRtp(Player player) {
        rtpInProcessing.put(player.getUniqueId(), true);
    }
    public void cancelRtp(Player player) {
        rtpInProcessing.remove(player.getUniqueId());
    }
    public LocationGenerator locationGenerator() {
        return locationGenerator;
    }
}
