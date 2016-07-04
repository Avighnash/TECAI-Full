package sebagius7110.TECAI;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class LagCheck extends BukkitRunnable {

    private List<Double> getTpsAfterPing(List<Integer> moni, Player p) {
        List<Double> tpss = new ArrayList<Double>();

        double tps = LagCheck.getTPS();
        tpss.add(tps);

        new BukkitRunnable() {
            @Override
            public void run() {

                double tps = LagCheck.getTPS();
                tpss.add(tps);

                new BukkitRunnable() {
                    @Override
                    public void run() {

                        double tps = LagCheck.getTPS();
                        tpss.add(tps);
                        new BukkitRunnable() {
                            @Override
                            public void run() {

                                double tps = LagCheck.getTPS();
                                tpss.add(tps);
                                sendLagMessage(p, moni, tpss);

                            }
                        }.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("TECAI"), 20l);

                    }
                }.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("TECAI"), 20l);

            }
        }.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("TECAI"), 20l);


        return tpss;
    }

    private void sendLagMessage(Player p, List<Integer> moni, List<Double> tps) {
        int ping1 = moni.get(0);
        int ping2 = moni.get(1);
        int ping3 = moni.get(2);
        int ping4 = moni.get(3);
        int averagePing = (ping1 + ping2 + ping3 + ping4) / 4;

        double tps1 = tps.get(0);
        double tps2 = tps.get(1);
        double tps3 = tps.get(2);
        double tps4 = tps.get(3);
        double averageTps = (tps1 + tps2 + tps3 + tps4) / 4.0;

        msg(p, "&6Average Ping: " + averagePing + "ms");
        msg(p, "&6Pings Counted: " + ping1 + "ms, " + ping2 + "ms, " + ping3 + "ms, " + ping4 + "ms");
        msg(p, "&6Average Tps: " + averageTps + "tps");
        msg(p, "&6Tps Counted: " + tps1 + "tps, " + tps2 + "tps, " + tps3 + "tps, " + tps4 + "tps");
        TextComponent message1 = new TextComponent( "Don't want to receive this message anymore? " );
        message1.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        TextComponent message2 = new TextComponent( "Click Here" );
        message2.setColor(net.md_5.bungee.api.ChatColor.RED);
        message2.setBold(true);
        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/TECAI:lagcheck disable"));

        message1.addExtra(message2);
        p.spigot().sendMessage(message1);

    }

    public void msg(Player p, String msg) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public List<Integer> monitorPing(Player p) {

        List<Integer> moni = new ArrayList<Integer>();
        moni.add(getPing(p));
        new BukkitRunnable() {
            @Override
            public void run() {

                moni.add(getPing(p));
                new BukkitRunnable() {
                    @Override
                    public void run() {

                        moni.add(getPing(p));
                        new BukkitRunnable() {
                            @Override
                            public void run() {

                                moni.add(getPing(p));
                                getTpsAfterPing(moni, p);

                            }
                        }.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("TECAI"), 20l);

                    }
                }.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("TECAI"), 20l);

            }
        }.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("TECAI"), 20l);
        return moni;
    }

    public int getPing(Player player) {

        int rPing = -1;

        try {
            String bukkitversion = Bukkit.getServer().getClass().getPackage()
                    .getName().substring(23);
            Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit."
                    + bukkitversion + ".entity.CraftPlayer");
            Object handle = craftPlayer.getMethod("getHandle").invoke(player);

            rPing = (Integer) handle.getClass().getDeclaredField("ping")
                    .get(handle);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return rPing;
    }

    public static int TICK_COUNT= 0;
    public static long[] TICKS= new long[600];
    public static long LAST_TICK= 0L;

    public static double getTPS()
    {
        return getTPS(100);
    }

    public static double getTPS(int ticks)
    {
        if (TICK_COUNT< ticks) {
            return 20.0D;
        }
        int target = (TICK_COUNT- 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];

        return ticks / (elapsed / 1000.0D);
    }

    public static long getElapsed(int tickID)
    {
        if (TICK_COUNT- tickID >= TICKS.length)
        {
        }

        long time = TICKS[(tickID % TICKS.length)];
        return System.currentTimeMillis() - time;
    }

    public void run()
    {
        TICKS[(TICK_COUNT% TICKS.length)] = System.currentTimeMillis();

        TICK_COUNT+= 1;
    }

}
