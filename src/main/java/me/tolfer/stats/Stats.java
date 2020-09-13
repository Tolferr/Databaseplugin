package me.tolfer.stats;

import me.tolfer.stats.sql.MySQL;
import me.tolfer.stats.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;

public final class Stats extends JavaPlugin implements Listener {

    public MySQL SQL;
    public SQLGetter data;

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Stats] Plugin is enabled!");

        this.SQL = new MySQL();
        this.data = new SQLGetter(this);

        try {
            SQL.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.RED + "[Stats] Database not connected!");
        }

        if(SQL.isConnected()){
            Bukkit.getLogger().info(ChatColor.GREEN + "[Stats] Database is connected!");
            data.createTable();
            getServer().getPluginManager().registerEvents(this, this);

        }
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Stats] Plugin is disabled!");
        SQL.disconnect();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        data.createPlayer(player);
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event){
        if(event.getEntity().getKiller() != null){
            Player player = (Player) event.getEntity().getKiller();
            data.addPoints(player.getUniqueId(), 1);
            player.sendMessage(ChatColor.AQUA + "+1 Points");
        }
    }
}
