package com.xpansive.bukkit.worldgen;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class ExpansiveTerrainEventListener extends WorldListener {

	private ExpansiveTerrain plugin;

	public ExpansiveTerrainEventListener(ExpansiveTerrain plugin) {
		this.plugin = plugin;
	}

	public void onWorldSave(WorldSaveEvent e) {
		if (e.getWorld().getName() == ExpansiveTerrain.WORLD_NAME) {
			VoronoiNoise.Save();
			System.out.println("ExpansiveTerrain world saved");
		}
	}

	public void onWorldLoad(WorldLoadEvent e) {
		if (e.getWorld().getName() == ExpansiveTerrain.WORLD_NAME) {
			VoronoiNoise.Load();
			System.out.println("ExpansiveTerrain world loaded");
		}
	}
}
