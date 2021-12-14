package dev.miguel5g.actionbarannouncer;

import org.bukkit.ChatColor;

public class ChatUtils {
    public static final String PREFIX = "§a[ActionBarAnnouncer] §r";

    public static String parseColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
