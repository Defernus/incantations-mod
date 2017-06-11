package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 05.06.2017.
 */
public class BlockQuartzBookshelf extends Block {

	public BlockQuartzBookshelf() {
		super(Material.ROCK);

		this.setRegistryName("quartz_bookshelf");
		this.setUnlocalizedName("quartz_bookshelf");
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}
}
