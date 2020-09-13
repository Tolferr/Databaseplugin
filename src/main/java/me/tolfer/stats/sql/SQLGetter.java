package me.tolfer.stats.sql;

import me.tolfer.stats.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLGetter {

    private Stats plugin;

    public SQLGetter(Stats plugin) {
        this.plugin = plugin;
    }

    // Maak een tabel aan met de naam stats
    public void createTable() {
        PreparedStatement ps;
        try {
            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS stats "
                    + "(NAME VARCHAR(100), UUID VARCHAR(100), POINTS INT(100), PRIMARY KEY (NAME))");
            ps.executeUpdate();
            Bukkit.getLogger().info(ChatColor.RED + "[DEBUG] Table is aangemaakt!");
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.RED + "[DEBUG] Fout bij het maken van een Table!");
        }
    }

    public void createPlayer(Player player) {
        try {
            UUID uuid = player.getUniqueId();
            // Als een speler niet voorkomt in de database, dan word er een nieuwe player aangemaakt.
            if (!exists(uuid)) {
                PreparedStatement ps2 = plugin.SQL.getConnection().prepareStatement("INSERT IGNORE INTO stats"
                        + " (NAME, UUID) VALUE  (?,?)");
                ps2.setString(1, player.getName());
                ps2.setString(2, uuid.toString());
                ps2.executeUpdate();
                player.sendMessage(ChatColor.RED + "[DEBUG] Je bent toegevoegd aan de database");
                Bukkit.getLogger().info(ChatColor.RED + "[DEBUG] Player toegevoegd aan database");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.RED + "[DEBUG] Fout bij het toevoegen van player");
        }
    }

    // Check of de player al voorkomt in de database
    public boolean exists(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());

            ResultSet result = ps.executeQuery();
            if (result.next()) {

                // player is gevonden
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return false;
    }

    public void addPoints(UUID uuid, int points) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("UPDATE stats SET POINTS=? WHERE UUID=?");
            ps.setInt(1, (getPoints(uuid) + points));
            ps.setString(2, uuid.toString());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getLogger().info(ChatColor.RED + "[DEBUG] Error addPoints");
        }
    }

    public int getPoints(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT  POINTS FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            int points = 0;
            if (rs.next()) {
                points = rs.getInt("POINTS");
                return points;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void emptyTable() {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("TRUNCATE stats");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(UUID uuid) {
        try {
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM stats WHERE UUID=?");
            ps.setString(1, uuid.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

