package ru.def.incantations.keybinds;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import ru.def.incantations.net.NetworkHandler;
import ru.def.incantations.net.PacketNextPage;

/**
 * Created by Defernus on 03.06.2017.
 */

@SideOnly(Side.CLIENT)
public class KeyBinder {
	public final KeyBinding nextPage;

	public KeyBinder() {
		nextPage = new KeyBinding("Next page", Keyboard.KEY_C, "Incantations");
		ClientRegistry.registerKeyBinding(nextPage);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if (nextPage.isPressed())
		{
			//Minecraft.getMinecraft().player.sendChatMessage("nextPage pressed!");
			NetworkHandler.INSTANCE.sendToServer(new PacketNextPage());
		}
	}
}
