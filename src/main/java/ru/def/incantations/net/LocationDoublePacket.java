package ru.def.incantations.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

/**
 * Created by Defernus on 10.06.2017.
 */
public abstract  class LocationDoublePacket<REQ extends IMessage> extends AbstractPacket<REQ>
{
	protected double px, py, pz, lx, ly, lz;

	public LocationDoublePacket(){}

	public LocationDoublePacket(final double px, final double py, final double pz, final double lx, final double ly, final double lz)
	{
		this.px = px;
		this.py = py;
		this.pz = pz;
		this.lx = lx;
		this.ly = ly;
		this.lz = lz;
	}

	//Запись переданных Double значений.
	@Override
	public void toBytes(final ByteBuf buf)
	{
		buf.writeDouble(px);
		buf.writeDouble(py);
		buf.writeDouble(pz);
		buf.writeDouble(lx);
		buf.writeDouble(ly);
		buf.writeDouble(lz);
	}

	//Чтение переданных Double значений
	@Override
	public void fromBytes(final ByteBuf buf)
	{
		px = buf.readDouble();
		py = buf.readDouble();
		pz = buf.readDouble();
		lx = buf.readDouble();
		ly = buf.readDouble();
		lz = buf.readDouble();
	}

	//Данный код получает позицию игрока в мире. И расширяет зону в которой будет действовать пакет, в данном случае на 64 блока.
	public NetworkRegistry.TargetPoint getTargetPoint(final World world)
	{
		return getTargetPoint(world, 64);
	}
	public NetworkRegistry.TargetPoint getTargetPoint(final World world, final double updateDistance)
	{
		return new NetworkRegistry.TargetPoint(world.provider.getDimension(), px, py, pz, updateDistance);
	}
}
