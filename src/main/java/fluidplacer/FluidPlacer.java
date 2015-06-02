package fluidplacer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

import fluidplacer.blocks.BlockFluidPlacer;
import fluidplacer.items.ItemBlockFluidPlacer;

@Mod(modid = ModData.ID, name = ModData.NAME, version = ModData.VERSION, dependencies = ModData.DEPENDENCIES)
public class FluidPlacer
{
	@Instance(ModData.ID)
	public static FluidPlacer instance;
	
	public static Logger log;
	
	public static Block FluidPlacer;
	
	@EventHandler
	public void PreInitialize(FMLPreInitializationEvent event)
	{
		log = LogManager.getLogger(ModData.NAME);
		
		//Metadata!
		ModData.setMetadata(event.getModMetadata());
		
		FluidPlacer = new BlockFluidPlacer();
		GameRegistry.registerBlock(FluidPlacer, ItemBlockFluidPlacer.class, ModData.FLUID_PLACER_KEY);

		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@EventHandler
	public void Initialize(FMLInitializationEvent event)
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(
			new ItemStack(FluidPlacer, 1, 0),
			new Object[] {
				"xgx",
				"x x",
				"opo",
				'x', Items.iron_ingot, 
				'g', "blockGlass",
				'o', Blocks.obsidian,
				'p', Blocks.piston
			}));
		
		FMLInterModComms.sendMessage("Waila", "register", "fluidplacer.compatibility.Waila.callbackRegister");	
	}
}
