package com.jmatt.appleskinspigot;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SyncTask extends BukkitRunnable {

    private static final float MINIMUM_EXHAUSTION_CHANGE_THRESHOLD = 0.01F;

    private final Map<UUID, Float> previousSaturationLevels = new HashMap<>();
    private final Map<UUID, Float> previousExhaustionLevels = new HashMap<>();

    @Override
    public void run() {
        for (final Player player : Bukkit.getServer().getOnlinePlayers()) {
            this.updatePlayer(player);
        }
    }

    private void updatePlayer(final Player player) {
        final float saturation = player.getSaturation();
        final Float previousSaturation = this.previousSaturationLevels.get(player.getUniqueId());
        if (previousSaturation == null || saturation != previousSaturation) {
            player.sendPluginMessage(
                AppleSkinSpigot.getInstance(),
                AppleSkinSpigot.SATURATION_KEY,
                ByteBuffer.allocate(Float.BYTES).putFloat(saturation).array()
            );
            this.previousSaturationLevels.put(player.getUniqueId(), saturation);
        }

        final float exhaustion = player.getExhaustion();
        final Float previousExhaustion = this.previousExhaustionLevels.get(player.getUniqueId());
        if (previousExhaustion == null || Math.abs(exhaustion - previousExhaustion) >= MINIMUM_EXHAUSTION_CHANGE_THRESHOLD) {
            player.sendPluginMessage(
                AppleSkinSpigot.getInstance(),
                AppleSkinSpigot.EXHAUSTION_KEY,
                ByteBuffer.allocate(Float.BYTES).putFloat(exhaustion).array())
            ;
            this.previousExhaustionLevels.put(player.getUniqueId(), exhaustion);
        }
    }

    void removePreviousLevels(final UUID uuid) {
        this.previousSaturationLevels.remove(uuid);
        this.previousExhaustionLevels.remove(uuid);
    }

}
