package ru.def.incantations;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.items.ItemsRegister;

/**
 * Created by Defernus on 10.05.2017.
 */
public class CreativeTabsHandler {

	private final ItemStack iconItem=Items.ENCHANTED_BOOK.getEnchantedItemStack(new EnchantmentData(new EnchantmentArrowInfinite(Enchantment.Rarity.COMMON,EntityEquipmentSlot.CHEST), 0));

	public static final CreativeTabs MY_TAB = new CreativeTabs("my_tab")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ItemsRegister.INCANTATIONS_BOOK_TAB);
		}
	};
}
