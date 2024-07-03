package com.lvdat.minevuicore;

import org.bukkit.plugin.java.JavaPlugin;

public class MinevuiCore extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("MinevuiCore loaded");
    }

    @Override
    public void onDisable() {
        getLogger().info("MinevuiCore disabled");
    }
}
