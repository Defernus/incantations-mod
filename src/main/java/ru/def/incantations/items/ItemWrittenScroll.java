package ru.def.incantations.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 17.05.2017.
 */
public class ItemWrittenScroll extends Item {

	public ItemWrittenScroll() {
		this.setUnlocalizedName("scroll_written");
		this.setRegistryName("scroll_written");
		this.setMaxStackSize(1);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
