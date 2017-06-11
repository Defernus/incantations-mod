package ru.def.incantations.net;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import ru.def.incantations.Core;

/**
 * Created by Defernus on 10.06.2017.
 */
public class NetworkHandler
{
	public static final NetworkHandler INSTANCE = new NetworkHandler();
	// NetworkName - название канала
	public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel(Core.MODID+":network");
	private static int dec;

	public NetworkHandler(){}

	public static void init()
	{
		NETWORK.registerMessage(PacketParticlesMP.class, PacketParticlesMP.class, dec++, Side.SERVER);//Регистрация пакета для работы на серверной стороне.
		NETWORK.registerMessage(PacketChargingParticle.class, PacketChargingParticle.class, dec++, Side.CLIENT);//Регистрация пакета для работы на клиентской стороне.
		NETWORK.registerMessage(PacketNextPage.Handler.class, PacketNextPage.class, dec++, Side.SERVER);
	}

	//Метод который отправляет пакеты всем на клиенте/сервере(Зависит от ситуации)
	public static void sendToAll(final IMessage message) {
		NETWORK.sendToAll(message);
	}
	//Метод который отправляет пакет конкретному игроку.
	public static void sendTo(final IMessage message, final EntityPlayerMP player) {
		NETWORK.sendTo(message, player);
	}
	//Наш пользовательский метод, который использует LocationDoublePacket для упрощения отправки пакетов методом sendToAllAround.
	public static void sendToAllAround(final LocationDoublePacket message, final World world) {
		sendToAllAround(message, message.getTargetPoint(world));
	}
	//Метод который отправляет пакет всем игрокам от отправителя, в определённом радиусе.
	public static void sendToAllAround(final IMessage message, final NetworkRegistry.TargetPoint point) {
		NETWORK.sendToAllAround(message, point);
	}
	//Метод который отправляет пакет в определённое измерение.
	public static void sendToDimension(final IMessage message, final int dimensionId) {
		NETWORK.sendToDimension(message, dimensionId);
	}
	//Метод который отправляет пакет на сервер.
	public static void sendToServer(final IMessage message){
		NETWORK.sendToServer(message);
	}
}