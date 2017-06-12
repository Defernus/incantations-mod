package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.items.ItemsRegister;

import java.util.Random;

/**
 * Created by Defernus on 05.06.2017.
 */
public class BlockQuartzBookshelf extends Block {

	private boolean isAncient;

	public BlockQuartzBookshelf(boolean isAncient) {
		super(Material.ROCK);

		this.setHarvestLevel("pickaxe", 1);
		this.setHardness(0.8F);

		this.isAncient = isAncient;

		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}


	@Override
	public int quantityDropped(Random random)
	{
		return 3;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rnd, int fortune)
	{
		if(!isAncient)return Items.BOOK;

		int a = rnd.nextInt(1000);

		if( a < 75 ) {
			if( a < 25 ) {
				if( a < 10 ) {
					if( a < 2) {
						return ItemsRegister.MYTHICAL_BOOK;
					}
					return ItemsRegister.LEGENDARY_BOOK;
				}
				return ItemsRegister.ANCIENT_BOOK;
			}
			return ItemsRegister.BASIC_BOOK;
		}
		return Items.BOOK;
	}

	@Override
	public float getEnchantPowerBonus(World world, BlockPos pos) {
		return 2;
	}
}
