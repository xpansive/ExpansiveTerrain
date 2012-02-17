package com.xpansive.bukkit.expansiveterrain.util;

import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;

import net.minecraft.server.Block;
import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.World;

/**
 * An API providing more direct, low-level access to the data stored in a world.
 * 
 * @author xpansive
 */
public class DirectWorld {

    private World world;
    private int lastChunkX = Integer.MAX_VALUE, lastChunkZ = Integer.MAX_VALUE;
    private Chunk lastChunk;
    private ChunkProviderServer cps;

    public DirectWorld(org.bukkit.World bukkitWorld) {
        this(((CraftWorld) bukkitWorld).getHandle());
    }

    public DirectWorld(World nmsWorld) {
        world = nmsWorld;
        cps = ((ChunkProviderServer)world.chunkProvider);
    }

    /**
     * Changes the type id of a block at the specified location without doing any sort of updates. You will need to manually handle updating lighting and items that have tile entities.
     */
    public void setRawTypeId(int x, int y, int z, int type) {
        if (y < world.height && y >= 0) {
            getChunk(x >> 4, z >> 4).b[(x & 15) << world.heightBitsPlusFour | (z & 15) << world.heightBits | y] = (byte) (type & 0xFF);
        }
    }

    /**
     * Changes the type id and data of a block at the specified location without doing any sort of updates. You will need to manually handle updating lighting and items that have tile entities.
     */
    public void setRawTypeIdAndData(int x, int y, int z, int type, int data) {
        if (y < world.height && y >= 0) {
            Chunk chunk = getChunk(x >> 4, z >> 4);
            chunk.b[(x & 15) << world.heightBitsPlusFour | (z & 15) << world.heightBits | y] = (byte) (type & 0xFF);
            chunk.g.a(x & 15, y, z & 15, data & 0xF);
        }
    }

    /**
     * Gets the type id of a block at a specified location.
     */
    public int getTypeId(int x, int y, int z) {
        if (y < world.height && y >= 0) {
            return getChunk(x >> 4, z >> 4).b[(x & 15) << world.heightBitsPlusFour | (z & 15) << world.heightBits | y];
        }
        return 0;
    }

    /**
     * Recalculates the lighting value of the specified block.
     */
    public void updateLighting(int x, int y, int z) {
        if (y < world.height && y >= 0) {
            Chunk chunk = getChunk(x >> 4, z >> 4);
            int type = getTypeId(x, y, z);
            x &= 15;
            z &= 15;
            int height = chunk.heightMap[z << 4 | x] & 255;
            if (Block.lightBlock[type & 255] != 0 && y >= height) {
                g(chunk, x, y + 1, z);
            } else if (y == height - 1) {
                g(chunk, x, y, z);
            }
        }
    }
    
    /**
     * Gets the y position of the highest block at the given x and z coordinates.
     */
    public int getHighestBlockY(int x, int z) {
        return world.getHighestBlockYAt(x, z);
    }
    
    /**
     * Gets the material at the specified coordinates
     */
    public Material getMaterial(int x, int y, int z) {
        return Material.getMaterial(getTypeId(x, y, z));
    }
    
    /**
     * Changes the material of a block at the specified location without doing any sort of updates. You will need to manually handle updating lighting and items that have tile entities.
     */
    public void setRawMaterial(int x, int y, int z, Material type) {
        setRawTypeId(x, y, z, type.getId());
    }
    
    public void setForceChunkLoad(boolean flag) {
        cps.forceChunkLoad = flag;
    }

    private Chunk getChunk(int x, int z) {
        if (lastChunkX != x || lastChunkZ != z) {
            lastChunkX = x;
            lastChunkZ = z;
            lastChunk = world.chunkProvider.getOrCreateChunk(x, z);
        }
        return lastChunk;
    }

    // Next two methods are private ones copied out of the NMS chunk class and modified slightly.
    private void g(Chunk chunk, int i, int j, int k) {
        int l = chunk.heightMap[k << 4 | i] & 255;
        int i1 = l;

        if (j > l) {
            i1 = j;
        }

        for (int j1 = i << world.heightBitsPlusFour | k << world.heightBits; i1 > 0 && Block.lightBlock[chunk.b[j1 + i1 - 1] & 255] == 0; --i1) {
        }

        if (i1 != l) {
            world.g(i, k, i1, l);
            chunk.heightMap[k << 4 | i] = (byte) i1;
            int k1;
            int l1;
            int i2;

            if (i1 < chunk.k) {
                chunk.k = i1;
            } else {
                k1 = world.height - 1;

                for (l1 = 0; l1 < 16; ++l1) {
                    for (i2 = 0; i2 < 16; ++i2) {
                        if ((chunk.heightMap[i2 << 4 | l1] & 255) < k1) {
                            k1 = chunk.heightMap[i2 << 4 | l1] & 255;
                        }
                    }
                }

                chunk.k = k1;
            }

            k1 = chunk.x * 16 + i;
            l1 = chunk.z * 16 + k;
            int j2;

            if (!world.worldProvider.f) {
                if (i1 < l) {
                    for (i2 = i1; i2 < l; ++i2) {
                        chunk.h.a(i, i2, k, 15);
                    }
                } else {
                    for (i2 = l; i2 < i1; ++i2) {
                        chunk.h.a(i, i2, k, 0);
                    }
                }

                for (i2 = 15; i1 > 0 && i2 > 0; chunk.h.a(i, i1, k, i2)) {
                    --i1;
                    j2 = Block.lightBlock[chunk.getTypeId(i, i1, k)];
                    if (j2 == 0) {
                        j2 = 1;
                    }

                    i2 -= j2;
                    if (i2 < 0) {
                        i2 = 0;
                    }
                }
            }

            byte b0 = chunk.heightMap[k << 4 | i];

            j2 = l;
            int k2 = b0;

            if (b0 < l) {
                j2 = b0;
                k2 = l;
            }

            if (!this.world.worldProvider.f) {
                d(chunk, k1 - 1, l1, j2, k2);
                d(chunk, k1 + 1, l1, j2, k2);
                d(chunk, k1, l1 - 1, j2, k2);
                d(chunk, k1, l1 + 1, j2, k2);
                d(chunk, k1, l1, j2, k2);
            }

            chunk.q = true;
        }
    }

    private void d(Chunk chunk, int i, int j, int k, int l) {
        if (l > k && world.areChunksLoaded(i, world.height / 2, j, 16)) {
            for (int i1 = k; i1 < l; ++i1) {
                world.b(EnumSkyBlock.SKY, i, i1, j);
            }

            chunk.q = true;
        }
    }

}
