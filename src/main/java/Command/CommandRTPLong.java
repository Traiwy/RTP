package Command;

import Util.LocationGenerator;
import Util.RtpLongInProcessing;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


public class CommandRTPLong implements CommandExecutor {
    private final JavaPlugin plugin;
    private final RtpLongInProcessing rtpLongInProcessing;
    public CommandRTPLong(JavaPlugin plugin){
        this.plugin = plugin;
        this.rtpLongInProcessing = plugin.getRtpLongInProcessing();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Только в игре");
            return true;
        }
        Player player = (Player) sender;
        plugin.get

    }
}
