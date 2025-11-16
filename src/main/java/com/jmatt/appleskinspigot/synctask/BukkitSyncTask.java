package com.jmatt.appleskinspigot.synctask;

import com.jmatt.appleskinspigot.AppleSkinSpigot;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class BukkitSyncTask extends BukkitRunnable {

    private final Player player;
    private final SyncTask task;

    public BukkitSyncTask(final Player player) {
        this.player = player;
        this.task = new SyncTask(player);
        this.runTaskTimer(
            AppleSkinSpigot.getInstance(),
            SyncTask.DELAY_TICKS,
            SyncTask.PERIOD_TICKS
        );
    }

    @Override
    public void run() {
        if (this.player.isOnline()) {
            this.task.run();
        } else {
            this.cancel();
        }
    }

}
