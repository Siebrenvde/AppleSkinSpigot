package com.jmatt.appleskinspigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;
import java.util.stream.Collectors;

public class LoginListener implements Listener {

    private final AppleSkinSpigot plugin;

    LoginListener(final AppleSkinSpigot plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        if (this.plugin.syncTask() == null) this.plugin.createSyncTask();
        this.plugin.syncTask().removePreviousLevels(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeave(final PlayerQuitEvent event) {
        this.plugin.syncTask().removePreviousLevels(event.getPlayer().getUniqueId());
        final List<Player> players = Bukkit.getOnlinePlayers().stream()
            .filter(p -> !p.equals(event.getPlayer()))
            .collect(Collectors.toList());
        if (players.isEmpty()) this.plugin.cancelSyncTask();
    }

}
