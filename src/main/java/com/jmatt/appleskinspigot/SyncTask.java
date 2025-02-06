package com.jmatt.appleskinspigot;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SyncTask extends BukkitRunnable {

    private static final float MINIMUM_EXHAUSTION_CHANGE_THRESHOLD = 0.01F;

    private final AppleSkinSpigotPlugin plugin;
    private final Map<UUID, Float> previousSaturationLevels;
    private final Map<UUID, Float> previousExhaustionLevels;

    SyncTask(AppleSkinSpigotPlugin plugin) {
        this.plugin = plugin;
        previousSaturationLevels = new HashMap<>();
        previousExhaustionLevels = new HashMap<>();
    }

    @Override
    public void run() {
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            updatePlayer(player);
        }
    }

    private void updatePlayer(Player player) {
        float saturation = player.getSaturation();
        Float previousSaturation = previousSaturationLevels.get(player.getUniqueId());
        if(previousSaturation == null || saturation != previousSaturation) {
            player.sendPluginMessage(plugin, AppleSkinSpigotPlugin.SATURATION_KEY, ByteBuffer.allocate(Float.BYTES).putFloat(saturation).array());
            previousSaturationLevels.put(player.getUniqueId(), saturation);
        }

        float exhaustion = player.getExhaustion();
        Float previousExhaustion = previousExhaustionLevels.get(player.getUniqueId());
        if(previousExhaustion == null || Math.abs(exhaustion - previousExhaustion) >= MINIMUM_EXHAUSTION_CHANGE_THRESHOLD) {
            player.sendPluginMessage(plugin, AppleSkinSpigotPlugin.EXHAUSTION_KEY, ByteBuffer.allocate(Float.BYTES).putFloat(exhaustion).array());
            previousExhaustionLevels.put(player.getUniqueId(), exhaustion);
        }
    }

    void removePreviousLevels(UUID uuid) {
        previousSaturationLevels.remove(uuid);
        previousExhaustionLevels.remove(uuid);
    }

}
