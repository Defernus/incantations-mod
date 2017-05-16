package ru.def.incantations.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Defernus on 14.05.2017.
 */
public class TileEntityWritingTable  extends TileEntity {

	public boolean hasPaper = false;

	public BlockPos posWN=null,posES=null;

	public TileEntityWritingTable setPaper(BlockPos pos1, BlockPos pos2) {
		if(hasPaper)return this;

		hasPaper=true;

		posWN=pos1;
		posES=pos2;

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);

		return this;
	}

	public TileEntityWritingTable setNoPaper() {
		hasPaper=false;

		posES=null;
		posWN=null;

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);

		return this;
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

		hasPaper=tag.getBoolean("paper");

		if(tag.getBoolean("hasPos")){
			posWN=new BlockPos(tag.getInteger("posWNx"),tag.getInteger("posWNy"),tag.getInteger("posWNz"));
			posES=new BlockPos(tag.getInteger("posESx"),tag.getInteger("posESy"),tag.getInteger("posESz"));
		}
	}



	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setBoolean("paper", hasPaper);

		if(posWN!=null){
			tag.setBoolean("hasPos",true);
			tag.setInteger("posWNx",posWN.getX());
			tag.setInteger("posWNy",posWN.getY());
			tag.setInteger("posWNz",posWN.getZ());
			tag.setInteger("posESx",posES.getX());
			tag.setInteger("posESy",posES.getY());
			tag.setInteger("posESz",posES.getZ());
		}

		return tag;
	}
}
