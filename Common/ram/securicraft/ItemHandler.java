package ram.securicraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import ram.securicraft.items.AccessCard;
import ram.securicraft.items.ItemSecurityDoor;
import ram.securicraft.items.SecurityTwiddler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemHandler {
	public static Item securityTwiddler;
	public static Item accessCard;
	
	public static Item itemSecurityDoor;
	
	public static void configureItems(Configuration config){
		securityTwiddler = new SecurityTwiddler(config.get("items", "SC_secTwiddler", 3000).getInt())
			.setUnlocalizedName("securityTwiddler")
			.setCreativeTab(CreativeTabs.tabTools);
		
		accessCard = new AccessCard(config.get("items", "SC_accessCard", 3001).getInt())
			.setUnlocalizedName("accessCard")
			.setCreativeTab(CreativeTabs.tabTools);
		
		itemSecurityDoor = new ItemSecurityDoor(config.get("items","SC_itemSecurityDoor",3002).getInt())
			.setUnlocalizedName("itemSecurityDoor")
			.setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	public static void registerItems(GameRegistry registry){
		registry.registerItem(securityTwiddler, "securityTwiddler");
		registry.registerItem(accessCard, "accessCard");
		registry.registerItem(itemSecurityDoor, "itemSecurityDoor");
		
		//RECIPES
		registry.addRecipe(new ItemStack(securityTwiddler), "xyx","xxx"," x ", 'x', Item.ingotIron, 'y', Item.ingotGold);
		registry.addRecipe(new ItemStack(accessCard,1), "ppi", 'p', Item.paper, 'i', Item.ingotIron);
	}
	
	public static void setNames(LanguageRegistry registry){
		registry.addName(securityTwiddler, "Security Twiddler");
		registry.addName(accessCard, "Access Card");
		registry.addName(itemSecurityDoor, "Security Door");
	}
	
}
