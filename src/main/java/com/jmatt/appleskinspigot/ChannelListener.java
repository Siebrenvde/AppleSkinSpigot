package com.jmatt.appleskinspigot;

import com.jmatt.appleskinspigot.synctask.BukkitSyncTask;
import com.jmatt.appleskinspigot.synctask.SyncTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public final class ChannelListener implements Listener {

    @EventHandler
    private void onPlayerRegisterChannel(final PlayerRegisterChannelEvent event) {
        if (event.getChannel().equals(AppleSkinSpigot.SATURATION_KEY)) {
            new BukkitSyncTask(event.getPlayer()).runTaskTimer(
                AppleSkinSpigot.getInstance(),
                SyncTask.DELAY_TICKS,
                SyncTask.PERIOD_TICKS
            );
        }
    }

}
