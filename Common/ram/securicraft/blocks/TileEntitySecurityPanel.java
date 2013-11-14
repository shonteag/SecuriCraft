package ram.securicraft.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySecurityPanel extends TileEntity {
	public String owner;
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		par1.setString("owner", owner);
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
}
