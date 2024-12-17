package Event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import ru.traiwy.rtp.RTP;

public class OnInventoryClick implements Listener {
    private final RTP plugin;
    public OnInventoryClick(RTP plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void InventoryClick(InventoryClickEvent event){
        if(event.getView().title().equals("RTP menu")){
            event.setCancelled(true);
        }
        ItemStack clickedItem = event.getCurrentItem();
        if(clickedItem != null && clickedItem.getType() == Material.COMPASS){
            Player player = (Player) event.getWhoClicked();
            player.performCommand("rtp");
            player.closeInventory();
        }

    }
}
