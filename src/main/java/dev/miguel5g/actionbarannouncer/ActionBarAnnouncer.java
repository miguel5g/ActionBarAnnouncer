package dev.miguel5g.actionbarannouncer;

import dev.miguel5g.actionbarannouncer.commands.AbaCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ActionBarAnnouncer extends JavaPlugin {
    @Override
    public void onEnable() {
        ChatUtils.logger("§7Plugin enable");

        // Set command executor
        this.getServer().getPluginCommand("aba").setExecutor(new AbaCommand());

        // Initialize messages manager
        MessagesManager.init(this);
    }

    @Override
    public void onDisable() {
        ChatUtils.logger("§7Plugin disabled");
    }
}
