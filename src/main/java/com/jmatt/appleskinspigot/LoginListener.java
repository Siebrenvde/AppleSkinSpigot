package com.jmatt.appleskinspigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.stream.Collectors;

import static com.jmatt.appleskinspigot.AppleSkinSpigot.cancelSyncTask;
import static com.jmatt.appleskinspigot.AppleSkinSpigot.getOrCreateSyncTask;

public final class LoginListener implements Listener {

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        getOrCreateSyncTask().removePreviousLevels(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeave(final PlayerQuitEvent event) {
        getOrCreateSyncTask().removePreviousLevels(event.getPlayer().getUniqueId());
        final List<Player> players = Bukkit.getOnlinePlayers().stream()
            .filter(p -> !p.equals(event.getPlayer()))
            .collect(Collectors.toList());
        if (players.isEmpty()) cancelSyncTask();
    }

}
