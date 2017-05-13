package ru.def.incantations.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.Core;

/**
 * Created by Dfernus on 10.05.2017.
 */
public class ItemsRegister {

	public static ItemIncantationsBook INCANTATIONS_BOOK=new ItemIncantationsBook(false,"incantations_book_ta");
	public static ItemIncantationsBook INCANTATIONS_BOOK_TAB=new ItemIncantationsBook(true,"incantations_book_ta_tab");

	public static void registerItems() {
		GameRegistry.register(INCANTATIONS_BOOK);
		GameRegistry.register(INCANTATIONS_BOOK_TAB);
	}

	@SideOnly(Side.CLIENT)
	public static void registerRenders() {
		registerRender(INCANTATIONS_BOOK);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(INCANTATIONS_BOOK_TAB,0, new ModelResourceLocation(INCANTATIONS_BOOK.getRegistryName(),"inventory"));
	}

	public static void registerRender(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item,0, new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}
}
