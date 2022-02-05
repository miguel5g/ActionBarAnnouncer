package dev.miguel5g.actionbarannouncer.commands;

import dev.miguel5g.actionbarannouncer.MessagesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AbaCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("aba.admin")) {
            sender.sendMessage(MessagesManager.instance.messages.get("no_permission"));
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(MessagesManager.instance.messages.get("invalid_subcommand"));
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("");
            sender.sendMessage("§eAvailable subcommands");
            sender.sendMessage("");
            sender.sendMessage(" §6/aba help §7- §eList of subcommands");
            sender.sendMessage(" §6/aba reload §7- §eReload settings.");
            sender.sendMessage("");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            MessagesManager.instance.reload();
            sender.sendMessage(MessagesManager.instance.messages.get("reload_success"));
            return true;
        }

        sender.sendMessage(MessagesManager.instance.messages.get("invalid_subcommand"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) return new ArrayList<>();

        List<String> completions = new ArrayList<>();
        List<String> result = new ArrayList<>();

        completions.add("help");
        completions.add("reload");

        if (args.length == 1) {
            completions.forEach((completion) -> {
                if (completion.startsWith(args[0])) result.add(completion);
            });

            return result;
        };

        return new ArrayList<>();
    }
}
