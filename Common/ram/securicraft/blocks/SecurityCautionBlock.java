package ram.securicraft.blocks;

import net.minecraft.block.material.Material;

public class SecurityCautionBlock extends SecurityBlock {
	
	public SecurityCautionBlock(int id, Material material){
		super(id,material);
	}
	
	@Override
	public boolean isOpaqueCube(){
		return true;
	}
}
