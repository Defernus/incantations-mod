package ru.def.incantations.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.Core;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.items.blocks.ItemBlockSkyChargingTable;
import ru.def.incantations.items.blocks.ItemBlockSkyIronBlock;
import ru.def.incantations.items.blocks.ItemBlockSkyIronOre;

/**
 * Created by Defernus on 10.05.2017.
 */
public class ItemsRegister {

	public static ItemIncantationsBook BASIC_BOOK=new ItemIncantationsBook(0);
	public static ItemIncantationsBook ANCIENT_BOOK=new ItemIncantationsBook(1);
	public static ItemIncantationsBook LEGENDARY_BOOK=new ItemIncantationsBook(2);
	public static ItemIncantationsBook MYTHICAL_BOOK=new ItemIncantationsBook(3);
	public static Item INCANTATIONS_BOOK_TAB=new Item().setRegistryName("incantations_book_ta_tab").setUnlocalizedName("incantations_book_ta");
	public static ItemRune RUNE=new ItemRune();
	public static ItemWrittenScroll WRITTEN_SCROLL=new ItemWrittenScroll();
	public static ItemWrittenScrollCharged WRITTEN_SCROLL_CHARGED=new ItemWrittenScrollCharged();
	public static Item BLANK_SCROLL=(new Item()).setUnlocalizedName("scroll_blank").setRegistryName("scroll_blank").setCreativeTab(CreativeTabsHandler.MY_TAB);
	public static ItemSkyIronIngot SKY_IRON_INGOT=new ItemSkyIronIngot();
	public static ItemSkyIronIngotCharged SKY_IRON_INGOT_CHARGED=new ItemSkyIronIngotCharged();
	public static Item SKY_CHARGING_WAND=(new ItemChargingWand()).setRegistryName("sky_charging_wand").setUnlocalizedName("sky_charging_wand");
	public static ItemBlockSkyIronOre SKY_IRON_ORE=new ItemBlockSkyIronOre();
	public static ItemBlockSkyIronBlock SKY_IRON_BLOCK=new ItemBlockSkyIronBlock();
	public static ItemBlockSkyChargingTable SKY_CHARGING_TABLE=new ItemBlockSkyChargingTable();

	public static void registerItems() {
		//items
		GameRegistry.register(BASIC_BOOK);
		GameRegistry.register(ANCIENT_BOOK);
		GameRegistry.register(LEGENDARY_BOOK);
		GameRegistry.register(MYTHICAL_BOOK);
		GameRegistry.register(INCANTATIONS_BOOK_TAB);
		GameRegistry.register(RUNE);
		GameRegistry.register(WRITTEN_SCROLL);
		GameRegistry.register(WRITTEN_SCROLL_CHARGED);
		GameRegistry.register(BLANK_SCROLL);
		GameRegistry.register(SKY_IRON_INGOT);
		GameRegistry.register(SKY_IRON_INGOT_CHARGED);
		GameRegistry.register(SKY_CHARGING_WAND);

		//blocks
		GameRegistry.register(SKY_IRON_ORE);
		GameRegistry.register(SKY_IRON_BLOCK);
		GameRegistry.register(SKY_CHARGING_TABLE);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerRender(BASIC_BOOK);
		registerRender(ANCIENT_BOOK);
		registerRender(LEGENDARY_BOOK);
		registerRender(MYTHICAL_BOOK);

		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(INCANTATIONS_BOOK_TAB,0, new ModelResourceLocation(BASIC_BOOK.getRegistryName(),"inventory"));

		for(int i = 0; i < ItemRune.EnumRune.values().length; i++) {
			registerRender(RUNE, i, "rune_" + ItemRune.EnumRune.values()[i].getName());
		}

		registerRender(WRITTEN_SCROLL);
		registerRender(WRITTEN_SCROLL_CHARGED);
		registerRender(BLANK_SCROLL);
		registerRender(SKY_IRON_INGOT);
		registerRender(SKY_IRON_INGOT_CHARGED);
		registerRender(SKY_CHARGING_WAND);

		//blocks
		registerRender(SKY_IRON_ORE);
		registerRender(SKY_IRON_BLOCK);
		registerRender(SKY_CHARGING_TABLE);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,0, new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}

	public static void registerRender(Item item, int meta, String name) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,meta, new ModelResourceLocation(Core.MODID+":"+name,"inventory"));
	}
}
