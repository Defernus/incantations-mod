package ru.def.incantations.items;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 06.06.2017.
 */
public class ItemSkyIronIngot extends Item implements IChargeable {

	public ItemSkyIronIngot() {
		this.setRegistryName( "sky_iron_ingot" );
		this.setUnlocalizedName( "sky_iron_ingot" );
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

	}

	@Override
	public boolean hasEffect(ItemStack p_hasEffect_1_) {
		return false;
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}

	@Override
	public ItemStack getChargedItem(ItemStack stack) {
		return new ItemStack(ItemsRegister.SKY_IRON_INGOT_CHARGED);
	}

	@Override
	public int getXPToCharge(ItemStack stack) {
		return 40;
	}

}
