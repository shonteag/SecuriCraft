package ram.securicraft.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySecurityBlock extends TileEntity {
	public String owner = " ";
	public boolean mobGuard = false;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setString("owner", this.owner);
		par1.setBoolean("mobGuard", this.mobGuard);
		super.writeToNBT(par1);
	}
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.owner = par1.getString("owner");
		this.mobGuard = par1.getBoolean("mobGuard");
		super.readFromNBT(par1);
	}
	
	public void setOwner (String par1Owner){
		this.owner = par1Owner;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	@Override
	public Packet getDescriptionPacket() {
        Packet132TileEntityData packet = new Packet132TileEntityData();
        packet.actionType = 0;
        packet.xPosition = xCoord;
        packet.yPosition = yCoord;
        packet.zPosition = zCoord;
	    NBTTagCompound tagCompound = new NBTTagCompound();
	    this.writeToNBT(tagCompound);
        packet.data = tagCompound;
	    return packet;
	}
	
	@Override
	public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
	    this.readFromNBT(packet.data);
	}
}
