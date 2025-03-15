package com.jmatt.appleskinspigot;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class AppleSkinSpigotPlugin extends JavaPlugin {

    public static AppleSkinSpigotPlugin INSTANCE;

    public static String SATURATION_KEY = "appleskin:saturation";
    public static String EXHAUSTION_KEY = "appleskin:exhaustion";
    public static final String NATURAL_REGENERATION_KEY = "appleskin:natural_regeneration";

    private SyncTask syncTask = null;

    @Override
    public void onEnable() {
        INSTANCE = this;

        String[] version = getServer().getBukkitVersion().split("-")[0].split("\\.");
        int minor = Integer.parseInt(version[1]);
        int patch = version.length > 2 ? Integer.parseInt(version[2]) : 0;

        if(minor < 20 || (minor == 20 && patch <= 4)) {
            SATURATION_KEY = "appleskin:saturation_sync";
            EXHAUSTION_KEY = "appleskin:exhaustion_sync";
        }

        PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new LoginListener(this), this);

        Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, SATURATION_KEY);
        messenger.registerOutgoingPluginChannel(this, EXHAUSTION_KEY);

        // https://github.com/squeek502/AppleSkin/commit/ee4d5316138a5ab4b2406753d4ed717e12f550dd
        if (minor > 21 || (minor == 21 && patch >= 3)) {
            messenger.registerOutgoingPluginChannel(this, NATURAL_REGENERATION_KEY);
            manager.registerEvents(new GameRuleListener(), this);
            if (isPaper()) manager.registerEvents(new GameRuleListener.Paper(), this);
        }
    }

    /**
     * {@return the current sync task}
     */
    public SyncTask syncTask() {
        return syncTask;
    }

    /**
     * Creates a new sync task and schedules it
     * <p>
     * If a sync task already exists, it will be cancelled
     */
    public void createSyncTask() {
        cancelSyncTask();
        syncTask = new SyncTask(this);
        syncTask.runTaskTimer(this, 0L, 1L);
    }

    /**
     * Cancels the current sync task
     */
    public void cancelSyncTask() {
        if (syncTask != null) {
            syncTask.cancel();
            syncTask = null;
        }
    }

    @Override
    public void onDisable() {
        cancelSyncTask();
    }

    /**
     * Checks whether the current server is a Paper server
     * @return <code>true</code> if the server is a Paper server
     */
    private boolean isPaper() {
        try {
            Class.forName("io.papermc.paper.configuration.Configuration");
            return true;
        } catch (ClassNotFoundException ignored) {
            try {
                Class.forName("com.destroystokyo.paper.PaperConfig");
                return true;
            } catch (ClassNotFoundException ignored1) {}
        }
        return false;
    }

}
