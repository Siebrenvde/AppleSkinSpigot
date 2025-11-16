package com.jmatt.appleskinspigot;

import org.bukkit.Bukkit;

public final class ServerVersion {

    private static final int MINOR;
    private static final int PATCH;

    static {
        final String[] version = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        MINOR = Integer.parseInt(version[1]);
        PATCH = version.length > 2 ? Integer.parseInt(version[2]) : 0;
    }

    public static boolean isHigherThanOrEqualTo(final int minor, final int patch) {
        return MINOR > minor || (MINOR == minor && PATCH >= patch);
    }

    private ServerVersion() {

    }

}
