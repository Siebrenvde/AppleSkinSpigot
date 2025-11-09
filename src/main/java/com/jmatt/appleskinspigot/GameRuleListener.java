package com.jmatt.appleskinspigot;

import io.papermc.paper.event.world.WorldGameRuleChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.nio.ByteBuffer;

public class GameRuleListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(
            AppleSkinSpigot.INSTANCE,
            () -> { if (event.getPlayer().isOnline()) sendNaturalRegenState(event.getPlayer()); },
            20L
        );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        sendNaturalRegenState(event.getPlayer());
    }

    private void sendNaturalRegenState(Player player) {
        sendNaturalRegenState(
            player,
            Boolean.TRUE.equals(player.getWorld().getGameRuleValue(GameRule.NATURAL_REGENERATION))
        );
    }

    private static void sendNaturalRegenState(Player player, boolean state) {
        player.sendPluginMessage(
            AppleSkinSpigot.INSTANCE,
            AppleSkinSpigot.NATURAL_REGENERATION_KEY,
            ByteBuffer.allocate(1).put((byte) (state ? 1 : 0)).array()
        );
    }

    public static class Paper implements Listener {

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onGameRuleChange(WorldGameRuleChangeEvent event) {
            if (event.getGameRule() != GameRule.NATURAL_REGENERATION) return;
            event.getWorld().getPlayers().forEach(player -> {
                sendNaturalRegenState(player, Boolean.parseBoolean(event.getValue()));
            });
        }

    }

}
