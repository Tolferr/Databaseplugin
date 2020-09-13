package me.tolfer.stats;

import me.tolfer.stats.commands.StatsCommand;
import me.tolfer.stats.gui.StatsGui;
import me.tolfer.stats.sql.MySQL;
import me.tolfer.stats.sql.SQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Stats extends JavaPlugin {

    public MySQL SQL;
    public SQLGetter data;
    public StatsGui statsgui;

    @Override
    public void onEnable() {
        getCommand("stats").setExecutor(new StatsCommand(this));

        this.SQL = new MySQL(this);
        this.data = new SQLGetter(this);
        this.statsgui = new StatsGui(this);

        SQL.enableSQL();
        SQL.enableSQLListener();

        statsgui.EnableGui();
    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }
}
