package me.tolfer.stats.events;

import me.tolfer.stats.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class blockBreakEvent implements Listener {

    private Stats plugin;

    public  blockBreakEvent(Stats plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        plugin.data.addBlocks(player.getUniqueId(), 1);
    }

}
