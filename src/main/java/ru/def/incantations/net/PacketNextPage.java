package ru.def.incantations.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.def.incantations.items.ItemIncantationsBook;

/**
 * Created by Defernus on 03.06.2017.
 */
public class PacketNextPage implements IMessage {
	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public PacketNextPage() {
	}

	public static class Handler implements IMessageHandler<PacketNextPage, IMessage> {
		@Override
		public IMessage onMessage(PacketNextPage message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(PacketNextPage message, MessageContext ctx) {
			EntityPlayer playerEntity = ctx.getServerHandler().playerEntity;
			ItemStack stack = playerEntity.getHeldItem( EnumHand.MAIN_HAND ).getItem() instanceof ItemIncantationsBook?playerEntity.getHeldItem( EnumHand.MAIN_HAND ):playerEntity.getHeldItem( EnumHand.OFF_HAND ).getItem() instanceof ItemIncantationsBook?playerEntity.getHeldItem( EnumHand.OFF_HAND ):null;

			System.out.println(System.currentTimeMillis()-stack.getTagCompound().getLong( "pageTurning"));

			if(stack!=null&&stack.getTagCompound().getInteger( "cur_inc" )!=-1 && (!stack.getTagCompound().hasKey( "pageTurning") || System.currentTimeMillis()-stack.getTagCompound().getLong( "pageTurning")>=1000)){
				for(int i = 0; i < stack.getTagCompound().getInteger( "max_inc" ); i++) {
					int j = ( i+stack.getTagCompound().getInteger(  "cur_inc"  ) ) % stack.getTagCompound().getInteger( "max_inc" );
					if(j!=stack.getTagCompound().getInteger( "cur_inc" ) && stack.getTagCompound().getCompoundTag( "incantations" ).hasKey( ""+j )) {
						stack.getTagCompound().setLong( "pageTurning", System.currentTimeMillis() );
						stack.getTagCompound().setInteger( "last_inc", stack.getTagCompound().getInteger( "cur_inc" ) );
						stack.getTagCompound().setInteger( "cur_inc", j );
						return;
					}
				}
			}
		}
	}
}
