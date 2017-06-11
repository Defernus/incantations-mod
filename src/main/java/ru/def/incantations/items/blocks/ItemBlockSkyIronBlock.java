package ru.def.incantations.items.blocks;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.blocks.BlocksRegister;

/**
 * Created by Defernus on 06.06.2017.
 */
public class ItemBlockSkyIronBlock extends ItemBlock {
	public ItemBlockSkyIronBlock() {
		super(BlocksRegister.SKY_IRON_BLOCK);
		this.setRegistryName("sky_iron_block");
		this.setUnlocalizedName("sky_iron_block");
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entity) {
		entity.setNoGravity(true);
		return super.onEntityItemUpdate(entity);
	}
}
