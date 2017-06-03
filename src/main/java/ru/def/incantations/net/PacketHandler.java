package ru.def.incantations.net;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.def.incantations.Core;

/**
 * Created by Defernus on 03.06.2017.
 */
public class PacketHandler {
	public static SimpleNetworkWrapper INSTANCE = null;

	private static int ID = 0;

	public static int nextID()
	{
		return ID++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Core.MODID);
		INSTANCE.registerMessage(PacketNextPage.Handler.class, PacketNextPage.class, nextID(), Side.SERVER);
	}
}
