package Event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import ru.traiwy.rtp.RTP;

public class OnInventoryClick implements Listener {
    private final RTP plugin;
    public OnInventoryClick(RTP plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void InventoryClick(InventoryClickEvent event){
        if(event.getView().title().equals("RTP menu")){
            event.setCancelled(true); // Отменяем перемещение вещей в меню
            if(event.getCurrentItem() !=  null && event.getCurrentItem().getType() == Material.COMPASS){
                Player player = (Player) event.getWhoClicked();
                player.closeInventory();
            }
        }
    }

}
