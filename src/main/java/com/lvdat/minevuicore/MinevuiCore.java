package com.lvdat.minevuicore;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MinevuiCore extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);

        MinevuiCommand commandExecutor = new MinevuiCommand(this);
        getCommand("minevui").setExecutor(commandExecutor);
        getCommand("minevui").setTabCompleter(commandExecutor);

        String serverVersion = Bukkit.getVersion();

        getLogger().info(">>> MinevuiCore for Minevui Network!<<<");
        getLogger().info("> Server version: " + serverVersion);
        getLogger().info("> Online player: " + Bukkit.getOnlinePlayers().size());
        getLogger().info("> Max player: " + Bukkit.getMaxPlayers());
        getLogger().info("> Running port: " + Bukkit.getPort());
        getLogger().info("");
    }

    @Override
    public void onDisable() {
        getLogger().info("MinevuiCore disabled");
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        EntityType entityType = entity.getType();
        FileConfiguration config = getConfig();

        // Adjust health if configured
        if (config.contains("mobs." + entityType.name() + ".health")) {
            double health = config.getDouble("mobs." + entityType.name() + ".health");
            entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            entity.setHealth(health);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity damager) {
            EntityType entityType = damager.getType();
            FileConfiguration config = getConfig();

            // Adjust damage if configured
            if (config.contains("mobs." + entityType.name() + ".damage")) {
                double damage = config.getDouble("mobs." + entityType.name() + ".damage");
                event.setDamage(damage);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player playerJoin = event.getPlayer();
        getLogger().info("> " + playerJoin.getDisplayName() + " connected");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player playerQuit = event.getPlayer();
        getLogger().info("> " + playerQuit.getDisplayName() + " left server");
    }
}
