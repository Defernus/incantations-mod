package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 05.06.2017.
 */
public class BlockQuartzLamp extends Block {

	public BlockQuartzLamp() {
		super(Material.ROCK);

		this.setHarvestLevel("pickaxe", 1);
		this.setHardness(0.8F);

		this.setRegistryName("quartz_lamp");
		this.setUnlocalizedName("quartz_lamp");
		this.setLightLevel(1.0F);
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}
}
