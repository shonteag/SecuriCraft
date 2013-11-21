package ram.securicraft.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import ram.securicraft.BlockHandler;
import ram.securicraft.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerControlBlock extends BlockContainer {
	public static final int MAX_X = 3;
	public static final int MAX_Y = 3;
	public static final int MAX_Z = 3;
	public static final int MIN_X = -3;
	public static final int MIN_Y = -3;
	public static final int MIN_Z = -3;
	public static final int MINSIZE = 1;
	
	public ServerControlBlock(int id, Material material) {
		super(id, material);
	}
	
	//TODO: Interacts with player and brings up GUI
	//TODO: Creates file on server with locations of linked panels
	//TODO: Checks for multiblock structure in vicinity
	
	
	@SideOnly(Side.CLIENT)
	public static Icon frontIcon;
	@SideOnly(Side.CLIENT)
	public static Icon topIcon;
	@SideOnly(Side.CLIENT)
	public static Icon bottomIcon;
	@SideOnly(Side.CLIENT)
	public static Icon sideIcon;
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_front"));
		this.frontIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_front"));
		this.sideIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_side"));
	}
	
	
	//finds facing side for texture rendering
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase player, ItemStack par6ItemStack){
		int front;
		
		double playerX = player.lastTickPosX;
		double playerZ = player.lastTickPosZ;
		
		double offsetX = x - playerX;
		double offsetZ = z - playerZ;
		
		if (offsetX >= 0 && Math.abs(offsetX) > Math.abs(offsetZ)) front = 4;
		else if (offsetX < 0 && Math.abs(offsetX) > Math.abs(offsetZ)) front = 5;
		else if (offsetZ >= 0 && Math.abs(offsetZ) >= Math.abs(offsetX)) front = 2;
		else front = 3;
		
        par1World.setBlockMetadataWithNotify(x, y, z, front, 2);
   	}
	
	@Override
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 0);
		return par9;
	}
	
	@Override
	public boolean canRenderInPass(int par1){
		return true;
	}
	
	@Override
	public void onBlockDestroyedByPlayer(World par1World, int x, int y, int z, int metadata){
		par1World.removeBlockTileEntity(x, y, z);
	}
	
	@Override
    public void onBlockDestroyedByExplosion(World par1World, int x, int y, int z, Explosion par5Explosion) {
		par1World.removeBlockTileEntity(x, y, z);
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6){
        par1World.removeBlockTileEntity(par2, par3, par4);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		
        return par1 == 1 ? this.sideIcon : (par1 == 0 ? this.sideIcon : (par1 != par2 ? this.sideIcon : this.frontIcon));
	}
	
	@Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		TileEntityServerControl tile = (TileEntityServerControl) par1World.getBlockTileEntity(x,y,z);
		//player.addChatMessage("Server size: " + tile.numServerBlocks);
		return true;
	}

	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		//check multiblock structure
		int numBlocks = 0;
		
		for (int i=this.MIN_X;i<=this.MAX_X;i++) {
			for (int j=this.MIN_Y;j<=this.MAX_Y;j++){
				for (int k=this.MIN_Z;k<=this.MAX_Z;k++){
					int tempID = par1World.getBlockId(par2+i, par3+j, par4+k);
					if (tempID == BlockHandler.serverBlock.blockID) numBlocks++;
				}
			}
		}
		
		TileEntityServerControl tile = (TileEntityServerControl) par1World.getBlockTileEntity(par2,par3,par4);
		tile.numServerBlocks = numBlocks;
		
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 5);

	}

	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityServerControl();
	}

}
