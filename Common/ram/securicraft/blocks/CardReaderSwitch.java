package ram.securicraft.blocks;

import ram.securicraft.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CardReaderSwitch extends BlockContainer {
	public int power = 0;
	
	public CardReaderSwitch(int id, Material material)
	{
		super(id,material);
		this.setBlockUnbreakable();
	}
	
	@Override
	public boolean canProvidePower(){
		return true;
	}
	
	@Override
    public int isProvidingWeakPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return power;
    }
	
	public int isProvidingStrongPower(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return power;
	}
	
	@Override
    public boolean canConnectRedstone(IBlockAccess iba, int i, int j, int k, int dir)
    {
    	return true;
    }
	
	@SideOnly(Side.CLIENT)
	public static Icon frontIcon;
	@SideOnly(Side.CLIENT)
	public static Icon frontIconOn;
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.blockIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_front"));
		this.frontIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_front"));
		this.frontIconOn = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5) + "_front_on"));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		return this.frontIcon;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
