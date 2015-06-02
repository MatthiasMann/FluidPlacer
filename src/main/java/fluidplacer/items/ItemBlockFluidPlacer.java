package fluidplacer.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import fluidplacer.ModData;

public class ItemBlockFluidPlacer extends ItemBlock
{
	public ItemBlockFluidPlacer(Block block) {
		super(block);
	}
	
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return ModData.ID + "." + ModData.FLUID_PLACER_UNLOCALIZED_NAME;
	}
}
