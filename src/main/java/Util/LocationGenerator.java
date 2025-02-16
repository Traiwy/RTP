package Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import ru.traiwy.rtp.RTP;

import java.util.List;
import java.util.Random;

public class LocationGenerator {
    private final RTP plugin;
    private final RtpInProcessing rtpInProcessing;
    public LocationGenerator(RTP plugin) {
        this.plugin = plugin;
        this.rtpInProcessing = plugin.getRtpInProcessing();
    }
    public static Location generateSafeLocation(Plugin plugin, World world) {
        List<String> unsafeMaterials = plugin.getConfig().getStringList("unsafeBlocks");
        boolean negative = plugin.getConfig().getBoolean("rtpLimit.negative");
        int xLimit = Math.min(plugin.getConfig().getInt("rtpLimit.xLimit"), 1000);
        int zLimit = Math.min(plugin.getConfig().getInt("rtpLimit.zLimit"), 1000);
        Random random = new Random();
        while (true) {
            int x = negative ? random.nextInt(xLimit * 2) - xLimit : random.nextInt(xLimit);
            int z = negative ? random.nextInt(zLimit * 2) - zLimit : random.nextInt(zLimit);


            Block generatedBlock = world.getHighestBlockAt(x, z);
            Block higherBlock = world.getBlockAt(x, generatedBlock.getY() + 2, z);
            if (isUnsafeBlock(generatedBlock, unsafeMaterials) || isUnsafeBlock(higherBlock, unsafeMaterials)) {
                continue;
            }
            return new Location(world, x + 0.5, generatedBlock.getY() + 1, z + 0.5);
        }
    }
    private static boolean isUnsafeBlock(Block block, List<String> unsafeMaterials) {
        return unsafeMaterials.contains(block.getType().name());
    }

    public static Location generateSafeLocationLong(Plugin plugin, World world){
        List<String> unsafeMaterials = plugin.getConfig().getStringList("unsafeBlocks");
        boolean negative = plugin.getConfig().getBoolean("rtpLongLimit.negative");
        int xLimit = Math.min(plugin.getConfig().getInt("rtpLongLimit.xLimit"), 10000);
        int zLimit = Math.min(plugin.getConfig().getInt("rtpLongLimit.zLimit"), 10000);
        Random random = new Random();
        while (true){
            int x = negative ? random.nextInt(xLimit * 2) - zLimit : random.nextInt(xLimit);
            int z = negative ? random.nextInt(zLimit * 2) - zLimit : random.nextInt(zLimit);

            Block generatedBlock = world.getHighestBlockAt(x, z);
            Block higherBlock = world.getBlockAt(x, generatedBlock.getY() + 2, z);
            if (isUnsafeBlock(generatedBlock, unsafeMaterials) || isUnsafeBlock(higherBlock, unsafeMaterials)) {
                continue;
            }
            return new Location(world, x + 0.5, generatedBlock.getY() + 1, z + 0.5);
        }
    }

}