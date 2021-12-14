package dev.miguel5g.actionbarannouncer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesManager {
    private final ActionBarAnnouncer plugin;
    private int currentIndex = 0;
    private int taskRef;

    public static MessagesManager instance;

    public List<String> messages;
    public int interval = 0;

    private MessagesManager(ActionBarAnnouncer plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.loadData();
        this.setInterval();
    }

    private void loadData() {
        // Get plugin config yaml
        FileConfiguration config = this.plugin.getConfig();

        // Get data from config
        this.messages = config.getStringList("messages");
        this.interval = config.getInt("interval", 10);

        // Display the number of messages loaded in the console
        int messagesLength = this.messages.toArray().length;

        this.plugin
                .getServer()
                .getConsoleSender()
                .sendMessage(ChatUtils.PREFIX + "§7Loaded §f" + messagesLength + "§7 messages from config");
    }

    private void setInterval() {
        // Validate if array length is > 0
        if (this.messages.toArray().length == 0) return;

        Runnable executor = () -> {
            // Check if the current index exists
            if (this.messages.toArray().length == this.currentIndex) this.currentIndex = 0;

            // Get the raw message and parse to TextComponent
            String rawMessage = this.messages.get(this.currentIndex);
            BaseComponent[] message = TextComponent.fromLegacyText(ChatUtils.parseColors(rawMessage));

            // Get all online players and send the message
            this.plugin
                    .getServer()
                    .getOnlinePlayers()
                    .forEach((player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message)));

            // Update the current index
            this.currentIndex++;
        };

        // Set te interval task
        this.taskRef = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, executor, 0, 20L * this.interval);
    }

    public static void init(ActionBarAnnouncer plugin) {
        MessagesManager.instance = new MessagesManager(plugin);
    }

    public void reload() {
        this.currentIndex = 0;
        this.interval = 0;
        this.messages = null;

        Bukkit.getScheduler().cancelTask(this.taskRef);

        this.plugin.reloadConfig();
        this.loadData();
        this.setInterval();
    }
}
