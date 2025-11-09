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

    LoginListener(AppleSkinSpigot plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (plugin.syncTask() == null) plugin.createSyncTask();
        plugin.syncTask().removePreviousLevels(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        plugin.syncTask().removePreviousLevels(event.getPlayer().getUniqueId());
        List<Player> players = Bukkit.getOnlinePlayers().stream()
            .filter(p -> !p.equals(event.getPlayer()))
            .collect(Collectors.toList());
        if (players.isEmpty()) plugin.cancelSyncTask();
    }

}
