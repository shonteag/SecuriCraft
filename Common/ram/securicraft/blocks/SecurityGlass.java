package ram.securicraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class SecurityGlass extends SecurityBlock {

	public SecurityGlass(int par1, Material par2Material) {
		super(par1, par2Material);
	}
	
	@Override
	public boolean isOpaqueCube(){
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}
	
	
	
}
