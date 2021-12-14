package dev.miguel5g.actionbarannouncer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ChatUtils {
    public static final String PREFIX = "§a[ActionBarAnnouncer] §r";

    public static String parseColors(String text) {
        if (text == null) return null;

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void logger(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatUtils.PREFIX + message);
    }
}
