package ru.def.incantations.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


/**
 * Created by Defernus on 12.05.2017.
 */
public class TileEntityBookMonument extends TileEntity {

	public float yaw=0;
	public ItemStack stack = ItemStack.EMPTY;

	public void setStack(World world, BlockPos pos, ItemStack stack,float yaw){
		this.stack = stack;
		this.yaw=yaw;

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(),1,this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.stack = new ItemStack((NBTTagCompound)tag.getTag("item"));
		yaw=tag.getFloat("yaw");
	}



	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagCompound s_tag = new NBTTagCompound();
		this.stack.writeToNBT(s_tag);
		tag.setTag("item", s_tag);

		tag.setFloat("yaw",yaw);

		return tag;
	}
}