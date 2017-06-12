package ru.def.incantations.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.items.IChargeable;
import ru.def.incantations.items.ItemChargingWand;
import ru.def.incantations.utils.ChargerUtils;

/**
 * Created by Defernus on 09.06.2017.
 */
public class TileEntitySkyChargingTable extends TileEntity implements ISidedInventory {

	public ItemStack stack = ItemStack.EMPTY;
	public int xpInCharger = 0;
	public float yaw=0;

	public TileEntitySkyChargingTable() {
		super();
	}

	public void setStack(ItemStack stack, float yaw){
		this.yaw = yaw;
		this.stack = stack;
		xpInCharger = 0;

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
	}

	public boolean chargeByPlayer(EntityPlayer player, ItemStack wand) {

		if( !(stack.getItem() instanceof  IChargeable && wand.getItem() instanceof ItemChargingWand) )return false;

		int xpBonus = ChargerUtils.getXpBonus(world, pos) + (int)( ((ItemChargingWand)wand.getItem()).getXpBonus(wand)*15 );

		int xpToCharge = (int)( ((IChargeable)stack.getItem()).getXPToCharge(stack) / (xpBonus/15.+1) );
		if( xpToCharge > 2500 ) {
			return false;
		}

		int xpPerUse = 4;
		if(xpToCharge-xpInCharger <= xpPerUse && xpToCharge-xpInCharger <= player.experienceTotal ) {

			ChargerUtils.addXpToPlayer(player, xpInCharger-xpToCharge);
			setStack( ((IChargeable)stack.getItem()).getChargedItem(stack), yaw );
			return true;
		}else if( player.experienceTotal >= xpPerUse ) {
			xpInCharger += xpPerUse;

			ChargerUtils.addXpToPlayer(player, -xpPerUse);

			this.markDirty();
			world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
			return true;
		}

		return false;
	}


	//TileEntity
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
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagCompound s_tag = new NBTTagCompound();
		this.stack.writeToNBT(s_tag);
		tag.setTag("stack", s_tag);

		tag.setInteger("xp", xpInCharger);
		tag.setFloat("yaw",yaw);

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.stack = new ItemStack((NBTTagCompound)tag.getTag("stack"));

		xpInCharger = tag.getInteger("xp");
		yaw=tag.getFloat("yaw");
	}


	//ISidedInventory
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index == 0 && this.stack.isEmpty() && itemStackIn.getItem() instanceof  IChargeable;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return index == 0 && !(itemStackIn.getItem() instanceof IChargeable);
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
		return index == 0 && !(stack.getItem() instanceof IChargeable)? stack : ItemStack.EMPTY;
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
		if(index == 0) {
			ItemStack stackToReturn = stack;
			setStack(ItemStack.EMPTY, 0);
			return stackToReturn;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		setStack(stack, 0);
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
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 0 && this.stack.isEmpty() && stack.getItem() instanceof IChargeable;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0:
				return xpInCharger;
		}
		return -1;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
		setStack(ItemStack.EMPTY, 0);
	}

	@Override
	public String getName() {
		return BlocksRegister.SKY_CHARGING_TABLE.getLocalizedName();
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	/*@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
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
		return index == 0 && stack.getItem() instanceof IChargeable;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0:
				return xpInCharger;
		}
		return -1;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
		setStack(ItemStack.EMPTY, 0);
	}
*/
}
