package me.tolfer.stats.events;

import me.tolfer.stats.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class playerJoinEvent implements Listener {

    private Stats plugin;

    public  playerJoinEvent(Stats plugin) {
        this.plugin = plugin;
    }

    public playerJoinEvent() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        int points = plugin.data.getPoints(player.getUniqueId());
        plugin.data.createPlayer(player);
        player.sendMessage(ChatColor.AQUA + "You have " + points + " points!");
    }
}
