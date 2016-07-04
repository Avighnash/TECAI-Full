package sebagius7110.TECAI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

class BuiltInFeatures implements Listener {

    private TECAI tec;

    public BuiltInFeatures(TECAI tecai) {
        tec = tecai;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void chatFeatures_LAG(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        if (message.contains("lag")) {
            if (tec.lagMessage) {
                Player p = e.getPlayer();
                if(tec.getConfig().getList("features.lagMessage.usersDisabled").contains(p.getUniqueId()))
                    return;

                tec.msg(p, "&6Monitoring your ping for three seconds and getting recent tps.");
                tec.msg(p, "This can take a few moments...");
                LagCheck lc = new LagCheck();
                lc.monitorPing(p);

            }
        }
    }

}
