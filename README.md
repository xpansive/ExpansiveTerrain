#ExpansiveTerrain
##A terrain generator for bukkit

###Compiling
I use maven to compile due to its sheer simplicity. After installing it, just do this in the root directory:   
`mvn clean package`   
The jar will be in the target folder (if it builds sucessfully).

###Getting There
You have a few options for getting to the ExpansiveTerrain world. 

####Command
The easiest method by far is to use the command (see below). However, this is probably not the best idea if you run a big server and have plenty of worlds, and is mainly for debugging purposes.

####bukkit.yml
If you want bukkit to use ExpansiveTerrain as the default world, you can add the following to your bukkit.yml, making sure to change NAMEOFTHEWORLD to whatever you want ("world" is the default in bukkit).   

        worlds:   
          NAMEOFTHEWORLD:   
            generator: ExpansiveTerrain   


####Multiverse (or any other similar world manager)
Simpily generate a new world (see documentation on your world manager for how to do this) with the generator set to ExpansiveTerrain. Make sure you capitalize it properly, something like expansiveterrain or ExPaNsIvEtErRaIn won't work.

###Commands
`/expansive` - Brings you to the ExpansiveTerrain world

###Features
-A voronoi noise based heightmap terrain, which produces large mountians with jagged peaks   
-Ores   
-Trees   
-Flowers   
-Pumpkins   
-Mushrooms   
-Wild grass/shrubs   
-Biome support   
-Cacti   
-Melons   
-Lakes   

###Todo List
-New 1.8 biomes   
-Sand near lakes   
-Oceans   
-Caves   
-Reeds   
-Varying biome-based terrian heights   
-More tree types   
-Dungeons/ruins   
-Cities   
-Better ore populator   
-Better tree populator   
-Cooler jungles and swamps   

###Bugs
-Mushrooms sometimes get planted where they shouldn't, espically at night (I've seen lakes with hundreds of mushrooms on them)
