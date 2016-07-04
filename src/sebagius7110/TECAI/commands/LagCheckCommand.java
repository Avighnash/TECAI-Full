package sebagius7110.TECAI.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sebagius7110.TECAI.TECAI;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LagCheckCommand implements CommandExecutor {

    TECAI tec;

    public LagCheckCommand(TECAI tecai) {
        tec = tecai;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("TECAI.lagcheckcommand")) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("enable")) {
                        List<UUID> disabled = (List<UUID>) tec.getConfig().getList("features.lagMessage.usersDisabled");
                        if(disabled.contains(p.getUniqueId())) {
                            disabled.remove(p.getUniqueId());
                            p.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "You will now receive lag test messages!");
                            p.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Use /TECAI:lagcheck disable to disable the messages!");
                            tec.getConfig().set("features.lagMessage.usersDisabled", disabled);
                            tec.saveConfig();
                        } else {
                            p.sendMessage(ChatColor.RED + "You have already disabled the lag check message!");
                        }
                    } else if (args[0].equalsIgnoreCase("disable")) {
                        List<UUID> disabled = (List<UUID>) tec.getConfig().getList("features.lagMessage.usersDisabled");
                        if(!disabled.contains(p.getUniqueId())) {
                            disabled.add(p.getUniqueId());
                            p.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "You will no longer receive lag test messages!");
                            p.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "Use /TECAI:lagcheck enable to enable the messages!");
                            tec.getConfig().set("features.lagMessage.usersDisabled", disabled);
                            tec.saveConfig();
                        } else {
                            p.sendMessage(ChatColor.RED + "You have already disabled the lag check message!");
                        }
                    } else {
                        p.sendMessage(ChatColor.DARK_RED + "Syntax: /" + label + " enable|disable");
                    }
                } else {
                    p.sendMessage(ChatColor.DARK_RED + "Syntax: /" + label + " enable|disable");
                }
            } else {
                p.sendMessage(ChatColor.DARK_RED + "You do not have permission to perform this command!");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You must be a player to perform this command");
        }

        return true;
    }
}
