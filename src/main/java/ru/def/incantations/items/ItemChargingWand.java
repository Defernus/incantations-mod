package ru.def.incantations.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 09.06.2017.
 */
public class ItemChargingWand extends Item {
	public ItemChargingWand() {
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}

	public double getXpBonus(ItemStack wand) {
		return 0;
	}
}
