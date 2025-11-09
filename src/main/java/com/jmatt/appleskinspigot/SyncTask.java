package com.jmatt.appleskinspigot;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SyncTask extends BukkitRunnable {

    private static final float MINIMUM_EXHAUSTION_CHANGE_THRESHOLD = 0.01F;

    private final AppleSkinSpigot plugin;
    private final Map<UUID, Float> previousSaturationLevels;
    private final Map<UUID, Float> previousExhaustionLevels;

    SyncTask(final AppleSkinSpigot plugin) {
        this.plugin = plugin;
        this.previousSaturationLevels = new HashMap<>();
        this.previousExhaustionLevels = new HashMap<>();
    }

    @Override
    public void run() {
        for (final Player player : this.plugin.getServer().getOnlinePlayers()) {
            this.updatePlayer(player);
        }
    }

    private void updatePlayer(final Player player) {
        final float saturation = player.getSaturation();
        final Float previousSaturation = this.previousSaturationLevels.get(player.getUniqueId());
        if (previousSaturation == null || saturation != previousSaturation) {
            player.sendPluginMessage(this.plugin, AppleSkinSpigot.SATURATION_KEY, ByteBuffer.allocate(Float.BYTES).putFloat(saturation).array());
            this.previousSaturationLevels.put(player.getUniqueId(), saturation);
        }

        final float exhaustion = player.getExhaustion();
        final Float previousExhaustion = this.previousExhaustionLevels.get(player.getUniqueId());
        if (previousExhaustion == null || Math.abs(exhaustion - previousExhaustion) >= MINIMUM_EXHAUSTION_CHANGE_THRESHOLD) {
            player.sendPluginMessage(this.plugin, AppleSkinSpigot.EXHAUSTION_KEY, ByteBuffer.allocate(Float.BYTES).putFloat(exhaustion).array());
            this.previousExhaustionLevels.put(player.getUniqueId(), exhaustion);
        }
    }

    void removePreviousLevels(final UUID uuid) {
        this.previousSaturationLevels.remove(uuid);
        this.previousExhaustionLevels.remove(uuid);
    }

}
