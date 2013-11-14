package ram.securicraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.Configuration;
import ram.securicraft.blocks.*;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class BlockHandler {
	public static Block securityBlock;
	public static Block securityLight;
	public static Block bioPanel;
	public static Block securityGlass;
	
	public static void configureBlocks(Configuration config){
		securityBlock = new SecurityBlock(config.get("blocks", "SC_secBlock", 2500).getInt(),Material.rock)
			.setUnlocalizedName("securityBlock")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		securityLight = new SecurityLight(config.get("blocks", "SC_secLight", 2499).getInt(),Material.rock)
			.setUnlocalizedName("securityLight")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		securityGlass = new SecurityGlass(config.get("blocks", "SC_secGlass", 2496).getInt(),Material.glass)
			.setUnlocalizedName("securityGlass")
			.setCreativeTab(CreativeTabs.tabBlock);
		
		bioPanel = new BioPanel(config.get("blocks", "SC_bioPanel", 2498).getInt(),Material.rock)
			.setUnlocalizedName("securityBioPanel")
			.setCreativeTab(CreativeTabs.tabRedstone);
		
		
		
	}
	
	public static void registerBlocks(GameRegistry registry){
		registry.registerBlock(securityBlock, "securityBlock");
		registry.registerBlock(securityLight, "securityLight");
		registry.registerBlock(securityGlass, "securityGlass");
		registry.registerBlock(bioPanel, "securityBioPanel");
	}
	
	public static void setNames(LanguageRegistry registry){
		registry.addName(securityBlock, "Security Building Block");
		registry.addName(securityLight, "Security Light");
		registry.addName(securityGlass, "Security Glass");
		registry.addName(bioPanel, "Biometric Access Panel");
	}
}
