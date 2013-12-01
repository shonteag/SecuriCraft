package ram.securicraft.blocks;

//CREDIT: Some methods from this file (marked by a comment before each method) are code written by
//		  Forge User domi1819. I did not write them, and I do not take credit for them. So thanks domi1819!

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import ram.securicraft.Reference;

public class SecurityGlass extends SecurityBlock {

	public static Icon[] textures = new Icon[47];
	public static int[] textureRefByID = { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20, 20, 16, 16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20, 20, 16, 16, 28, 28, 25, 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 7, 7, 24, 24, 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };

	

	public SecurityGlass(int par1, Material par2Material) {
		super(par1, par2Material);
		this.setResistance(2500);
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	@Override
	public int getRenderBlockPass(){
		return 1;
	}

	//CREDIT Forge Forum User: domi1819
	@Override
	public void registerIcons(IconRegister iconRegistry)
	{
		this.blockIcon = iconRegistry.registerIcon(Reference.MOD_ID + ":" + this.getUnlocalizedName().substring(5) + "_1");
		for (int i = 0; i < 47; i++) textures[i] = iconRegistry.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)) + "_" + (i+1));
	}
	
	//CREDIT Forge Forum User: domi1819
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
	{
	         boolean[] bitMatrix = new boolean[8];
	        
	         if (side == 0 || side == 1)
	         {
	                 bitMatrix[0] = world.getBlockId(x-1, y, z-1) == this.blockID;
	                 bitMatrix[1] = world.getBlockId(x, y, z-1) == this.blockID;
	                 bitMatrix[2] = world.getBlockId(x+1, y, z-1) == this.blockID;
	                 bitMatrix[3] = world.getBlockId(x-1, y, z) == this.blockID;
	                 bitMatrix[4] = world.getBlockId(x+1, y, z) == this.blockID;
	                 bitMatrix[5] = world.getBlockId(x-1, y, z+1) == this.blockID;
	                 bitMatrix[6] = world.getBlockId(x, y, z+1) == this.blockID;
	                 bitMatrix[7] = world.getBlockId(x+1, y, z+1) == this.blockID;
	         }
	         if (side == 2 || side == 3)
	         {
	                 bitMatrix[0] = world.getBlockId(x+(side==2?1:-1), y+1, z) == this.blockID;
	                 bitMatrix[1] = world.getBlockId(x, y+1, z)                      == this.blockID;
	                 bitMatrix[2] = world.getBlockId(x+(side==3?1:-1), y+1, z) == this.blockID;
	                 bitMatrix[3] = world.getBlockId(x+(side==2?1:-1), y, z) == this.blockID;
	                 bitMatrix[4] = world.getBlockId(x+(side==3?1:-1), y, z) == this.blockID;
	                 bitMatrix[5] = world.getBlockId(x+(side==2?1:-1), y-1, z) == this.blockID;
	                 bitMatrix[6] = world.getBlockId(x, y-1, z)                      == this.blockID;
	                 bitMatrix[7] = world.getBlockId(x+(side==3?1:-1), y-1, z) == this.blockID;
	         }
	         if (side == 4 || side == 5)
	         {
	                 bitMatrix[0] = world.getBlockId(x, y+1, z+(side==5?1:-1)) == this.blockID;
	                 bitMatrix[1] = world.getBlockId(x, y+1, z)                      == this.blockID;
	                 bitMatrix[2] = world.getBlockId(x, y+1, z+(side==4?1:-1)) == this.blockID;
	                 bitMatrix[3] = world.getBlockId(x, y, z+(side==5?1:-1)) == this.blockID;
	                 bitMatrix[4] = world.getBlockId(x, y, z+(side==4?1:-1)) == this.blockID;
	                 bitMatrix[5] = world.getBlockId(x, y-1, z+(side==5?1:-1)) == this.blockID;
	                 bitMatrix[6] = world.getBlockId(x, y-1, z)                      == this.blockID;
	                 bitMatrix[7] = world.getBlockId(x, y-1, z+(side==4?1:-1)) == this.blockID;
	         }
	        
	         int idBuilder = 0;

	         for (int i = 0; i <= 7; i++) idBuilder = idBuilder + (bitMatrix[i]?(i==0?1:(i==1?2:(i==2?4:(i==3?8:(i==4?16:(i==5?32:(i==6?64:128))))))):0);
	        
	         return idBuilder>255||idBuilder<0?textures[0]:textures[textureRefByID[idBuilder]];
	}
	
	
}
