package com.jmatt.appleskinspigot.synctask;

import com.jmatt.appleskinspigot.AppleSkinSpigot;
import org.bukkit.entity.Player;

public final class FoliaSyncTask extends SyncTask {

    public FoliaSyncTask(final Player player) {
        super(player);
        player.getScheduler().runAtFixedRate(
            AppleSkinSpigot.getInstance(),
            ignored -> this.run(),
            null,
            DELAY_TICKS,
            PERIOD_TICKS
        );
    }

}
