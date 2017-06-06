package ru.def.incantations.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 06.06.2017.
 */
public class ItemSkyIronIngot extends Item {
	public ItemSkyIronIngot() {
		this.setRegistryName("sky_iron_ingot");
		this.setUnlocalizedName("sky_iron_ingot");
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}
}
