package com.jmatt.appleskinspigot;

import io.papermc.paper.event.world.WorldGameRuleChangeEvent;
import org.bukkit.GameRule;
import org.bukkit.GameRules;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;

import java.nio.ByteBuffer;

import static com.jmatt.appleskinspigot.AppleSkinSpigot.NATURAL_REGENERATION_KEY;

public final class GameRuleListener implements Listener {

    private static final GameRule<Boolean> NATURAL_REGENERATION;

    static {
        if (ServerVersion.isHigherThanOrEqualTo(21, 11)) {
            NATURAL_REGENERATION = GameRules.NATURAL_HEALTH_REGENERATION;
        } else {
            //noinspection removal
            NATURAL_REGENERATION = GameRule.NATURAL_REGENERATION;
        }
    }

    @EventHandler
    private void onPlayerRegisterChannel(final PlayerRegisterChannelEvent event) {
        if (event.getChannel().equals(NATURAL_REGENERATION_KEY)) {
            this.sendNaturalRegenState(event.getPlayer());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        if (event.getPlayer().getListeningPluginChannels().contains(NATURAL_REGENERATION_KEY)) {
            this.sendNaturalRegenState(event.getPlayer());
        }
    }

    private void sendNaturalRegenState(final Player player) {
        sendNaturalRegenState(
            player,
            Boolean.TRUE.equals(player.getWorld().getGameRuleValue(NATURAL_REGENERATION))
        );
    }

    private static void sendNaturalRegenState(final Player player, final boolean state) {
        player.sendPluginMessage(
            AppleSkinSpigot.getInstance(),
            NATURAL_REGENERATION_KEY,
            ByteBuffer.allocate(1).put((byte) (state ? 1 : 0)).array()
        );
    }

    public static class Paper implements Listener {

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        public void onGameRuleChange(final WorldGameRuleChangeEvent event) {
            if (event.getGameRule() != NATURAL_REGENERATION) return;
            event.getWorld().getPlayers().forEach(player -> {
                if (player.getListeningPluginChannels().contains(NATURAL_REGENERATION_KEY)) {
                    sendNaturalRegenState(player, Boolean.parseBoolean(event.getValue()));
                }
            });
        }

    }

}
