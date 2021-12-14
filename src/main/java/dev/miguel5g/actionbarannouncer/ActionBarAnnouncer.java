package dev.miguel5g.actionbarannouncer;

import dev.miguel5g.actionbarannouncer.commands.AbaCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ActionBarAnnouncer extends JavaPlugin {
    private List<String> messages = new ArrayList<>();
    private int currentIndex = 0;
    private int interval = 0;

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
