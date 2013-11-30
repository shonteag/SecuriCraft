package ram.securicraft.blocks;

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
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import ram.securicraft.BlockHandler;
import ram.securicraft.Reference;
import ram.securicraft.SecuriCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ServerControlBlock extends BlockContainer {
	public static final int MAX_X = 4;
	public static final int MAX_Y = 4;
	public static final int MAX_Z = 4;
	public static final int MIN_X = -4;
	public static final int MIN_Y = -4;
	public static final int MIN_Z = -4;
	public static final int MINSIZE = 1;
	
	//temporary
	private int subnetID;
	
	public ServerControlBlock(int id, Material material) {
		super(id, material);
	}
	
	//TODO: Interacts with player and brings up GUI
	//COMPLETE: Finds blocks in specified radius, and binds serverBlocks to its subnetID
	
	
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
        
		//Generate new SubnetID!!
		int id = (int) Math.abs(x)+ (int) Math.abs(x) + (int) Math.abs(x)+ (int)(Math.random()*5001);
		TileEntityServerControl controlTile = (TileEntityServerControl) par1World.getBlockTileEntity(x,y,z);
		controlTile.subnetID = id;
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
		TileEntityServerControl controlTile = (TileEntityServerControl) par1World.getBlockTileEntity(par2,par3,par4);

        par1World.removeBlockTileEntity(par2, par3, par4);
        //reset all serverBlock subnetID fields to 0
		for (int i=this.MIN_X;i<=this.MAX_X;i++) {
			for (int j=this.MIN_Y;j<=this.MAX_Y;j++){
				for (int k=this.MIN_Z;k<=this.MAX_Z;k++){
					int tempID = par1World.getBlockId(par2+i, par3+j, par4+k);
					if (tempID == BlockHandler.serverBlock.blockID){
						TileEntityServerBlock tile = (TileEntityServerBlock) par1World.getBlockTileEntity(par2+i,par3+j,par4+k);
						if (tile.subnetID == controlTile.subnetID) {
							//serverBlock linked to this subnet
							tile.subnetID = 0;
						}
					}
				}
			}
		}
		par1World.removeBlockTileEntity(par2,par3,par4);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		
        return par1 == 1 ? this.sideIcon : (par1 == 0 ? this.sideIcon : (par1 != par2 ? this.sideIcon : this.frontIcon));
	}
	
	@Override
    public boolean onBlockActivated(World par1World, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		TileEntityServerControl tile = (TileEntityServerControl) par1World.getBlockTileEntity(x,y,z);

		if ((player.getHeldItem() != null && player.getHeldItem().getItem().getUnlocalizedName().equals("item.securityTwiddler"))){
			
			if (player.isSneaking()){

			} else {
				
				//add loc info to twiddler
				ItemStack itemStack = player.inventory.getCurrentItem();
				
				int[] loc = new int[5];
				loc[0]=1;
				loc[1]=tile.subnetID;
				loc[2]=x;
				loc[3]=y;
				loc[4]=z;
				
				if (itemStack.stackTagCompound == null) {
					itemStack.stackTagCompound = new NBTTagCompound();
					itemStack.stackTagCompound.setBoolean("linking", false);
				}
				
				itemStack.stackTagCompound.setIntArray("linkInfo", loc);
				itemStack.stackTagCompound.setBoolean("linking", true);
				
				if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.BLUE + "Link initiated...");
			}
		
		} else if (player.getHeldItem() != null && player.getHeldItem().getItem().getUnlocalizedName().equals("item.accessCard")) {
			//Add subnet to access card
			ItemStack itemStack = player.inventory.getCurrentItem();
			
			if (itemStack.stackTagCompound == null) {
				itemStack.stackTagCompound = new NBTTagCompound();
			    itemStack.stackTagCompound.setIntArray("subnetIDs", new int[16]);
			    itemStack.stackTagCompound.setInteger("currentInd",0);
			    itemStack.stackTagCompound.setString("subnetNames", EnumChatFormatting.BLUE + "Subnets: ");
			}
			
			int[] subs = itemStack.stackTagCompound.getIntArray("subnetIDs");
			String subsName = itemStack.stackTagCompound.getString("subnetNames");
			
			if (itemStack.stackTagCompound.getInteger("currentInd") >= 15) {
				if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.RED + "Error: Cannot add more than 15 subnets to one access card!");
			} else {
				
				boolean isPresent = false;
				for (int k = 0; k<subs.length; k++) { 
					if (subs[k] == tile.subnetID) {
						isPresent = true;
						break;
					}
				}
				
				if (!isPresent) {
					//SUBNET IDS
					itemStack.stackTagCompound.setInteger("currentInd", itemStack.stackTagCompound.getInteger("currentInd")+1);
					itemStack.stackTagCompound.setIntArray("subnetIDs", subs);
					subs[itemStack.stackTagCompound.getInteger("currentInd")] = tile.subnetID;

					//SUBNET NAMES
					subsName += EnumChatFormatting.GREEN + tile.subnetName + ",";
					itemStack.stackTagCompound.setString("subnetNames", subsName);
					
					if(!par1World.isRemote) player.addChatMessage(EnumChatFormatting.GREEN + "Subnet Access added to ID Card.");
				} else {
					if (!par1World.isRemote) player.addChatMessage(EnumChatFormatting.RED + "Subnet Access already granted to this ID Card.");
				}
			}
		
		} else {
			//TODO: PULL UP GUI
			player.openGui(SecuriCraft.instance, 0, par1World, x, y, z);
			
			//just info
			if (!par1World.isRemote) player.addChatMessage("Server size: " + tile.numServerBlocks 
					+ ", SubnetID = " + EnumChatFormatting.BLUE + tile.subnetID 
					+ EnumChatFormatting.WHITE + ", Subnet Name: " + EnumChatFormatting.BLUE + tile.subnetName);
		}
		return true;
	}

	@Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		//check multiblock structure
		TileEntityServerControl controlTile = (TileEntityServerControl) par1World.getBlockTileEntity(par2,par3,par4);
		int numBlocks = 0;
		
		for (int i=this.MIN_X;i<=this.MAX_X;i++) {
			for (int j=this.MIN_Y;j<=this.MAX_Y;j++){
				for (int k=this.MIN_Z;k<=this.MAX_Z;k++){
					int tempID = par1World.getBlockId(par2+i, par3+j, par4+k);
					if (tempID == BlockHandler.serverBlock.blockID){
						TileEntityServerBlock tile = (TileEntityServerBlock) par1World.getBlockTileEntity(par2+i,par3+j,par4+k);
						if (tile.subnetID == 0){
							//Set serverBlock tile entity subnet ID
							tile.subnetID = controlTile.subnetID;
							numBlocks++;
						} else if (tile.subnetID == controlTile.subnetID) {
							//serverBlock linked to correct subnet
							numBlocks++;
						} else {
							//serverBlock belongs to another subnet. ignore that sh*t.
						}
					}
				}
			}
		}
		
		controlTile.numServerBlocks = numBlocks;
		
		par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 5);

	}
	
	
	public void onBlockClicked(World par1World, int x, int y, int z, EntityPlayer player){
		TileEntityServerControl controlTile = (TileEntityServerControl) par1World.getBlockTileEntity(x,y,z);

		if(player.getHeldItem() != null && player.getHeldItem().getItem().getUnlocalizedName().equals("item.securityTwiddler") && player.isSneaking()) {

		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityServerControl();
	}

}
