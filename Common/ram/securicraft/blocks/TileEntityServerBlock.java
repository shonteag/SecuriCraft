package ram.securicraft.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityServerBlock extends TileEntity {
	public int subnetID;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setInteger("subnetID", this.subnetID);
		super.writeToNBT(par1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.subnetID = par1.getInteger("subnetID");
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
