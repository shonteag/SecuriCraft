package ram.securicraft.blocks;

import java.util.ArrayList;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySecurityPanel extends TileEntity {
	public String owner;
	public ArrayList<String> users;
	public boolean open = false;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setString("owner", this.owner);
		par1.setBoolean("open", this.open);
		
		//TODO: Get this shit working. Fucking shit fucks didn't include setStringArray() for NBT tags.
		
//		String userString = "";
//		if (!users.isEmpty()) {
//			for (String s : users) {
//				userString.concat(s + ",");
//			}
//			//if (!userString.isEmpty()) par1.setString("users", userString);
//		}
		
		super.writeToNBT(par1);
	}
	@Override
	public void readFromNBT(NBTTagCompound par1){
		this.owner = par1.getString("owner");
		this.open = par1.getBoolean("open");
		
//		String u = par1.getString("users");
//		String[] temp = u.split(",");
//		for (int i=0;i<temp.length;i++){
//			this.users.add(temp[i]);
//		}
		
		super.readFromNBT(par1);
	}
	
	public void setOwner (String par1Owner){
		this.owner = par1Owner;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void addUser(String user){
		users.add(user);
	}
	
	public boolean checkUser(String user){
		if (user.equals(this.owner) || users.contains(user.toString())) {
			return true;
		}
		return false;
	}
	
	public void setOpen(boolean par1){
		this.open = par1;
	}
	public boolean isOpen(){
		return this.open;
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
