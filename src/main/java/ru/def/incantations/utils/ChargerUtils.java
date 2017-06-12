package ru.def.incantations.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.def.incantations.blocks.BlocksRegister;

/**
 * Created by Defernus on 12.06.2017.
 */
public class ChargerUtils {
	public static int getXpBonus(World world, BlockPos pos) {
		int bs = 0;
		int xpBonus = 0;

		for(int i = -2; i <= 2; i++) {
			for(int j = -2; j <= 2; j++) {
				if(bs>=15) break;
				if( (i == 2 || i == -2 || j == -2 || j == -2) ){
					BlockPos pos1 = new BlockPos(pos.getX()+i, pos.getY(), pos.getZ()+j);
					if( world.getBlockState(pos1).getBlock().getEnchantPowerBonus(world, pos1) > 0 ) {
						bs++;
						xpBonus += world.getBlockState(pos1).getBlock().getEnchantPowerBonus(world, pos1);
					}
				}
			}
		}
		return xpBonus;
	}

	public static void addXpToPlayer(EntityPlayer player, int amount) {
		int experience = player.experienceTotal + amount;
		player.experienceTotal = experience;
		player.experienceLevel = getLevelForXp(experience);
		int expForLevel = getXpForLevel(player.experienceLevel);
		player.experience = (float)(experience - expForLevel) / (float)player.xpBarCap();
	}

	public static int getXpForLevel(int level) {
		if(level == 0) {
			return 0;
		}

		if(level > 0 && level < 16) {
			return level * 17;
		}

		if(level > 15 && level < 31) {
			return (int)(1.5 * Math.pow(level, 2) - 29.5 * level + 360);
		}

		return (int)(3.5 * Math.pow(level, 2) - 151.5 * level + 2220);
	}

	public static int getLevelForXp(int experience) {
		int i = 0;
		while(getXpForLevel(i+1) <= experience) {
			i++;
		}

		return i;
	}
}
