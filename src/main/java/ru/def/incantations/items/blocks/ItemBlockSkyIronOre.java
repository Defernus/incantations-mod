package ru.def.incantations.items.blocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.blocks.BlocksRegister;

/**
 * Created by Defernus on 06.06.2017.
 */
public class ItemBlockSkyIronOre extends ItemBlock {
	public ItemBlockSkyIronOre() {
		super(BlocksRegister.SKY_IRON_ORE);
		this.setRegistryName("sky_iron_ore");
		this.setUnlocalizedName("sky_iron_ore");
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}
}
