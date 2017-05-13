package ru.def.incantations.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.def.incantations.entity.TileEntityBookMonument;

/**
 * Created by Defernus on 12.05.2017.
 */
public class ContainerBookMonument extends Container {

	public ContainerBookMonument(InventoryPlayer inv, TileEntityBookMonument tile){


		addSlotToContainer(new SlotIncantationsBook(tile.inv, 0, 80,16));

		for(int i=0;i<27;i++){
			addSlotToContainer(new Slot(inv, i+10, 8+(i%9)*18, 46+(i/9)*18));
		}

		for(int i=0;i<9;i++){
			addSlotToContainer(new Slot(inv, i+1, 8+(i%9)*18, 104));
		}
	}



	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 0)
			{
				if (!this.mergeItemStack(itemstack1, 1, 37, true))
				{
					return ItemStack.EMPTY;
				}
			}
			else
			{
				if ((this.inventorySlots.get(0)).getHasStack() || !(this.inventorySlots.get(0)).isItemValid(itemstack1))
				{
					return ItemStack.EMPTY;
				}

				if (itemstack1.hasTagCompound() && itemstack1.getCount() == 1)
				{
					(this.inventorySlots.get(0)).putStack(itemstack1.copy());
					itemstack1.setCount(0);
				}
				else if (!itemstack1.isEmpty())
				{
					(this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
					itemstack1.shrink(1);
				}
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, itemstack1);
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
}
