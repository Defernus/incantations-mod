package ru.def.incantations.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by Defernus on 17.05.2017.
 */
public class ItemWrittenScrollCharged extends Item {

	public ItemWrittenScrollCharged() {
		this.setUnlocalizedName("scroll_written_charged");
		this.setRegistryName("scroll_written_charged");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
