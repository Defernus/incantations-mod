package ru.def.incantations.items;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Defernus on 12.05.2017.
 */
public class Rune extends Item {
	public int id;

	public Rune(int id) {
		this.id=id;
	}

	public NBTTagCompound getTag(){
		NBTTagCompound tag=new NBTTagCompound();
		return tag;
	}
}
