package ram.securicraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import ram.securicraft.items.SecurityTwiddler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ItemHandler {
	public static Item securityTwiddler;
	
	public static void configureItems(Configuration config){
		securityTwiddler = new SecurityTwiddler(config.get("items", "SC_secTwiddler", 3000).getInt())
			.setUnlocalizedName("securityTwiddler")
			.setCreativeTab(CreativeTabs.tabTools);
	}
	
	public static void registerItems(GameRegistry registry){
		registry.registerItem(securityTwiddler, "securityTwiddler");
	}
	
	public static void setNames(LanguageRegistry registry){
		registry.addName(securityTwiddler, "Security Twiddler");
	}
	
}
