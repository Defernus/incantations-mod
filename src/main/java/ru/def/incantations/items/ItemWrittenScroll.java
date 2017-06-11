package ru.def.incantations.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 17.05.2017.
 */
public class ItemWrittenScroll extends Item implements IChargeable {

	public ItemWrittenScroll() {
		this.setUnlocalizedName("scroll_written");
		this.setRegistryName("scroll_written");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack getChargedItem(ItemStack stack) {
		ItemStack newStack = new ItemStack(ItemsRegister.WRITTEN_SCROLL_CHARGED);
		newStack.setTagCompound(stack.getTagCompound());
		return newStack;
	}

	@Override
	public int getXPToCharge(ItemStack stack) {
		float power = stack.getTagCompound().getFloat("power");
		return (int)(power*power*10);
	}
}
