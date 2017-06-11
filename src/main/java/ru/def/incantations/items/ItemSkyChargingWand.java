package ru.def.incantations.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 09.06.2017.
 */
public class ItemSkyChargingWand extends Item {
	public ItemSkyChargingWand() {
		this.setRegistryName("sky_charging_wand");
		this.setUnlocalizedName("sky_charging_wand");
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}
}
