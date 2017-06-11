package ru.def.incantations.items;

import net.minecraft.item.ItemStack;

/**
 * Created by Defernus on 09.06.2017.
 */
public interface IChargeable {
	public ItemStack getChargedItem(ItemStack stack);
	public int getXPToCharge(ItemStack stack);
}
