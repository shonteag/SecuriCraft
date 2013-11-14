package ram.securicraft.blocks;

import ram.securicraft.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BioPanel extends BlockContainer {
	
	public BioPanel(int id, Material material) {
		super(id, material);
		this.setBlockUnbreakable();
		this.setResistance(2500);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	
	public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		TileEntitySecurityPanel tile = (TileEntitySecurityPanel) par1World.getBlockTileEntity(x, y, z);
		if (player.getEntityName() == tile.getOwner()) {
			
			
			//access granted
			player.addChatMessage("BioPanel: Access Granted");
		} else {
			//access denied
			player.addChatMessage("BioPanel: Access Denied");
		}
		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase player, ItemStack par6ItemStack){
		TileEntitySecurityPanel tile = (TileEntitySecurityPanel) par1World.getBlockTileEntity(x, y, z);
		tile.setOwner(player.getEntityName());
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitySecurityPanel();
	}
	
	
 
}
