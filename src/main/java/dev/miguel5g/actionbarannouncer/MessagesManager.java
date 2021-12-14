package dev.miguel5g.actionbarannouncer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class MessagesManager {
    private final ActionBarAnnouncer plugin;
    private int currentIndex = 0;
    private int taskRef;

    public static MessagesManager instance;

    public int announcerInterval = 0;
    public List<String> announcerMessages;
    public HashMap<String, String> messages = new HashMap<>();
    public String joinMessage;
    public String firstJoinMessage;

    private MessagesManager(ActionBarAnnouncer plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.loadData();
        this.setAnnouncerTask();
    }

    private void loadData() {
        FileConfiguration config = this.plugin.getConfig();

        messages.put("no_permission", ChatUtils.parseColors(config.getString("messages.no_permission", "&cYou do not have permission to run this command.")));
        messages.put("invalid_subcommand", ChatUtils.parseColors(config.getString("messages.invalid_subcommand", "&cIncorrect use! Try to use &4/aba help&c.")));
        messages.put("reload_success", ChatUtils.parseColors(config.getString("messages.reload_success", "&aSettings reloaded successfully!")));

        this.announcerInterval = config.getInt("announcer.interval", 10);
        this.announcerMessages = config.getStringList("announcer.messages");
        this.joinMessage = ChatUtils.parseColors(config.getString("player_join.message", null));
        this.firstJoinMessage = ChatUtils.parseColors(config.getString("player_first_join.message", null));

        int messagesLength = this.announcerMessages.toArray().length;

        if (this.joinMessage != null) messagesLength++;
        if (this.firstJoinMessage != null) messagesLength++;

        ChatUtils.logger("§7Loaded §f" + messagesLength + "§7 messages from config");
    }

    private void setAnnouncerTask() {
        if (this.announcerMessages.toArray().length == 0) return;

        Runnable executor = () -> {
            if (this.announcerMessages.toArray().length == this.currentIndex) this.currentIndex = 0;

            String rawMessage = this.announcerMessages.get(this.currentIndex);
            BaseComponent[] message = TextComponent.fromLegacyText(ChatUtils.parseColors(rawMessage));

            Bukkit
                    .getOnlinePlayers()
                    .forEach((player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message)));

            this.currentIndex++;
        };

        this.taskRef = Bukkit
                .getScheduler()
                .scheduleSyncRepeatingTask(this.plugin, executor, 0, 20L * this.announcerInterval);
    }

    public static void init(ActionBarAnnouncer plugin) {
        MessagesManager.instance = new MessagesManager(plugin);
    }

    public void reload() {
        ChatUtils.logger("§7Reloading plugin");

        this.currentIndex = 0;
        this.announcerInterval = 0;
        this.announcerMessages = null;

        Bukkit.getScheduler().cancelTask(this.taskRef);

        this.plugin.saveDefaultConfig();
        this.plugin.reloadConfig();
        this.loadData();
        this.setAnnouncerTask();
    }
}
