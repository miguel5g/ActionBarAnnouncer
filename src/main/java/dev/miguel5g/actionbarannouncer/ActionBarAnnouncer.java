package dev.miguel5g.actionbarannouncer;

import dev.miguel5g.actionbarannouncer.commands.AbaCommand;
import dev.miguel5g.actionbarannouncer.events.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ActionBarAnnouncer extends JavaPlugin {
    @Override
    public void onEnable() {
        ChatUtils.logger("§7Plugin enable");

        getServer().getPluginCommand("aba").setExecutor(new AbaCommand());
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        MessagesManager.init(this);
    }

    @Override
    public void onDisable() {
        ChatUtils.logger("§7Plugin disabled");
    }
}
