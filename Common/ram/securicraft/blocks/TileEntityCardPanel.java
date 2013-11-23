package ram.securicraft.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCardPanel extends TileEntity {
	public String owner = "";
	public int subnetID = 0;
	public String subnetName = "";
	public boolean unlocked = false;
	
	public TileEntityCardPanel(){
		super();
		this.subnetID = 0; 
		this.subnetName = "Subnet";
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setString("owner", this.owner);
		par1.setInteger("subnetID", this.subnetID);
		par1.setString("subnetName", this.subnetName);
		par1.setBoolean("unlocked", this.unlocked);
		super.writeToNBT(par1);
	}
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.owner = par1.getString("owner");
		this.subnetID = par1.getInteger("subnetID");
		this.subnetName = par1.getString("subnetName");
		this.unlocked = par1.getBoolean("unlocked");
		super.readFromNBT(par1);
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
