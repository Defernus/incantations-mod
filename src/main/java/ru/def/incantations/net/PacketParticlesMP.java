package ru.def.incantations.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

/**
 * Created by Defernus on 10.06.2017.
 */
public class PacketParticlesMP extends AbstractPacket<PacketParticlesMP>
{
	private static int lx, ly, lz;

	public PacketParticlesMP() {}

	public PacketParticlesMP(final int lx, final int ly, final int lz)
	{
		this.lx = lx;
		this.ly = ly;
		this.lz = lz;
	}
	@Override
	public void handleClientSide(final EntityPlayer player) {}

	@Override
	public void handleServerSide(final EntityPlayer player)
	{

		NetworkHandler.sendToAllAround(new PacketChargingParticle(player.posX, player.posY, player.posZ, lx, ly, lz), player.world);
	}

	@Override
	public void fromBytes(final ByteBuf buf) {}

	@Override
	public void toBytes(final ByteBuf buf) {}
}