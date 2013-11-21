package ram.securicraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityServerControl extends TileEntity {
	public int numServerBlocks = 0;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setInteger("servers", this.numServerBlocks);
		super.writeToNBT(par1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.numServerBlocks = par1.getInteger("servers");
		super.readFromNBT(par1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public Packet getDescriptionPacket() {
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    this.writeToNBT(tagCompound);
	    return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tagCompound);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
	    this.readFromNBT(packet.data);
	}
}
