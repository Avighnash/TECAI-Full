package sebagius7110.TECAI;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import sebagius7110.TECAI.commands.LagCheckCommand;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.UUID;

public class TECAI extends JavaPlugin implements Listener {

    File chat;

    public void log(String msg) {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    private void firstStart() {
        new File(getDataFolder() + File.separator + "CHT FILES").mkdirs();
        log("&aIt seems that you have not completed startup. We will go through this now!");
    }

    boolean lagMessage = false;

    public void onEnable() {
        log("&bLoading/Creating config");
        getConfig().addDefault("startupComplete", false);
        getConfig().addDefault("features.lagMessage.enabled", true);
        getConfig().addDefault("features.lagMessage.usersDisabled", new ArrayList<UUID>());
        getConfig().options().copyDefaults(true);
        saveConfig();
        log("&bComplete!");
        if (!getConfig().getBoolean("startupComplete")) {
            firstStart();
        }
        chat = new File(getDataFolder() + File.separator + "CHT FILES" + File.separator + "myserver.cht");
        try {
            chat.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new BuiltInFeatures(this), this);
        lagMessage = getConfig().getBoolean("features.lagMessage.enabled");
        getCommand("lagcheck").setExecutor(new LagCheckCommand(this));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void writeChatToFile(AsyncPlayerChatEvent e) {
        String toFile = e.getPlayer().getDisplayName() + " ~|~|~ " + e.getMessage() + "\n";

        try {
            Files.write(chat.toPath(), toFile.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void msg(Player p, String msg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }





}
