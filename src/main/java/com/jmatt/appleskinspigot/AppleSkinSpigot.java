package com.jmatt.appleskinspigot;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public final class AppleSkinSpigot extends JavaPlugin {

    private static @Nullable AppleSkinSpigot instance;

    public static final String SATURATION_KEY = "appleskin:saturation";
    public static final String EXHAUSTION_KEY = "appleskin:exhaustion";
    public static final String NATURAL_REGENERATION_KEY = "appleskin:natural_regeneration";

    private static @Nullable SyncTask syncTask = null;

    @Override
    public void onEnable() {
        instance = this;

        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new LoginListener(), this);

        final Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, SATURATION_KEY);
        messenger.registerOutgoingPluginChannel(this, EXHAUSTION_KEY);

        // https://github.com/squeek502/AppleSkin/commit/ee4d5316138a5ab4b2406753d4ed717e12f550dd
        if (ServerVersion.isHigherThanOrEqualTo(21, 3)) {
            messenger.registerOutgoingPluginChannel(this, NATURAL_REGENERATION_KEY);
            manager.registerEvents(new GameRuleListener(), this);
            if (this.isPaper()) manager.registerEvents(new GameRuleListener.Paper(), this);
        }
    }

    /**
     * Returns the current sync task or creates a new one if it isn't running
     *
     * @return the current sync task
     */
    public static SyncTask getOrCreateSyncTask() {
        if (syncTask == null) createSyncTask();
        return syncTask;
    }

    /**
     * Creates a new sync task and schedules it
     *
     * <p>If a sync task already exists, it will be cancelled</p>
     */
    private static void createSyncTask() {
        cancelSyncTask();
        syncTask = new SyncTask();
        syncTask.runTaskTimer(getInstance(), 0L, 1L);
    }

    /**
     * Cancels the current sync task
     */
    public static void cancelSyncTask() {
        if (syncTask != null) {
            syncTask.cancel();
            syncTask = null;
        }
    }

    @Override
    public void onDisable() {
        cancelSyncTask();
    }

    public static AppleSkinSpigot getInstance() {
        return Objects.requireNonNull(instance);
    }

    /**
     * Checks whether the current server is a Paper server
     *
     * @return <code>true</code> if the server is a Paper server
     */
    private boolean isPaper() {
        try {
            Class.forName("io.papermc.paper.configuration.Configuration");
            return true;
        } catch (final ClassNotFoundException ignored) {
            try {
                Class.forName("com.destroystokyo.paper.PaperConfig");
                return true;
            } catch (final ClassNotFoundException ignored1) { }
        }
        return false;
    }

}
