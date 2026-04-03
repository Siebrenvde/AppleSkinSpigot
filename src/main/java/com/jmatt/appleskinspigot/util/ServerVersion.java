package com.jmatt.appleskinspigot.util;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ServerVersion {

    private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)(?:\\.(\\d+))?");

    private static final int YEAR;
    private static final int DROP;
    private static final int HOTFIX;

    static {
        final Matcher matcher = VERSION_PATTERN.matcher(Bukkit.getServer().getBukkitVersion());
        if (!matcher.find()) throw new IllegalStateException("Failed to determine server version");

        YEAR = Integer.parseInt(matcher.group(1));
        DROP = Integer.parseInt(matcher.group(2));
        HOTFIX = matcher.group(3) != null ? Integer.parseInt(matcher.group(3)) : 0;
    }

    public static boolean isHigherThanOrEqualTo(final int year, final int drop, final int hotfix) {
        return YEAR > year || (YEAR == year && (DROP > drop || (DROP == drop && HOTFIX >= hotfix)));
    }

    private ServerVersion() {

    }

}
