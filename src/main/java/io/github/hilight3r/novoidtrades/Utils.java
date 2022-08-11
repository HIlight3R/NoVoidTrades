package io.github.hilight3r.novoidtrades;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

public final class Utils {
    private Utils() {
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static boolean isMoving(Player player) {
        Integer value = NoVoidTrades.getInstance().movingPlayers.get(player.getUniqueId());
        return value != null && value <= Config.TICKS_AFTER_MOVING;
    }

    public static boolean checkDistance(@NotNull Location first, @NotNull Location second) {
        return first.distance(second) <= Config.MAX_DISTANCE;
    }

    public static void closeMerchantInventory(@NotNull Player player) {
        if (player.getOpenInventory().getType() == InventoryType.MERCHANT) {
            player.getOpenInventory().close();
        }
    }
}
