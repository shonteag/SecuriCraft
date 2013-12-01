package ram.securicraft.blocks;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import ram.securicraft.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CardReaderPanel extends BlockContainer {
	private int tickNumber;
	private int power = 0;
	
	public CardReaderPanel(int id, Material material) {
		super(id, material);
		this.setBlockUnbreakable();
		this.setTickNumber(40);
		this.setResistance(2500);
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
	
	public void setTickNumber(int num){
		this.tickNumber = num;
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
		if (par2 > 4) return this.frontIconOn;
		return this.frontIcon;
	}
	
	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		TileEntityCardPanel panelTile = (TileEntityCardPanel) par1World.getBlockTileEntity(par2,par3,par4);
		if (panelTile != null) {
		//switch back to locked
			panelTile.unlocked = false;
			if (this.power > 0) this.power = 0;
			
//            int meta = par1World.getBlockMetadata(par2, par3, par4);
//            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, meta-4);
			
            par1World.notifyBlocksOfNeighborChange(par2, par3 - 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3 + 1, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 - 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2 + 1, par3, par4, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 - 1, this.blockID);
            par1World.notifyBlocksOfNeighborChange(par2, par3, par4 + 1, this.blockID);
            
            //par1World.markBlockForRenderUpdate(par2, par3, par4);
		}
    }
	
	@Override
	public void onBlockPlacedBy(World par1World, int x, int y, int z, EntityLivingBase player, ItemStack par6ItemStack){
		TileEntityCardPanel tile = (TileEntityCardPanel) par1World.getBlockTileEntity(x, y, z);
		tile.owner = (((EntityPlayer)player).username);
	}
	
	@Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		TileEntityCardPanel panelTile = (TileEntityCardPanel) par1World.getBlockTileEntity(x, y, z);
		
		if (player.inventory.getCurrentItem() != null && player.inventory.getCurrentItem().getItem().getUnlocalizedName().equals("item.accessCard")) {
		//------------------ACCESS CARD--------------------------------------------------------------------
			//check for creative spawn
			if (player.inventory.getCurrentItem().stackTagCompound == null) {
				ItemStack itemStack = player.inventory.getCurrentItem();
			    itemStack.stackTagCompound = new NBTTagCompound();
			    itemStack.stackTagCompound.setIntArray("subnetIDs", new int[16]);
			    itemStack.stackTagCompound.setInteger("currentInd",0);
			    itemStack.stackTagCompound.setString("subnetNames", EnumChatFormatting.BLUE + "Subnets: ");
			}
			
			int[] subs = player.inventory.getCurrentItem().stackTagCompound.getIntArray("subnetIDs");
			
			boolean found = false;
			//check access card for proper subnet access
			for (int i = 0; i < subs.length; i++) if (subs[i] == panelTile.subnetID) {
				found = true;
				break;
			}
			
			if (found) {
				//access granted
				panelTile.unlocked = true;
				if (this.power < 15) this.power = 15;
				
//				int meta = par1World.getBlockMetadata(x, y, z);
//				par1World.setBlockMetadataWithNotify(x, y, z, 3, meta+4);
				
	            par1World.notifyBlocksOfNeighborChange(x, y - 1, z, this.blockID);
	            par1World.notifyBlocksOfNeighborChange(x, y + 1, z, this.blockID);
	            par1World.notifyBlocksOfNeighborChange(x - 1, y, z, this.blockID);
	            par1World.notifyBlocksOfNeighborChange(x + 1, y, z, this.blockID);
	            par1World.notifyBlocksOfNeighborChange(x, y, z - 1, this.blockID);
	            par1World.notifyBlocksOfNeighborChange(x, y, z + 1, this.blockID);
				
	            //par1World.markBlockForRenderUpdate(x, y, z);
				par1World.scheduleBlockUpdate(x, y, z, this.blockID, this.tickNumber);
				
				if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.GREEN + "Access granted via subnet " + EnumChatFormatting.BLUE + panelTile.subnetID);
			} else {
				//access denied
				if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.RED + "Access denied by subnet " + EnumChatFormatting.BLUE + panelTile.subnetID);
			}
			
			return true;
			
		} else if (player.inventory.getCurrentItem() != null
						&& player.inventory.getCurrentItem().getItem().getUnlocalizedName().equals("item.securityTwiddler") /* If used item is the twiddler */
						&& player.inventory.getCurrentItem().stackTagCompound.getBoolean("linking") /* If twiddler is in linking mode */
						&& panelTile.owner.equals(player.username)) /* Link ONLY BY PANEL OWNER */ {
		//------------------SERVER LINKER-------------------------------------------------------------------
			int[] loc = player.inventory.getCurrentItem().stackTagCompound.getIntArray("linkInfo");			
			if (loc[0] == 1) {
				//LINK SUCCESS
				//subnet id
				panelTile.subnetID = loc[1];
				//subnet name
				TileEntityServerControl serverTile = (TileEntityServerControl) par1World.getBlockTileEntity(loc[2], loc[3], loc[4]);
				panelTile.subnetName = serverTile.subnetName;
				
				if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.BLUE + "Link Established! Linked to: " + panelTile.subnetID + ", " + panelTile.subnetName);
			} else {
				//LINK FAIL
				if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.RED + "Cannot link to this device!");
			}
			return true;
			
		} else if (panelTile.owner.equals(player.username)) /* If item used is NOT twiddler or accessCard, and user is owner */ {
		//-----------------OWNER GET INFO---------------------------------------------------------------------
			if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.BLUE + "Card Reader -> Subnet: " + EnumChatFormatting.WHITE + panelTile.subnetID + ", " + panelTile.subnetName);
			return true;
		}
		else return false;
	}
	
	@Override
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer player){
		TileEntityCardPanel tile = (TileEntityCardPanel) par1World.getBlockTileEntity(x, y, z);
		if (player.getEntityName().equals(tile.owner)){
			if (player.getHeldItem() != null && player.getHeldItem().getUnlocalizedName().equals("item.securityTwiddler")) {
				this.dropBlockAsItem(par1World, x, y, z, par1World.getBlockMetadata(x, y, z), 0);
				par1World.setBlockToAir(x, y, z);
				par1World.removeBlockTileEntity(x, y, z);
			}
		}
	}
	
	@Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6){
		par1World.removeBlockTileEntity(par2,par3,par4);
	}
	
    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(par5);
        return (dir == NORTH && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) ||
               (dir == SOUTH && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) ||
               (dir == WEST  && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) ||
               (dir == EAST  && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST));
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        return (par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST)) ||
               (par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) ||
               (par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) ||
               (par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH));
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = par1World.getBlockMetadata(par2, par3, par4);
        int k1 = j1 & 8;
        j1 &= 7;


        ForgeDirection dir = ForgeDirection.getOrientation(par5);

        if (dir == NORTH && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH))
        {
            j1 = 4;
        }
        else if (dir == SOUTH && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH))
        {
            j1 = 3;
        }
        else if (dir == WEST && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST))
        {
            j1 = 2;
        }
        else if (dir == EAST && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST))
        {
            j1 = 1;
        }
        else
        {
            j1 = this.getOrientation(par1World, par2, par3, par4);
        }

        return j1 + k1;
    }

    /**
     * Get side which this button is facing.
     */
    private int getOrientation(World par1World, int par2, int par3, int par4)
    {
        if (par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST)) return 1;
        if (par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST)) return 2;
        if (par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) return 3;
        if (par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) return 4;
        return 1;
    }
    
    //reduce metaData
    private int reduceMeta(int meta){
    	if (meta > 4) return meta-4;
    	return meta;
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = this.reduceMeta(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
        this.func_82534_e(l);
    }

    private void func_82534_e(int par1)
    {
        int j = par1 & 7;
        boolean flag = (par1 & 8) > 0;
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.1875F;
        float f3 = 0.125F;

        if (flag)
        {
            f3 = 0.0625F;
        }

        if (j == 1)
        {
            this.setBlockBounds(0.0F, f, 0.5F - f2, f3, f1, 0.5F + f2);
        }
        else if (j == 2)
        {
            this.setBlockBounds(1.0F - f3, f, 0.5F - f2, 1.0F, f1, 0.5F + f2);
        }
        else if (j == 3)
        {
            this.setBlockBounds(0.5F - f2, f, 0.0F, 0.5F + f2, f1, f3);
        }
        else if (j == 4)
        {
            this.setBlockBounds(0.5F - f2, f, 1.0F - f3, 0.5F + f2, f1, 1.0F);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityCardPanel();
	}
}
