package dev.miguel5g.actionbarannouncer.commands;

import dev.miguel5g.actionbarannouncer.ChatUtils;
import dev.miguel5g.actionbarannouncer.MessagesManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbaCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("aba.admin")) {
            sender.sendMessage("§cVocê não tem permissão para executar este comando.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("§cUso incorreto! Tente utilizar §4/aba help§c.");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("");
            sender.sendMessage("§eSubcomandos disponíveis");
            sender.sendMessage("");
            sender.sendMessage(" §6/aba help §7- §eLista de subcomandos.");
            sender.sendMessage(" §6/aba reload §7- §eRecarrega o plugin.");
            sender.sendMessage(" §6/aba interval §7- §eIntervalo entre mensagens.");
            sender.sendMessage(" §6/aba messages §7- §eMensagens carregadas");
            sender.sendMessage("");
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            MessagesManager.instance.reload();
            sender.sendMessage("§aPlugin recarregado com sucesso!");
            return true;
        }

        if (args[0].equalsIgnoreCase("interval")) {
            sender.sendMessage("§eIntervalo entre as mensagens §6" + MessagesManager.instance.interval + "§e segundos.");
            return true;
        }

        if (args[0].equalsIgnoreCase("messages")) {
            sender.sendMessage("");
            sender.sendMessage("§e" + MessagesManager.instance.messages.toArray().length + " mensagen(s) carregada(s)");
            sender.sendMessage("");

            MessagesManager.instance.messages.forEach((message) -> {
                sender.sendMessage(" §7- §r" + ChatUtils.parseColors(message));
            });

            sender.sendMessage("");
            return true;
        }

        sender.sendMessage("§cUso incorreto! Tente utilizar §4/aba help§c.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) return null;

        List<String> completions = new ArrayList<>();
        List<String> result = new ArrayList<>();

        completions.add("help");
        completions.add("reload");
        completions.add("internal");
        completions.add("messages");

        if (args.length == 1) {
            completions.forEach((completion) -> {
                if (completion.startsWith(args[0])) result.add(completion);
            });

            return result;
        };

        return null;
    }
}
