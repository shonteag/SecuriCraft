package ram.securicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ram.securicraft.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SecurityLight extends BlockContainer { 
	
	public SecurityLight(int id, Material material) {
		super(id, material);
		this.setLightValue(1.0f);
		this.setBlockUnbreakable();
		this.setResistance(2500);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	@Override
	public void onBlockAdded(World par1World, int x, int y, int z){
		
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase player, ItemStack par6ItemStack){
		TileEntitySecurityBlock tile = (TileEntitySecurityBlock) par1World.getBlockTileEntity(x, y, z);
		tile.setOwner(player.getEntityName());
	}
	
	@Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
//		TileEntitySecurityBlock tile = (TileEntitySecurityBlock) par1World.getBlockTileEntity(x, y, z);
//		player.addChatMessage("Owner: " + tile.getOwner() + ", " + tile.getOwnerID());
		return false;
	}
	
	@Override
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer player){
		TileEntitySecurityBlock tile = (TileEntitySecurityBlock) par1World.getBlockTileEntity(x, y, z);
		
		if (player.getEntityName() == tile.getOwner()){
			if (player.getHeldItem() != null && player.getHeldItem().getUnlocalizedName().equals("item.securityTwiddler")) {
				this.dropBlockAsItem(par1World, x, y, z, par1World.getBlockMetadata(x, y, z), 0);
				par1World.setBlockToAir(x, y, z);
				par1World.removeBlockTileEntity(x, y, z);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySecurityBlock();
	}
}
