package dev.miguel5g.actionbarannouncer.events;

import dev.miguel5g.actionbarannouncer.ActionBarAnnouncer;
import dev.miguel5g.actionbarannouncer.ChatUtils;
import dev.miguel5g.actionbarannouncer.MessagesManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final ActionBarAnnouncer plugin;

    public PlayerJoinListener(ActionBarAnnouncer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean isFirstJoin = !player.hasPlayedBefore();
        boolean isEnableFirstJoinMessage = MessagesManager.instance.firstJoinMessage != null;
        boolean isEnableJoinMessage = MessagesManager.instance.joinMessage != null;

        if (!(isFirstJoin && isEnableFirstJoinMessage) && !isEnableJoinMessage) return;

        boolean isPlaySoundEnable = (isFirstJoin && isEnableFirstJoinMessage && MessagesManager.instance.isEnableFirstJoinSound) || (isEnableJoinMessage && MessagesManager.instance.isEnableJoinSound);

        String message = ((isFirstJoin && isEnableFirstJoinMessage)
                ? MessagesManager.instance.firstJoinMessage
                : MessagesManager.instance.joinMessage
        ).replaceAll("<player>", player.getDisplayName());

        Bukkit.getScheduler().scheduleSyncDelayedTask(
                this.plugin,
                () -> {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
                    if (isPlaySoundEnable)
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
                },
                50
        );
    }
}
