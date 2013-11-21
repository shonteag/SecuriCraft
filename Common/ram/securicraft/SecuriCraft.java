package ram.securicraft;

import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
@NetworkMod(serverSideRequired = false, clientSideRequired = true)
public class SecuriCraft {
	@Instance(Reference.MOD_NAME)
	public static SecuriCraft instance;
	
	@SidedProxy(clientSide = "ram.securicraft.client.ClientProxy", serverSide = "ram.securicraft.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry gamereg = new GameRegistry();
		LanguageRegistry langreg = new LanguageRegistry();
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		BlockHandler.configureBlocks(config);
		ItemHandler.configureItems(config);
		
		config.save();
		
		BlockHandler.registerBlocks(gamereg);
		BlockHandler.setNames(langreg);
		
		ItemHandler.registerItems(gamereg);
		ItemHandler.setNames(langreg);
		
		GameRegistry.registerTileEntity(ram.securicraft.blocks.TileEntitySecurityBlock.class, "te_SB");
		GameRegistry.registerTileEntity(ram.securicraft.blocks.TileEntitySecurityPanel.class, "te_BP");
		GameRegistry.registerTileEntity(ram.securicraft.blocks.TileEntityServerControl.class, "te_SCB");
		GameRegistry.registerTileEntity(ram.securicraft.blocks.TileEntityServerBlock.class, "te_SerB");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
