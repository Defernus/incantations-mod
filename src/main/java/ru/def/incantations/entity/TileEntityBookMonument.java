package ru.def.incantations.entity;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by Defernus on 12.05.2017.
 */
public class TileEntityBookMonument extends TileEntity {

	public InventoryBasic inv=new InventoryBasic(new TextComponentString("Book monument"),1);

	ItemStack lastStack = ItemStack.EMPTY;

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		inv.setInventorySlotContents(0,new ItemStack(tag));

		markDirty();

	}



	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {

		inv.getStackInSlot(0).writeToNBT(tag);

		markDirty();

		return tag;
	}

	@Override
	public void markDirty() {

		if(lastStack.getItem()!=inv.getStackInSlot(0).getItem()){
			super.markDirty();
			lastStack=inv.getStackInSlot(0);
		}
	}
}
