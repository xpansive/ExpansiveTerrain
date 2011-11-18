package com.xpansive.bukkit.expansiveterrain;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpansiveTerrain extends JavaPlugin {
	private final static String WORLD_NAME = "ExpansiveTerrain";
	private static World genWorld = null;

	public void onDisable() {
	}

	public void onEnable() {
		PluginDescriptionFile desc = this.getDescription();

		System.out.println(desc.getName() + " version " + desc.getVersion()
				+ " is enabled!");

		getCommand("expansive").setExecutor(new ExpansiveTerrainCommandExec());
	}

	public boolean anonymousCheck(CommandSender sender) {
		return sender instanceof Player;
	}

	public static World getExpansiveTerrainWorld() {
		if (genWorld == null) {
			genWorld = Bukkit.getServer().createWorld(WORLD_NAME,
					World.Environment.NORMAL,
					new ExpansiveTerrainChunkGenerator(WORLD_NAME));
		}
		return genWorld;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ExpansiveTerrainChunkGenerator(worldName);
	}
}