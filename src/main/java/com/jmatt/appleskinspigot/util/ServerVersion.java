package com.jmatt.appleskinspigot.util;

import org.bukkit.Bukkit;

public final class ServerVersion {

    private static final int YEAR;
    private static final int DROP;
    private static final int HOTFIX;

    static {
        final String[] version = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        YEAR = Integer.parseInt(version[0]);
        DROP = Integer.parseInt(version[1]);
        HOTFIX = version.length > 2 ? Integer.parseInt(version[2]) : 0;
    }

    public static boolean isHigherThanOrEqualTo(final int year, final int drop, final int hotfix) {
        return YEAR > year || (YEAR == year && (DROP > drop || (DROP == drop && HOTFIX >= hotfix)));
    }

    private ServerVersion() {

    }

}
