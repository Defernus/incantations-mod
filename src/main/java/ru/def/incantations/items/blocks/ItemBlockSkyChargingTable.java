package ru.def.incantations.items.blocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.blocks.BlocksRegister;

/**
 * Created by Defernus on 06.06.2017.
 */
public class ItemBlockSkyChargingTable extends ItemBlock {
	public ItemBlockSkyChargingTable() {
		super(BlocksRegister.SKY_CHARGING_TABLE);
		this.setRegistryName("sky_charging_table");
		this.setUnlocalizedName("sky_charging_table");
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}
}
