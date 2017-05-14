package ru.def.incantations.incantations;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

/**
 * Created by Defernus on 11.05.2017.
 */
public class IncantationHandler {

	public static float explosionStr=4;

	public static boolean onUsed(EntityPlayer player, BlockPos pos, World worldIn, NBTTagCompound tag){
		if(tag.hasKey("used")){

		}
		return false;
	}

	public static boolean onClicked(EntityPlayer player, World worldIn, NBTTagCompound tag){
		//if(tag.hasKey("clicked")){

		RayTraceResult result = player.rayTrace(40f,0.2f);

		if(!worldIn.isAirBlock(result.getBlockPos())){
			Vec3d v = result.hitVec;
			worldIn.newExplosion(player, v.xCoord, v.yCoord, v.zCoord, explosionStr, false, true);

			return true;
		}

		return false;
	}
}