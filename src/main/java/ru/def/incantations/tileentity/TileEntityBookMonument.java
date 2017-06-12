package ru.def.incantations.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.items.ItemIncantationsBook;


/**
 * Created by Defernus on 12.05.2017.
 */
public class TileEntityBookMonument extends TileEntity implements ISidedInventory {

	public float yaw=0;
	public ItemStack stack = ItemStack.EMPTY;

	public void setStack(ItemStack stack, float yaw){
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

		this.stack = new ItemStack((NBTTagCompound)tag.getTag("stack"));
		yaw=tag.getFloat("yaw");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagCompound s_tag = new NBTTagCompound();
		this.stack.writeToNBT(s_tag);
		tag.setTag("stack", s_tag);

		tag.setFloat("yaw",yaw);

		return tag;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index == 0 && stack.isEmpty() && itemStackIn.getItem() instanceof ItemIncantationsBook;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index == 0;
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0? stack: ItemStack.EMPTY;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if(count > 0 && index == 0) {
			ItemStack stackToReturn = stack;
			setStack(ItemStack.EMPTY, 0);
			return stackToReturn;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stackToReturn = stack;
		setStack(ItemStack.EMPTY, 0);
		return stackToReturn;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(index == 0) {
			setStack(stack, 0);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && stack.getItem() instanceof ItemIncantationsBook;
	}

	@Override
	public int getField(int id) {
		return -1;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		setStack(ItemStack.EMPTY, 0);
	}

	@Override
	public String getName() {
		return BlocksRegister.BOOK_MONUMENT.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}
}
