package ram.securicraft.items;

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

public class SecurityTwiddler extends Item {

	public SecurityTwiddler(int id){
		super(id);
		this.setMaxStackSize(1);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register){
		this.itemIcon = register.registerIcon(Reference.MOD_ID + ":" + (this.getUnlocalizedName().substring(5)));
	}
	
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setBoolean("linking", false);
	}
	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null && itemStack.stackTagCompound.getBoolean("linking")) {
			int[] loc = itemStack.stackTagCompound.getIntArray("linkInfo");
			String subnet = "Subnet ID: " + loc[1];
			String location = "Server Loc: " + loc[2] + "," + loc[3] + "," + loc[4];
			
			if (itemStack.stackTagCompound.getBoolean("linking")) list.add(EnumChatFormatting.BLUE + "Linking Mode");
			list.add(subnet);
			list.add(location);
		

		}
	}	
}
