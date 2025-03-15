package com.jmatt.appleskinspigot;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

public class AppleSkinSpigotPlugin extends JavaPlugin {

    public static String SATURATION_KEY = "appleskin:saturation";
    public static String EXHAUSTION_KEY = "appleskin:exhaustion";

    private SyncTask syncTask = null;

    @Override
    public void onEnable() {
        String[] version = getServer().getBukkitVersion().split("-")[0].split("\\.");
        int minor = Integer.parseInt(version[1]);
        int patch = version.length > 2 ? Integer.parseInt(version[2]) : 0;

        if(minor < 20 || (minor == 20 && patch <= 4)) {
            SATURATION_KEY = "appleskin:saturation_sync";
            EXHAUSTION_KEY = "appleskin:exhaustion_sync";
        }

        getServer().getPluginManager().registerEvents(new LoginListener(this), this);

        Messenger messenger = getServer().getMessenger();
        messenger.registerOutgoingPluginChannel(this, SATURATION_KEY);
        messenger.registerOutgoingPluginChannel(this, EXHAUSTION_KEY);
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

}
