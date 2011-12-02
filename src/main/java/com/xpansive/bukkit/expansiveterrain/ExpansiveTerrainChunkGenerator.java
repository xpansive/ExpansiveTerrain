package com.xpansive.bukkit.expansiveterrain;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;
import org.bukkit.World;

import com.xpansive.bukkit.expansiveterrain.biome.BiomeGenerator;
import com.xpansive.bukkit.expansiveterrain.biome.BiomeGeneratorFactory;
import com.xpansive.bukkit.expansiveterrain.populator.TerrainPopulator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public class ExpansiveTerrainChunkGenerator extends ChunkGenerator {
    private boolean initialized = false;
    private BiomeGeneratorFactory biomeGenerator;

    @Override
    public byte[] generate(World world, Random r, int cx, int cz) {
        if (!initialized) {
            biomeGenerator = new BiomeGeneratorFactory(world);
            initialized = true;
        }

        byte[] result = new byte[32768];

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                Biome biome = world.getBiome(cx * 16 + x, cz * 16 + z);
                BiomeGenerator bg = biomeGenerator.getForBiome(biome);
                TerrainGenerator tg = bg.getTerrainGenerator();
                tg.fillColumn(world, r, cx * 16 + x, cz * 16 + z, x, z, result);
            }
        }
        return result;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return world.getHighestBlockAt(x, z).getType() == Material.GRASS;

    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Arrays.asList((BlockPopulator) new TerrainPopulator());
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        int x = random.nextInt(1000) - 500;
        int z = random.nextInt(1000) - 500;
        int y = world.getHighestBlockYAt(x, z);
        return new Location(world, x, y, z);
    }
}
