package ru.def.incantations.events;

import net.minecraftforge.common.MinecraftForge;
import ru.def.incantations.gui.GuiBook;
import ru.def.incantations.items.renders.RenderIncantationsBook;

/**
 * Created by Defernus on 30.05.2017.
 */
public class EventRegister {

	public static void reg() {
		MinecraftForge.EVENT_BUS.register(new RenderIncantationsBook());
	}
}
