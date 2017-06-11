package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.items.ItemsRegister;

import java.util.Random;

/**
 * Created by Defernus on 06.06.2017.
 */
public class BlockSkyIronBlock extends Block {
	public BlockSkyIronBlock() {
		super(Material.ROCK);

		this.setRegistryName("sky_iron_block");
		this.setUnlocalizedName("sky_iron_block");
		this.setLightLevel(.7F);
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

		this.setHarvestLevel("pickaxe", 3);
		this.setResistance(20F);
		this.setHardness(3F);
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(ItemsRegister.SKY_IRON_BLOCK, 1, this.damageDropped(state));
	}

	@Override
	public Item getItemDropped(IBlockState p_getItemDropped_1_, Random p_getItemDropped_2_, int p_getItemDropped_3_) {
		return ItemsRegister.SKY_IRON_BLOCK;
	}

}
