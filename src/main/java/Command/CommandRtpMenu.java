package Command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

import java.util.ArrayList;
import java.util.List;

public class CommandRtpMenu implements CommandExecutor {
    private Inventory inventory;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Эту команду можно использовать только в игре.");
            return true;
        }
        inventory = Bukkit.createInventory(null, 27, Component.text("RTP menu", NamedTextColor.BLUE));
        ItemStack item = new ItemStack(Material.COMPASS);
        ItemMeta meta = item.getItemMeta();
        Component displayName = Component.text("Рандомная телепортация")
                .color(TextColor.color(0x00FFFF))
                .decoration(TextDecoration.ITALIC, false);
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Телепорация..")
                .decoration(TextDecoration.ITALIC, false));
        meta.lore(lore);
        item.setItemMeta(meta);
        meta.displayName(displayName);
        item.setItemMeta(meta);

        inventory.setItem(13, item);
        Player player = (Player) sender;
        open(player);
        return true;
    }
    public void open(Player player){
        player.openInventory(inventory);
    }
}
