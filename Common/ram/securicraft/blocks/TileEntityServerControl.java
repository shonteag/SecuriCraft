package ram.securicraft.blocks;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityServerControl extends TileEntity {
	public int numServerBlocks = 0;
	public int subnetID;
	public String subnetName = "[Insert Subnet Name]";
	
	private ArrayList panels;
	private ArrayList sensors;
	
	@Deprecated
	private ArrayList cardIDs;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setInteger("servers", this.numServerBlocks);
		par1.setInteger("subnetID", this.subnetID);
		par1.setString("subnetName", this.subnetName);
		
		super.writeToNBT(par1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.numServerBlocks = par1.getInteger("servers");
		this.subnetID = par1.getInteger("subnetID");
		this.subnetName = par1.getString("subnetName");
		super.readFromNBT(par1);
	}
	
	@Deprecated
	public void addPanel(int x, int y, int z) {
		this.panels.add(x);
		this.panels.add(y);
		this.panels.add(z);
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
