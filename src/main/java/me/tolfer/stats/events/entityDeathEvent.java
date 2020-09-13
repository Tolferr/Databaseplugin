package me.tolfer.stats.events;

import me.tolfer.stats.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class entityDeathEvent implements Listener {

    private Stats plugin;

    public entityDeathEvent(Stats plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player player = (Player) event.getEntity().getKiller();
            plugin.data.addPoints(player.getUniqueId(), 1);
            player.sendMessage(ChatColor.AQUA + "+1 Points");
        }
    }
}
