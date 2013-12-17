package ram.securicraft;


import ram.securicraft.world.WorldBunker;
import cpw.mods.fml.common.registry.GameRegistry;

public class WorldGenHandler {
	public static WorldBunker worldBunker = new WorldBunker();
	
	public static void registerWorldGenerators(GameRegistry gameRegistry)
	{
		gameRegistry.registerWorldGenerator(worldBunker);
	}
}
