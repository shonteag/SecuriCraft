package ram.securicraft.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ram.securicraft.BlockHandler;
import ram.securicraft.Reference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UtilBlock_AntiMob extends BlockContainer {
	public static final int NEGRANGE = -3;
	public static final int RANGE = 3;
	
	
	public UtilBlock_AntiMob(int id, Material material){
		super(id,material);
	}
	
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
    	for (int i=NEGRANGE;i<=RANGE;i++){
    		for (int k=NEGRANGE;k<=RANGE;k++){
    			if (par1World.getBlockId(par2+i, par3, par4+k) == BlockHandler.securityBlock.blockID)
    			{
    				TileEntitySecurityBlock tile = (TileEntitySecurityBlock) par1World.getBlockTileEntity(par2+i, par3, par4+k);
    				tile.mobGuard = true;
    				par1World.setBlockMetadataWithNotify(par2+i, par3, par4+k, 1, 2);
    				par1World.markBlockForRenderUpdate(par2+i, par3, par4+k);
    			}
    		}
    	}
    	par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 5);
	}
	
	
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
    	for (int i=NEGRANGE;i<=RANGE;i++){
    		for (int k=NEGRANGE;k<=RANGE;k++){
    			if (par1World.getBlockId(par2+i, par3, par4+k) == BlockHandler.securityBlock.blockID)
    			{
    				TileEntitySecurityBlock tile = (TileEntitySecurityBlock) par1World.getBlockTileEntity(par2+i, par3, par4+k);
    				tile.mobGuard = true;
    				par1World.setBlockMetadataWithNotify(par2+i, par3, par4+k, 1, 2);
    				par1World.markBlockForRenderUpdate(par2+i, par3, par4+k);
    			}
    		}
    	}
    	par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 5);
    	return 0;
    }
    
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6){
    	for (int i=NEGRANGE;i<=RANGE;i++){
    		for (int k=NEGRANGE;k<=RANGE;k++){
    			if (par1World.getBlockId(par2+i, par3, par4+k) == BlockHandler.securityBlock.blockID)
    			{
    				TileEntitySecurityBlock tile = (TileEntitySecurityBlock) par1World.getBlockTileEntity(par2+i, par3, par4+k);
    				tile.mobGuard = false;
    				par1World.setBlockMetadataWithNotify(par2+i, par3, par4+k, 0, 2);
    				par1World.markBlockForRenderUpdate(par2+i, par3, par4+k);
    			}
    		}
    	}
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)));
	}
    
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}

}
