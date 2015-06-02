package fluidplacer.tileentities;

import cpw.mods.fml.common.Loader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import fluidplacer.compatibility.Botania;
import fluidplacer.compatibility.ModChecks;

public class TileFluidPlacer extends TileEntity implements IFluidHandler {
	
	public FluidStack fluid;
	
	private boolean needsUpdate;
	private int timer;
	
	public TileFluidPlacer() {
		fluid = new FluidStack(FluidRegistry.WATER, 0);
	}
	
	@Override
	public void updateEntity()
	{
		if(++timer >= 20) {
			timer = 0;
			
			if(!worldObj.isRemote && fluid.amount >= 1000) {
				tryPlaceFluid();
    		}
			
			if(needsUpdate) {
				needsUpdate = false;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
	}
	
	private void tryPlaceFluid()
	{
		Block worldBlock = worldObj.getBlock(xCoord, yCoord+1, zCoord);
		if(worldBlock == null || worldBlock == Blocks.air) {
			Block fluidWorldBlock = fluid.getFluid().getBlock();
			worldObj.setBlock(xCoord, yCoord+1, zCoord, fluidWorldBlock);
			fluid.amount -= 1000;
			needsUpdate = true;
		} else if(ModChecks.hasBotania()) {
			tryBotaniaSupport();
		}
	}
	
	private void tryBotaniaSupport() {
		if(fluid.getFluid() == FluidRegistry.WATER) {
			TileEntity te = worldObj.getTileEntity(xCoord, yCoord+1, zCoord);
			//System.out.println("We have water and a " + te);
			if(te != null && Botania.isIPetalApothecary(te)) {
				if(!Botania.hasWater(te)) {
					Botania.setWater(te, true);
					fluid.amount -= 1000;
					needsUpdate = true;
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		
		fluid = new FluidStack(FluidRegistry.getFluid(compound.getShort("fluid")), compound.getInteger("amount"));
		needsUpdate = true;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("amount", fluid.amount);
		compound.setShort("fluid", (short)fluid.fluidID);
	}
	
	@Override
	public Packet getDescriptionPacket() {
    	NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }
	
	@Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	//IFluidHandler!	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(resource == null)
			return 0;
		
		if(fluid.amount > 0 && fluid.fluidID != resource.fluidID)
			return 0;
		
		int fillAmount = Math.min(1000 - fluid.amount, resource.amount);
		
		if(!doFill || fillAmount <= 0)
			return fillAmount;
		
		fluid.amount += fillAmount;
		
		if(fluid.fluidID != resource.fluidID)
			fluid = new FluidStack(FluidRegistry.getFluid(resource.fluidID), fluid.amount);
		
		needsUpdate = true;
		return fillAmount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {
			new FluidTankInfo(fluid, 1000)
		};
	}

}
