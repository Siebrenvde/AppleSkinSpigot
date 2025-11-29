package com.jmatt.appleskinspigot.synctask;

import com.jmatt.appleskinspigot.AppleSkinSpigot;
import org.bukkit.entity.Player;

import java.nio.ByteBuffer;

public sealed class SyncTask implements Runnable permits FoliaSyncTask {

    public static final long DELAY_TICKS = 1;
    public static final long PERIOD_TICKS = 1;

    private static final float MINIMUM_EXHAUSTION_CHANGE_THRESHOLD = 0.01F;

    private final Player player;
    private float previousSaturation = -1;
    private float previousExhaustion = -1;

    public SyncTask(final Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        final float saturation = this.player.getSaturation();
        if (saturation != this.previousSaturation) {
            this.player.sendPluginMessage(
                AppleSkinSpigot.getInstance(),
                AppleSkinSpigot.SATURATION_KEY,
                ByteBuffer.allocate(Float.BYTES).putFloat(saturation).array()
            );
            this.previousSaturation = saturation;
        }

        final float exhaustion = this.player.getExhaustion();
        if (Math.abs(exhaustion - this.previousExhaustion) >= MINIMUM_EXHAUSTION_CHANGE_THRESHOLD) {
            this.player.sendPluginMessage(
                AppleSkinSpigot.getInstance(),
                AppleSkinSpigot.EXHAUSTION_KEY,
                ByteBuffer.allocate(Float.BYTES).putFloat(exhaustion).array()
            );
            this.previousExhaustion = exhaustion;
        }
    }

}
