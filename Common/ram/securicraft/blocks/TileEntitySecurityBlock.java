package ram.securicraft.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntitySecurityBlock extends TileEntity {
	public String owner;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setString("owner", this.owner);
		super.writeToNBT(par1);
	}
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.owner = par1.getString("owner");
		super.readFromNBT(par1);
	}
	
	public void setOwner (String par1Owner){
		this.owner = par1Owner;
	}
	
	public String getOwner() {
		return this.owner;
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
