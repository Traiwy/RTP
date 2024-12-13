package Command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class CommandRtpMenu implements CommandExecutor {
    private Inventory inventory;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return true;
        }
        inventory = Bukkit.createInventory(null, 9, "RTP menu");
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        inventory.setItem(4, item);

        Player player = (Player) sender;
        open(player);
        return true;
    }
    public void open(Player player){
        player.openInventory(inventory);
    }
}
