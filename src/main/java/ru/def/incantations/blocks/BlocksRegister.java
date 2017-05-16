package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Defernus on 10.05.2017.
 */
public class BlocksRegister {

	public static BlockBookMonument BOOK_MONUMENT=new BlockBookMonument(Material.ROCK);
	public static BlockWritingTable WRITING_TABLE=new BlockWritingTable(Material.ROCK);

	public static void registerBlocks() {
		registerBlock(BOOK_MONUMENT);
		registerBlock(WRITING_TABLE);
	}

	private static void registerBlock(final Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	public static void registerRenders() {
		registerRender(BOOK_MONUMENT);
		registerRender(WRITING_TABLE);
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
