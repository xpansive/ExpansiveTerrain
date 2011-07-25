package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class SandPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk source) {
		int chunkx = (source.getX() << 4);
		int chunkz = (source.getZ() << 4);
		for (int x = 0; x < 16; x++) {
			for(int z = 0; z < 16; z++) {
				int y = world.getHighestBlockYAt(chunkx + x, chunkz + z);
				
				Block b = world.getBlockAt(chunkx + x, y - 1, chunkz + z);
				if (b.getBiome() == Biome.DESERT) {
					b.setType(Material.SAND);
					for (int i = 1; i < 8; i++) {
						Block cur = b.getRelative(0, -i, 0);
						if (cur.getType() == Material.DIRT) {
							cur.setType(Material.SANDSTONE);
						}
					}
				}
			}
		}
	}

}
