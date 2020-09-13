package me.tolfer.stats.gui;

import me.tolfer.stats.Stats;
import me.tolfer.stats.events.entityDeathEvent;
import me.tolfer.stats.events.playerJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Arrays;


public class StatsGui implements Listener {

    private Stats plugin;

    public StatsGui(Stats plugin) {
        this.plugin = plugin;
    }
    public static Inventory inv;

    public void initializeItems(){
        inv.addItem(createGuiItem(Material.STONE, "Player_stats", "Stone:" ));

    }
    public void EnableGui(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public ItemStack createGuiItem(Material material,  String name, String... lore){
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public void openInventory(HumanEntity ent){
        inv = Bukkit.createInventory(null,  9, "Player stats");
        initializeItems();
        ent.openInventory(inv);
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getInventory() != inv) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();

        if(clickedItem == null || clickedItem.getType() == Material.AIR) return;

        Player player = (Player) e.getWhoClicked();

        player.sendMessage("je klikte op slot " + e.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e){
        if(e.getInventory() == inv){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerClockClick(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getPlayer().getItemInHand().equals(new ItemStack(Material.DIAMOND_AXE))) {


                Inventory inv = Bukkit.createInventory(null, 9, ChatColor.GREEN + "" + ChatColor.BOLD + e.getPlayer().getName() + " stat's");

                int kills = e.getPlayer().getLevel();
                inv.setItem(0,new ItemStack(Material.EXP_BOTTLE, kills));
            }
        }
    }
}
