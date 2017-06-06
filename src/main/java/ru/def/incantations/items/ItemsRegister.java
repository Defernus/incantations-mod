package ru.def.incantations.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.Core;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Dfernus on 10.05.2017.
 */
public class ItemsRegister {

	public static ItemIncantationsBook BASIC_BOOK=new ItemIncantationsBook(0);
	public static ItemIncantationsBook ANCIENT_BOOK=new ItemIncantationsBook(1);
	public static ItemIncantationsBook LEGENDARY_BOOK=new ItemIncantationsBook(2);
	public static ItemIncantationsBook MYTHICAL_BOOK=new ItemIncantationsBook(3);
	public static Item INCANTATIONS_BOOK_TAB=new Item().setRegistryName("incantations_book_ta_tab").setUnlocalizedName("incantations_book_ta");
	public static ItemRune RUNE=new ItemRune();
	public static ItemWrittenScroll WRITTEN_SCROLL=new ItemWrittenScroll();
	public static Item BLANK_SCROLL=(new Item()).setUnlocalizedName("scroll_blank").setRegistryName("scroll_blank").setCreativeTab(CreativeTabsHandler.MY_TAB);
	public static ItemSkyIronIngot SKY_IRON_INGOT=new ItemSkyIronIngot();
	public static ItemSkyIronOre SKY_IRON_ORE=new ItemSkyIronOre();

	public static void registerItems() {
		GameRegistry.register(BASIC_BOOK);
		GameRegistry.register(ANCIENT_BOOK);
		GameRegistry.register(LEGENDARY_BOOK);
		GameRegistry.register(MYTHICAL_BOOK);
		GameRegistry.register(INCANTATIONS_BOOK_TAB);
		GameRegistry.register(RUNE);
		GameRegistry.register(WRITTEN_SCROLL);
		GameRegistry.register(BLANK_SCROLL);
		GameRegistry.register(SKY_IRON_INGOT);
		GameRegistry.register(SKY_IRON_ORE);
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
		registerRender(BLANK_SCROLL);
		registerRender(SKY_IRON_INGOT);
		registerRender(SKY_IRON_ORE);
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,0, new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}

	public static void registerRender(Item item, int meta, String name) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,meta, new ModelResourceLocation(Core.MODID+":"+name,"inventory"));
	}
}
