package com.xpansive.bukkit.expansiveterrain.structure;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.xpansive.bukkit.expansiveterrain.WorldState;

public class BedrockGenerator extends LargeStructureGenerator {
    private SimplexNoiseGenerator noise;
    private double scale;
    private int minHeight, maxHeight;

    public BedrockGenerator(WorldState state) {
        super(state);
        noise = new SimplexNoiseGenerator(state.getBukkitWorld().getSeed());
        FileConfiguration config = state.getConfig();
        scale = config.getDouble("world.bedrock.scale");
        minHeight = config.getInt("world.bedrock.minheight");
        maxHeight = config.getInt("world.bedrock.maxheight");
    }

    @Override
    public void fillColumn(int worldX, int worldZ, int x, int z, byte[] chunkData) {
        double bedrock = noise.noise(worldX * scale, worldZ * scale);
        bedrock = (bedrock + 1) * (maxHeight - minHeight) / 2 + minHeight;
        for (int y = 0; y < bedrock; y++) {
            setBlock(chunkData, x, y, z, Material.BEDROCK);
        }
    }

}
