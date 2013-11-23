package ram.securicraft.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ram.securicraft.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class AccessCard extends Item {
	
	public AccessCard(int id) {
		super(id);
		this.setMaxStackSize(1);
	}
	
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setIntArray("subnetIDs", new int[16]);
	    itemStack.stackTagCompound.setInteger("currentInd",0);
	    itemStack.stackTagCompound.setString("subnetNames", EnumChatFormatting.BLUE + "Subnets: ");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.itemIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			int[] subs = itemStack.stackTagCompound.getIntArray("subnetIDs");
			String subsName = itemStack.stackTagCompound.getString("subnetNames");
			
			String[] names = subsName.split(",");
			
			for (int i = 0; i < names.length; i++) list.add(names[i]);
			
			//NUMBERS
//			for (int i = 0; i<subs.length;i++){
//				if (subs[i]!=0) list.add("Subnet: " + EnumChatFormatting.GREEN + subs[i]);
//			}

		}
	}	
}