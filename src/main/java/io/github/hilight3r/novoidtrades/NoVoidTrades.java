package io.github.hilight3r.novoidtrades;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;

public final class NoVoidTrades extends JavaPlugin {
    private static NoVoidTrades instance;
    private final Logger logger = getLogger();

    public final Map<UUID, Integer> movingPlayers = new HashMap<>();
    public final Map<UUID, Inventory> tradingPlayers = new HashMap<>();

    public static NoVoidTrades getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        (instance = this).saveDefaultConfig();

        logger.info("");
        logger.info(Utils.color("&a| &fPlugin &eNoVoidTrades &8| &fVersion &e" + this.getDescription().getVersion()));
        logger.info(Utils.color("&a| &fDeveloper &eHIlight3R &8- &6github.com/HIlight3R"));
        logger.info("");

        new BukkitRunnable() {

            @Override
            public void run() {
                Iterator<Entry<UUID, Integer>> movingPlayersIterator = movingPlayers.entrySet().iterator();

                while (movingPlayersIterator.hasNext()) {
                    Entry<UUID, Integer> entry = movingPlayersIterator.next();
                    Player player = Bukkit.getPlayer(entry.getKey());
                    if (player != null && !player.isOnline()) {
                        movingPlayersIterator.remove();
                        continue;
                    }

                    entry.setValue(entry.getValue() + 1);
                }

                for (Entry<UUID, Inventory> entry : tradingPlayers.entrySet()) {
                    Player player = Bukkit.getPlayer(entry.getKey());
                    Inventory villagerInventory = entry.getValue();
                    if (player != null && villagerInventory.getLocation() != null) {
                        if (!player.isOnline()) {
                            movingPlayersIterator.remove();
                            continue;
                        }

                        if (!Utils.checkDistance(player.getLocation(), villagerInventory.getLocation()) && player.getOpenInventory().getType() == InventoryType.MERCHANT)
                            player.getOpenInventory().close();
                    }
                }
            }
        }.runTaskTimer(this, 1, 1);

        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }
}
