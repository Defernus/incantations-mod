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

	public static BlockBookMonument BOOK_MONUMENT = new BlockBookMonument();
	public static BlockWritingTable WRITING_TABLE = new BlockWritingTable();
	public static BlockQuartzLamp QUARTZ_LAMP = new BlockQuartzLamp();
	public static Block QUARTZ_BOOKSHELF = (new BlockQuartzBookshelf(false)).setRegistryName("quartz_bookshelf").setUnlocalizedName("quartz_bookshelf");
	public static Block QUARTZ_BOOKSHELF_ANCIENT = (new BlockQuartzBookshelf(true)).setRegistryName("quartz_bookshelf_ancient").setUnlocalizedName("quartz_bookshelf_ancient");
	public static BlockSkyIronOre SKY_IRON_ORE = new BlockSkyIronOre();
	public static BlockSkyIronBlock SKY_IRON_BLOCK = new BlockSkyIronBlock();
	public static BlockSkyChargingTable SKY_CHARGING_TABLE = new BlockSkyChargingTable();

	public static void registerBlocks() {
		registerBlock(BOOK_MONUMENT);
		registerBlock(WRITING_TABLE);
		registerBlock(QUARTZ_LAMP);
		registerBlock(QUARTZ_BOOKSHELF);
		registerBlock(QUARTZ_BOOKSHELF_ANCIENT);
		GameRegistry.register(SKY_IRON_ORE);
		GameRegistry.register(SKY_IRON_BLOCK);
		GameRegistry.register(SKY_CHARGING_TABLE);
	}

	private static void registerBlock(final Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
	}

	public static void registerRenders() {
		registerRender(BOOK_MONUMENT);
		registerRender(WRITING_TABLE);
		registerRender(QUARTZ_LAMP);
		registerRender(QUARTZ_BOOKSHELF);
		registerRender(QUARTZ_BOOKSHELF_ANCIENT);
		registerRender(SKY_IRON_ORE);
		registerRender(SKY_IRON_BLOCK);
		registerRender(SKY_CHARGING_TABLE);
	}

	@SideOnly(Side.CLIENT)
	private static void registerRender(Block block){
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}
