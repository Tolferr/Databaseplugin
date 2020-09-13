package me.tolfer.stats.commands;

import me.tolfer.stats.Stats;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {

    private Stats plugin;

    public StatsCommand(Stats plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("openen.... Gui");
            plugin.statsgui.openInventory(player);

        }
        return false;
    }

}
