package ru.def.incantations.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ru.def.incantations.CreativeTabsHandler;

/**
 * Created by Defernus on 17.05.2017.
 */
public class ItemRune extends Item {

	public enum EnumRune{
		//				name		id	ch	power	cd	type
		BLANK		("blank",		0,	0,		0,		0,	"blank"),
		USE			("use",			1,	1,		1,		0,	"activator"),
		RIGHT_CLICK	("right_click",	2,	2,		1,		0,	"activator"),
		DELAY		("delay",		3,	3,	 0.1f,		0,	"arg"),
		LOOK_AT		("look_at",		4,	4,		1,	   20,	"action_type"),
		EXPLOSION	("explosion",	5,	5,		3,	   40,	"action.look_at"),
		PROJECTILE	("projectile",	6,	6,		1,	   20,	"action_type"),
		FIREBALL	("fireball",	7,	7,	 2.5f,	   40,	"action.projectile"),
		ARROW		("arrow",		8,	8,		2,	   20,	"action.projectile");

		private final String NAME, TYPE;
		private final int ID, CHAR, CD;
		private final float POWER;

		EnumRune(String name, int id, int ch, float power, int cd, String type) {
			this.POWER = power;
			this.CD = cd;
			this.TYPE = type;
			this.NAME = name;
			this.ID = id;
			this.CHAR = ch;
		}

		public String getType(){
			return TYPE;
		}

		public String getName() {
			return NAME;
		}

		public int getID() {
			return ID;
		}

		public int getChar() {
			return CHAR;
		}
		public int getCD() {
			return CD;
		}
		public float getPower() {
			return POWER;
		}
	}

	public ItemRune() {
		this.setUnlocalizedName("rune");
		this.setRegistryName("rune");
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
		this.setMaxStackSize(1);
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> items) {
		for(int i = 0; i < EnumRune.values().length; i++) {
			items.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		for(int i = 0; i < EnumRune.values().length; i++) {
			if(stack.getItemDamage() == i){
				return this.getUnlocalizedName() + "." + EnumRune.values()[i].getName();
			}
		}
		return this.getUnlocalizedName() + "." + EnumRune.BLANK.getName();
	}
}
