package ru.def.incantations.net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Defernus on 10.06.2017.
 */
public abstract class AbstractPacket<REQ extends IMessage> implements IMessage, IMessageHandler<REQ, REQ>
{
	@Override
	public REQ onMessage(final REQ message, final MessageContext ctx)
	{
		if(ctx.side == Side.SERVER)
		{
			handleServerSide(ctx.getServerHandler().playerEntity);
		}
		else
		{
			handleClientSide();
		}
		return null;
	}


	@SideOnly(Side.CLIENT)
	private void handleClientSide(){
		handleClientSide(FMLClientHandler.instance().getClientPlayerEntity());
	}
	public abstract void handleClientSide(final EntityPlayer player);
	public abstract void handleServerSide(final EntityPlayer player);
}