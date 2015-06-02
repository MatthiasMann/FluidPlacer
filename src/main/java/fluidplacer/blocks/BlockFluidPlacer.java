package fluidplacer.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import fluidplacer.ModData;
import fluidplacer.tileentities.TileFluidPlacer;

public class BlockFluidPlacer extends Block implements ITileEntityProvider {
	public static IIcon topIcon;
	public static IIcon sideIcon;
	public static IIcon bottomIcon;
	
	public BlockFluidPlacer() {
		super(Material.ground);
		
		setHardness(0.8f);
		setStepSound(soundTypeMetal);
		setCreativeTab(CreativeTabs.tabBlock);
		GameRegistry.registerTileEntity(TileFluidPlacer.class, this.getUnlocalizedName());
	}

	@Override
	public String getUnlocalizedName()
	{
		return ModData.ID + "." + ModData.FLUID_PLACER_UNLOCALIZED_NAME;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register)
	{
		topIcon = register.registerIcon(ModData.TEXTURE_LOCATION + ":IconFluidPlacerTop");
		sideIcon = register.registerIcon(ModData.TEXTURE_LOCATION + ":IconFluidPlacerSide");
		bottomIcon = register.registerIcon(ModData.TEXTURE_LOCATION + ":IconFluidPlacerBottom");
		blockIcon = topIcon;
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileFluidPlacer();
	}
	
	@Override
	public boolean hasTileEntity(int meta)
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
    {
    	switch(side) {
    		case 0:
    			return bottomIcon;
    		case 1:
				return topIcon;
			default:
				return sideIcon;
		}
    }
}
