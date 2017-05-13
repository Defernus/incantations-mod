package ru.def.incantations.containers;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.def.incantations.items.ItemIncantationsBook;

/**
 * Created by Defernus on 12.05.2017.
 */
public class SlotIncantationsBook extends Slot {

	public SlotIncantationsBook(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stack.isEmpty()?super.getItemStackLimit(stack):1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemIncantationsBook && super.isItemValid(stack);
	}
}
