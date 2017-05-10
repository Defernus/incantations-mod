package ru.def.incantations;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

/**
 * Created by Defernus on 10.05.2017.
 */
public class CreativeTabsHandler extends CreativeTabs {

	public static CreativeTabsHandler MY_TAB;

	public static void tabRegister(){
		MY_TAB=new CreativeTabsHandler(CreativeTabs.getNextID(),"my_tab");
	}

	private final ItemStack iconItem=Items.ENCHANTED_BOOK.getEnchantedItemStack(new EnchantmentData(new EnchantmentArrowInfinite(Enchantment.Rarity.COMMON,EntityEquipmentSlot.CHEST), 0));

	public CreativeTabsHandler(int index, String label) {
		super(index, label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return iconItem;
	}
}
