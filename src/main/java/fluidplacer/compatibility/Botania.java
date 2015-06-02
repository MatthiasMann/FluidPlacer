package fluidplacer.compatibility;

import net.minecraft.tileentity.TileEntity;

import vazkii.botania.api.item.IPetalApothecary;

public class Botania {
	
	private Botania() {}
	
	public static boolean isIPetalApothecary(TileEntity te) {
		return te instanceof IPetalApothecary;
	}
	
	public static boolean hasWater(TileEntity te) {
		return ((IPetalApothecary)te).hasWater();
	}
	
	public static void setWater(TileEntity te, boolean water) {
		((IPetalApothecary)te).setWater(water);
	}
}
