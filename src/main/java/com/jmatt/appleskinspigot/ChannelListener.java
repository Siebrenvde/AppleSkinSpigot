package com.jmatt.appleskinspigot;

import com.jmatt.appleskinspigot.synctask.BukkitSyncTask;
import com.jmatt.appleskinspigot.synctask.FoliaSyncTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

public final class ChannelListener implements Listener {

    @EventHandler
    private void onPlayerRegisterChannel(final PlayerRegisterChannelEvent event) {
        if (event.getChannel().equals(AppleSkinSpigot.SATURATION_KEY)) {
            if (AppleSkinSpigot.isPaper()) {
                new FoliaSyncTask(event.getPlayer());
            } else {
                new BukkitSyncTask(event.getPlayer());
            }
        }
    }

}
