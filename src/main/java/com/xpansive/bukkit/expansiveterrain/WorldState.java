package com.xpansive.bukkit.expansiveterrain;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.CraftWorld;

import com.xpansive.bukkit.expansiveterrain.biome.BiomeGeneratorFactory;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class WorldState {
    public WorldState(World bukkitWorld, ConfigManager configManager, ExpansiveTerrain plugin) {
        this.bukkitWorld = bukkitWorld;
        nmsWorld = ((CraftWorld) bukkitWorld).getHandle();
        directWorld = new DirectWorld(bukkitWorld);
        logger = plugin.getServer().getLogger();
        random = new Random(bukkitWorld.getSeed());
        randomExt = new RandomExt(random);
        this.configManager = configManager;
        config = configManager.getConfig(bukkitWorld.getName());
        this.plugin = plugin;
    }
    
    private final World bukkitWorld;
    private final net.minecraft.server.World nmsWorld;
    private final DirectWorld directWorld;

    private BiomeGeneratorFactory biomeGenerator;

    private final Logger logger;

    private final Random random;
    private final RandomExt randomExt;

    private final ConfigManager configManager;
    private final FileConfiguration config;

    private final ExpansiveTerrain plugin;

    public org.bukkit.World getBukkitWorld() {
        return bukkitWorld;
    }

    public net.minecraft.server.World getNmsWorld() {
        return nmsWorld;
    }

    public DirectWorld getDirectWorld() {
        return directWorld;
    }

    public Random getRandom() {
        return random;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public ExpansiveTerrain getPlugin() {
        return plugin;
    }

    public BiomeGeneratorFactory getBiomeGenerator() {
        if (biomeGenerator == null) {
            // We can't do this in the constructor because of obvious reasons
            biomeGenerator = new BiomeGeneratorFactory(this);
        }
        return biomeGenerator;
    }

    public Logger getLogger() {
        return logger;
    }

    public RandomExt getRandomExt() {
        return randomExt;
    }
}
