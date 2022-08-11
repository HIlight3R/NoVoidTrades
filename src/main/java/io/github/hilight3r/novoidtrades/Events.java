package io.github.hilight3r.novoidtrades;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.Objects;

public class Events implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerMove(PlayerMoveEvent event) {
        NoVoidTrades.getInstance().movingPlayers.put(event.getPlayer().getUniqueId(), 0);
        Utils.closeMerchantInventory(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onVehicleMove(VehicleMoveEvent event) {
        for (Entity entity : event.getVehicle().getPassengers()) {
            if (entity instanceof Player) {
                NoVoidTrades.getInstance().movingPlayers.put(entity.getUniqueId(), 0);
                Utils.closeMerchantInventory((Player) entity);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getType() == InventoryType.MERCHANT && Utils.checkDistance(event.getPlayer().getLocation(), Objects.requireNonNull(event.getInventory().getLocation())))
            NoVoidTrades.getInstance().tradingPlayers.put(event.getPlayer().getUniqueId(), event.getInventory());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getType() == InventoryType.MERCHANT)
            NoVoidTrades.getInstance().tradingPlayers.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        EntityType type = event.getRightClicked().getType();
        if (type == EntityType.VILLAGER || type == EntityType.WANDERING_TRADER)
            if (Utils.isMoving(event.getPlayer())) {
                event.setCancelled(true);
            }
    }
}
