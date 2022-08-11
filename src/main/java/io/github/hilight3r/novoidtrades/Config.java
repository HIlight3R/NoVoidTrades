package io.github.hilight3r.novoidtrades;

import org.bukkit.configuration.file.FileConfiguration;

public final class Config {
    private static final FileConfiguration config = NoVoidTrades.getInstance().getConfig();

    public static final int TICKS_AFTER_MOVING = config.getInt("config.ticks-after-moving");
    public static final double MAX_DISTANCE = config.getDouble("config.max-distance");

    private Config() {
    }
}
