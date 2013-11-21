package ram.securicraft.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import ram.securicraft.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerBlock extends BlockContainer {
	@SideOnly(Side.CLIENT)
	public static Icon frontIcon;
	@SideOnly(Side.CLIENT)
	public static Icon sideIcon;
	@SideOnly(Side.CLIENT)
	public static Icon topIcon;
	
	public ServerBlock(int id, Material material){
		super(id, material);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)));
		this.frontIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_front"));
		this.sideIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_side"));
		this.topIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_top"));
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		
        return par1 == 1 ? this.topIcon : (par1 == 0 ? this.topIcon : (par1 != par2 ? this.sideIcon : this.frontIcon));
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityServerBlock();
	}
}
