package me.tolfer.stats.sql;

import me.tolfer.stats.Stats;
import me.tolfer.stats.events.blockBreakEvent;
import me.tolfer.stats.events.entityDeathEvent;
import me.tolfer.stats.events.playerJoinEvent;
import me.tolfer.stats.gui.StatsGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private Stats plugin;

    public MySQL(Stats plugin) {
        this.plugin = plugin;
    }

    private Connection connection;

    private String host = "localhost";
    private String port = "3306";
    private String database = "player-data";
    private String username = "root";
    private String password = "";

    public void enableSQL(){
        try {
            this.connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.RED + "[Stats] Database not connected!");
        }
    }

    public void enableSQLListener(){
        if (plugin.SQL.isConnected()) {
            Bukkit.getLogger().info(ChatColor.GREEN + "[Stats] Database is connected!");
            plugin.data.createTable();
            plugin.getServer().getPluginManager().registerEvents(new playerJoinEvent(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new blockBreakEvent(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new entityDeathEvent(plugin), plugin);
            plugin.getServer().getPluginManager().registerEvents(new StatsGui(plugin), plugin);
        }
    }

    public boolean isConnected(){
        return (connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {

        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" +
                            host + ":" + port + "/" + database + "?useSSL=false",
                    username, password);
        }
    }

    public void disconnect(){
        if(isConnected()){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection(){
        return connection;
    }
}
