package com.jmatt.appleskinspigot;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class AppleSkinSpigot extends JavaPlugin {

    private static AppleSkinSpigot instance;

    public static final String SATURATION_KEY = "appleskin:saturation";
    public static final String EXHAUSTION_KEY = "appleskin:exhaustion";
    public static final String NATURAL_REGENERATION_KEY = "appleskin:natural_regeneration";

    private SyncTask syncTask = null;

    @Override
    public void onEnable() {
        instance = this;

        final String[] version = getServer().getBukkitVersion().split("-")[0].split("\\.");
        final int minor = Integer.parseInt(version[1]);
        final int patch = version.length > 2 ? Integer.parseInt(version[2]) : 0;

        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new LoginListener(this), this);

        final Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, SATURATION_KEY);
        messenger.registerOutgoingPluginChannel(this, EXHAUSTION_KEY);

        // https://github.com/squeek502/AppleSkin/commit/ee4d5316138a5ab4b2406753d4ed717e12f550dd
        if (minor > 21 || (minor == 21 && patch >= 3)) {
            messenger.registerOutgoingPluginChannel(this, NATURAL_REGENERATION_KEY);
            manager.registerEvents(new GameRuleListener(), this);
            if (this.isPaper()) manager.registerEvents(new GameRuleListener.Paper(), this);
        }
    }

    /**
     * {@return the current sync task}
     */
    public SyncTask syncTask() {
        return this.syncTask;
    }

    /**
     * Creates a new sync task and schedules it
     *
     * <p>If a sync task already exists, it will be cancelled</p>
     */
    public void createSyncTask() {
        this.cancelSyncTask();
        this.syncTask = new SyncTask(this);
        this.syncTask.runTaskTimer(this, 0L, 1L);
    }

    /**
     * Cancels the current sync task
     */
    public void cancelSyncTask() {
        if (this.syncTask != null) {
            this.syncTask.cancel();
            this.syncTask = null;
        }
    }

    @Override
    public void onDisable() {
        this.cancelSyncTask();
    }

    public static AppleSkinSpigot getInstance() {
        return instance;
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
