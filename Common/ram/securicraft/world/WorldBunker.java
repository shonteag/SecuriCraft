package ram.securicraft.world;

import java.util.Random;

import ram.securicraft.BlockHandler;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldBunker implements IWorldGenerator {
	public int securityBlockID = BlockHandler.securityBlock.blockID;
	public int securityGlassID = BlockHandler.securityGlass.blockID;
	public int securityLightID = BlockHandler.securityLight.blockID;
	
	//vars
	public static final int DEPTHOFFSET = 10; //depth of floor from surface
	
	//main room size
	public static final int HEIGHT = 3;
	public static final int WIDTH = 8;
	public static final int LENGTH = 8;
	
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		//Primary block at random
		BiomeGenBase b = world.getBiomeGenForCoords(chunkX, chunkZ);
		if (b.biomeName.equalsIgnoreCase("plains") && false)
		{
			int posX = chunkX*16 + random.nextInt(16);
			int posZ = chunkZ*16 + random.nextInt(16);
			int posY = 1;
			
			//find ground level for posY
			for (posY = 1;posY<100;posY++)
			{
				if (world.canBlockSeeTheSky(posX, posY, posZ)) break;
			}
			
			//shaft
			for (int i=0;i<4;i++){
				for (int j=1;j<DEPTHOFFSET;j++){
					for (int k=0;k<4;k++){
						if (i==2 && k==2) {
							world.setBlock(posX+i, posY+j-DEPTHOFFSET, posZ+k, 0);
						} else {
							world.setBlock(posX+i, posY+j-DEPTHOFFSET, posZ+k, securityBlockID);
						}
					}
				}
			}
			
			
			//MAIN ROOM
			//floor and ceiling + lights
			for (int i = 0;i<=WIDTH;i++){
				for (int k = 0;k<=LENGTH;k++){
					world.setBlock(posX+i, posY-DEPTHOFFSET, posZ+k, securityBlockID);
					
					if (i==1 && k==1) {
						world.setBlock(posX+i, posY+HEIGHT+1-DEPTHOFFSET, posZ+k, 0);
					} 
					else if ((i*k) % 2 != 0) {
						world.setBlock(posX+i, posY+HEIGHT+1-DEPTHOFFSET, posZ+k, securityLightID);
					} else {
						world.setBlock(posX+i, posY+HEIGHT+1-DEPTHOFFSET, posZ+k, securityBlockID);
					}
				}
			}
			
			//walls and clear
			for (int i = 0;i<=WIDTH;i++) {
				for (int j = 1;j<=HEIGHT;j++) {
					for (int k = 0; k<=LENGTH; k++) {
						if ((k==0 || k==LENGTH) || (i==0 || i==WIDTH)) {
							if (k==0 && (i==2 || i==3) && (j==1 || j==2)) {
								//door
								world.setBlock(posX+i, posY+j-DEPTHOFFSET, posZ+k, 0);
							} else {
								world.setBlock(posX+i, posY+j-DEPTHOFFSET, posZ+k, securityBlockID);
							}
						} else {
							world.setBlock(posX+i, posY+j-DEPTHOFFSET, posZ+k, 0);
						}
					}
				}
			}
			
			
		}
		
	}
	
}
