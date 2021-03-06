package ram.securicraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.Configuration;
import ram.securicraft.blocks.CardReaderPanel;
import ram.securicraft.blocks.CardReaderSwitch;
import ram.securicraft.blocks.SecurityBlock;
import ram.securicraft.blocks.SecurityCautionBlock;
import ram.securicraft.blocks.SecurityDoor;
import ram.securicraft.blocks.SecurityGlass;
import ram.securicraft.blocks.SecurityLight;
import ram.securicraft.blocks.ServerBlock;
import ram.securicraft.blocks.ServerControlBlock;
import ram.securicraft.blocks.UtilBlock_AntiMob;
import ram.securicraft.blocks.UtilBlock_TripWire;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockHandler {
	public static Block securityBlock;
	public static Block securityCautionBlock;
	public static Block securityLight;
//	public static Block bioPanel;
	public static Block securityGlass;
	
	public static Block serverBlock;
	public static Block serverControlBlock;
	
	public static Block cardReaderPanel;
	public static Block cardReaderSwitch;
	public static Block securityDoor;
	
	public static Block utilMobGuard;
	public static Block utilTripWire;
	
	public static void configureBlocks(Configuration config){
		securityBlock = new SecurityBlock(config.get("blocks", "SC_secBlock", 2500).getInt(),Material.rock)
			.setUnlocalizedName("securityBlock")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		securityCautionBlock = new SecurityCautionBlock(config.get("blocks", "SC_secCautBlock", 2501).getInt(),Material.glass)
			.setUnlocalizedName("securityCautionBlock")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		securityLight = new SecurityLight(config.get("blocks", "SC_secLight", 2499).getInt(),Material.rock)
			.setUnlocalizedName("securityLight")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		securityGlass = new SecurityGlass(config.get("blocks", "SC_secGlass", 2496).getInt(),Material.glass)
			.setUnlocalizedName("securityGlass")
			.setCreativeTab(CreativeTabs.tabBlock);
		
//		bioPanel = new BioPanel(config.get("blocks", "SC_bioPanel", 2498).getInt(),Material.rock)
//			.setUnlocalizedName("securityBioPanel")
//			.setCreativeTab(CreativeTabs.tabRedstone);
		
		serverBlock = new ServerBlock(config.get("blocks", "SC_serverBlock", 2497).getInt(),Material.circuits)
			.setUnlocalizedName("serverBlock")
			.setCreativeTab(CreativeTabs.tabRedstone);
		serverControlBlock = new ServerControlBlock(config.get("blocks", "SC_sercerControlBlock", 2495).getInt(),Material.circuits)
			.setUnlocalizedName("serverControlBlock")
			.setCreativeTab(CreativeTabs.tabRedstone);
		
		
		cardReaderPanel = new CardReaderPanel(config.get("blocks", "SC_cardReaderPanel", 2494).getInt(),Material.circuits)
			.setUnlocalizedName("cardReaderPanel")
			.setCreativeTab(CreativeTabs.tabRedstone);
		
		cardReaderSwitch = new CardReaderSwitch(config.get("blocks", "SC_cardReaderSwitch", 2493).getInt(),Material.circuits)
			.setUnlocalizedName("cardReaderSwitch")
			.setCreativeTab(CreativeTabs.tabRedstone);
		
		securityDoor = new SecurityDoor(config.get("blocks", "SC_securityDoor", 2490).getInt(), Material.iron)
			.setUnlocalizedName("securityDoor")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		
		utilMobGuard = new UtilBlock_AntiMob(config.get("blocks", "SC_antiMob", 2487).getInt(), Material.rock)
			.setUnlocalizedName("utilAntiMobBlock")
			.setCreativeTab(CreativeTabs.tabRedstone);
		
		utilTripWire = new UtilBlock_TripWire(config.get("blocks", "SC_tripWire",2486).getInt(), Material.circuits)
			.setUnlocalizedName("utilTripWireBlock")
			.setCreativeTab(CreativeTabs.tabRedstone);
		
	}
	
	public static void registerBlocks(GameRegistry registry){
		registry.registerBlock(securityBlock, "securityBlock");
		registry.registerBlock(securityCautionBlock, "securityCautionBlock");
		registry.registerBlock(securityLight, "securityLight");
		registry.registerBlock(securityGlass, "securityGlass");
//		registry.registerBlock(bioPanel, "securityBioPanel");
		
		registry.registerBlock(serverBlock, "serverBlock");
		registry.registerBlock(serverControlBlock, ItemBlock.class,"serverControlBlock");
		
		registry.registerBlock(cardReaderPanel, "cardReaderPanel");
		registry.registerBlock(securityDoor, "securityDoor");
		
		registry.registerBlock(utilMobGuard, "utilAntiMobBlock");
		registry.registerBlock(utilTripWire, "utilTripWireBlock");
		//----------RECIPES---------------

	}
	
	public static void setNames(LanguageRegistry registry){
		registry.addName(securityBlock, "Security Building Block");
		registry.addName(securityCautionBlock, "Security Anti-Tampering Block");
		registry.addName(securityLight, "Security Light");
		registry.addName(securityGlass, "Security Glass");
//		registry.addName(bioPanel, "Biometric Access Panel");
		
		registry.addName(serverBlock, "Server Block");
		registry.addName(serverControlBlock, "Server Control");
		
		registry.addName(cardReaderPanel, "Card Reader Access Panel");
		registry.addName(securityDoor, "Security Door");
		
		registry.addName(utilMobGuard, "Watch Dog Block: Anti-Mob Spawn");
		registry.addName(utilTripWire, "Watch Dog Block: Trip Wire Laser");
	}
}
