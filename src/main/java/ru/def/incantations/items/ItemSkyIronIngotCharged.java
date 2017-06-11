package ru.def.incantations.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 06.06.2017.
 */
public class ItemSkyIronIngotCharged extends Item {

	public ItemSkyIronIngotCharged() {
		this.setRegistryName( "sky_iron_ingot_charged" );
		this.setUnlocalizedName( "sky_iron_ingot_charged" );
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

	}

	@Override
	public boolean hasEffect(ItemStack p_hasEffect_1_) {
		return true;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}
}
