package com.jmatt.appleskinspigot.synctask;

import com.jmatt.appleskinspigot.AppleSkinSpigot;
import org.bukkit.entity.Player;

public final class FoliaSyncTask extends SyncTask {

    public FoliaSyncTask(final Player player) {
        super(player);
        this.tryScheduleTask();
    }

    private void tryScheduleTask() {
        this.player.getScheduler().runAtFixedRate(
            AppleSkinSpigot.getInstance(),
            ignored -> this.run(),
            this::tryScheduleTask,
            DELAY_TICKS,
            PERIOD_TICKS
        );
    }

}
