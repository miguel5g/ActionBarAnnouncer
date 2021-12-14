package dev.miguel5g.actionbarannouncer;

import dev.miguel5g.actionbarannouncer.commands.AbaCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ActionBarAnnouncer extends JavaPlugin {
    @Override
    public void onEnable() {
        this.getServer().getConsoleSender().sendMessage(ChatUtils.PREFIX + "§7Plugin enable");

        // Set command executor
        this.getServer().getPluginCommand("aba").setExecutor(new AbaCommand());

        // Inizialize messages manager
        MessagesManager.init(this);
    }

    @Override
    public void onDisable() {
        this.getServer().getConsoleSender().sendMessage(ChatUtils.PREFIX + "§7Plugin disabled");
    }
}
