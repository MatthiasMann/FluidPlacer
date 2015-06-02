package fluidplacer.compatibility;

import java.text.DecimalFormat;
import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import fluidplacer.blocks.BlockFluidPlacer;
import fluidplacer.tileentities.TileFluidPlacer;

public class Waila implements IWailaDataProvider {
	
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}
	
	@Override
	public List<String> getWailaHead(ItemStack stack, List<String> currentTip,
			IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currentTip;
	}

	@Override
	public List<String> getWailaBody(ItemStack stack, List<String> currentTip,
			IWailaDataAccessor accessor, IWailaConfigHandler config) {
		if (accessor.getBlock() instanceof BlockFluidPlacer) {
			TileFluidPlacer teFluidPlacer = (TileFluidPlacer)accessor.getTileEntity();
			currentTip.add(getFluidPlacerDisplay(teFluidPlacer));
		}
		return currentTip;
	}

	@Override
	public List<String> getWailaTail(ItemStack stack, List<String> currentTip,
			IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currentTip;
	}

	public String getFluidPlacerDisplay(TileFluidPlacer fluidPlacer) {
		if(fluidPlacer.fluid.amount == 0)
			return "Empty";
		return fluidPlacer.fluid.getFluid().getName() + " " +
			fluidPlacer.fluid.amount + "mB";
	}
	
	public static void callbackRegister(IWailaRegistrar registrar) {
		Waila instance = new Waila();
		registrar.registerBodyProvider(instance, BlockFluidPlacer.class);
	}
}
