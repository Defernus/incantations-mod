package ru.def.incantations.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.items.IChargeable;

/**
 * Created by Defernus on 09.06.2017.
 */
public class TileEntitySkyChargingTable extends TileEntity {

	public ItemStack stack = ItemStack.EMPTY;
	public int xpToCharge = 0;
	public float yaw=0;

	public TileEntitySkyChargingTable() {
		super();
	}

	public void setStack(ItemStack stack, float yaw){
		this.yaw = yaw;
		this.stack = stack;
		xpToCharge = 0;

		this.markDirty();
		world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
	}

	public boolean charge(EntityPlayer player) {

		int bs = 0;
		int xpBonus = 0;

		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				if(bs>=15) break;
				if( (i == 2 || i == -2 || j == -2 || j == -2) ){
					if( world.getBlockState(new BlockPos(pos.getX()+i, pos.getY(), pos.getZ()+j)).getBlock() == Blocks.BOOKSHELF) {
						bs++;
						xpBonus++;
					}else if(world.getBlockState(new BlockPos(pos.getX()+i, pos.getY(), pos.getZ()+j)).getBlock() == BlocksRegister.QUARTZ_BOOKSHELF) {
						bs++;
						xpBonus+=4;
					}
				}
			}
		}

		if(stack.getItem() instanceof  IChargeable) {
			int stackXP = ((IChargeable)stack.getItem()).getXPToCharge(stack);
			if( stackXP > 10000 ) {
				return false;
			}

			int xpPerUse = 4;
			int xpToAdd = (int)(xpPerUse * (1+xpBonus/15.));
			if( player.experienceTotal > xpPerUse ) {
				if(stackXP-xpToCharge <= xpToAdd) {

					addPlayerXP(player, (xpToCharge-stackXP)*4/xpToAdd);
					setStack( ((IChargeable)stack.getItem()).getChargedItem(stack), yaw );
					return true;
				}else {
					xpToCharge += xpToAdd;

					addPlayerXP(player, -xpPerUse);

					this.markDirty();
					world.notifyBlockUpdate(pos,world.getBlockState(pos),world.getBlockState(pos),3);
					return true;
				}
			}
		}
		return false;
	}

	public static void addPlayerXP(EntityPlayer player, int amount) {
		player.sendMessage(new TextComponentString(amount+""));
		int experience = player.experienceTotal + amount;
		player.experienceTotal = experience;
		player.experienceLevel = getLevelForExperience(experience);
		int expForLevel = getExperienceForLevel(player.experienceLevel);
		player.experience = (float)(experience - expForLevel) / (float)player.xpBarCap();
	}

	public static int getExperienceForLevel(int level) {
		if (level == 0) { return 0; }
		if (level > 0 && level < 16) {
			return level * 17;
		} else if (level > 15 && level < 31) {
			return (int)(1.5 * Math.pow(level, 2) - 29.5 * level + 360);
		} else {
			return (int)(3.5 * Math.pow(level, 2) - 151.5 * level + 2220);
		}
	}

	public static int getLevelForExperience(int experience) {
		int i = 0;
		while (getExperienceForLevel(i) <= experience) {
			i++;
		}
		return i - 1;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.getPos(),1,this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);

		NBTTagCompound s_tag = new NBTTagCompound();
		this.stack.writeToNBT(s_tag);
		tag.setTag("stack", s_tag);

		tag.setInteger("xp", xpToCharge);
		tag.setFloat("yaw",yaw);

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);

		this.stack = new ItemStack((NBTTagCompound)tag.getTag("stack"));

		xpToCharge = tag.getInteger("xp");
		yaw=tag.getFloat("yaw");
	}
}
