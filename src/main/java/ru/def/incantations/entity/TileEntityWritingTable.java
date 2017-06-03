package ru.def.incantations.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

/**
 * Created by Defernus on 14.05.2017.
 */
public class TileEntityWritingTable  extends TileEntity {

	public boolean hasPaper = false;

	public BlockPos posWN=null,posES=null;

	public int facing;

	public int[] chars = new int[]{0,0,0,0};

	private static Random rnd=new Random();

	public TileEntityWritingTable setPaper(BlockPos pos1, BlockPos pos2, EnumFacing facing) {

		if(hasPaper)return this;

		this.facing = facing.getHorizontalIndex();

		hasPaper=true;

		posWN=pos1;
		posES=pos2;

		//chars=new int[]{rnd.nextInt(40), rnd.nextInt(40), rnd.nextInt(40), rnd.nextInt(40)};

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);

		return this;
	}

	public TileEntityWritingTable setChar(int ch, int x, int y){
		if(!hasPaper)return this;

		int z=y;
		switch (facing){
			case 1:
				y=1-x;
				x=z;
				break;
			case 2:
				y=1-y;
				x=1-x;
				break;
			case 3:
				y=x;
				x=1-z;
				break;
		}

		chars[x+y*2]=ch;

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);

		return this;
	}

	public TileEntityWritingTable setNoPaper() {
		hasPaper=false;

		posES=null;
		posWN=null;

		chars=new int[]{0,0,0,0};

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

		if(tag.getBoolean("hasPos")) {
			chars=tag.getIntArray("chars");

			posWN=new BlockPos(tag.getInteger("posWNx"), tag.getInteger("posWNy"),tag.getInteger("posWNz"));
			posES=new BlockPos(tag.getInteger("posESx"), tag.getInteger("posESy"),tag.getInteger("posESz"));

			facing=tag.getInteger("facing");
		}
	}



	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		tag.setBoolean("paper", hasPaper);


		if(posWN!=null){
			tag.setIntArray("chars", chars);
			tag.setInteger("facing", facing);
			tag.setBoolean("hasPos", true);
			tag.setInteger("posWNx", posWN.getX());
			tag.setInteger("posWNy", posWN.getY());
			tag.setInteger("posWNz", posWN.getZ());
			tag.setInteger("posESx", posES.getX());
			tag.setInteger("posESy", posES.getY());
			tag.setInteger("posESz", posES.getZ());
		}

		return tag;
	}
}
