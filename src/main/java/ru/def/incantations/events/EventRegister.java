package ru.def.incantations.events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.gui.GuiBook;
import ru.def.incantations.items.renders.RenderIncantationsBook;
import ru.def.incantations.keybinds.KeyBinder;

/**
 * Created by Defernus on 30.05.2017.
 */
public class EventRegister {

	@SideOnly(Side.CLIENT)
	public static void regClient() {
		MinecraftForge.EVENT_BUS.register(new RenderIncantationsBook());
		MinecraftForge.EVENT_BUS.register(new KeyBinder());
	}
	public static void regCommon() {

	}
}
