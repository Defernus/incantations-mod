package ru.def.incantations.incantations;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import ru.def.incantations.entity.TileEntityWritingTable;
import ru.def.incantations.items.ItemRune;

import java.util.Objects;

/**
 * Created by Defernus on 11.05.2017.
 */
public class IncantationHandler {

	public static float explosionStr=4;

	public static boolean onUsed(EntityPlayer player, BlockPos pos, World worldIn, NBTTagCompound tag){

		return false;
	}

	public static boolean onClicked(EntityPlayer player, World worldIn, NBTTagCompound tag) {
		if(((NBTTagCompound)tag.getTag("activator")).getInteger("id") == ItemRune.EnumRune.RIGHT_CLICK.getID()) {
			System.out.println(ItemRune.EnumRune.RIGHT_CLICK.getName());

			NBTTagCompound action_type_tag = (NBTTagCompound)((NBTTagCompound)tag.getTag("activator")).getTag("action_type");

			if(action_type_tag.getInteger("id") == ItemRune.EnumRune.PROJECTILE.getID()) {
				return spawnProjectile(player, worldIn, (NBTTagCompound)action_type_tag.getTag("action"));
			}else if(action_type_tag.getInteger("id") == ItemRune.EnumRune.LOOK_AT.getID()) {
				return spawnLookAt(player, worldIn, (NBTTagCompound)action_type_tag.getTag("action"));
			}
		}

		return false;
	}

	private static boolean spawnLookAt(EntityPlayer player, World worldIn, NBTTagCompound tag){
		if(tag.getInteger("id") == ItemRune.EnumRune.EXPLOSION.getID()) {

			Vec3d pos = player.getPositionEyes(1);
			Vec3d look = player.getLook(1);


			RayTraceResult result = worldIn.rayTraceBlocks(pos, new Vec3d(look.xCoord*50, look.yCoord*50, look.zCoord*50).add(pos), false, true, false);

			System.out.println(look.xCoord+"_"+look.yCoord+"_"+look.zCoord);

			if(result!=null&&!worldIn.isAirBlock(result.getBlockPos())){
				Vec3d v = result.hitVec;
				worldIn.newExplosion(player, v.xCoord, v.yCoord, v.zCoord, explosionStr, false, true);

				return true;
			}
		}

		return false;
	}

	private static boolean spawnProjectile(EntityPlayer player, World worldIn, NBTTagCompound tag) {
		//if(Objects.equals(tag.getString("name"), ItemRune.EnumRune.PROJECTILE.getName())) {

		//}
		if (tag.getInteger("id")==ItemRune.EnumRune.FIREBALL.getID()) {
			double lx = player.getLook(1.0f).xCoord;
			double ly = player.getLook(1.0f).yCoord;
			double lz = player.getLook(1.0f).zCoord;

			double px = player.getPositionEyes(1.0f).xCoord + (ly < 0 ? lx * 2 : lx);
			double py = player.getPositionEyes(1.0f).yCoord + (ly < 0 ? ly * 2 : ly);
			double pz = player.getPositionEyes(1.0f).zCoord + (ly < 0 ? lz * 2 : lz);

			System.out.println("player pos: (" + px + "; " + py + "; " + pz + "; look: (" + lx + "; " + ly + "; " + lz + ")");

			EntityFireball fireball;

			worldIn.spawnEntity(new EntityLargeFireball(worldIn, player, lx*100, ly*100, lz*100));

			return true;
		} else if (tag.getInteger("id")==ItemRune.EnumRune.ARROW.getID()) {

			ItemArrow itemarrow = (ItemArrow) Items.ARROW;
			EntityArrow entityarrow = itemarrow.createArrow(worldIn, new ItemStack(Items.ARROW), player);
			entityarrow.setAim(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
			worldIn.spawnEntity(entityarrow);

			return true;
		}
		return false;
	}

	public static NBTTagCompound createInk(World world, BlockPos WN, BlockPos ES) {
		TileEntityWritingTable te = getTE(world,WN);

		NBTTagCompound tag = new NBTTagCompound();

		int facing = te.facing;

		int w = facing==0||facing==2 ? (1+ES.getX()-WN.getX())*2 : (1+ES.getZ()-WN.getZ())*2;
		int h = facing==0||facing==2 ? (1+ES.getZ()-WN.getZ())*2 : (1+ES.getX()-WN.getX())*2;

		int[][] runes = new int[w][h];

		for(int i=0;i<=ES.getX()-WN.getX();i++) {
			for(int j=0;j<=ES.getZ()-WN.getZ();j++) {
				TileEntityWritingTable te1 = getTE(world,WN.east(i).south(j));

				switch (facing){
					case 1:
						runes[w-1 - j*2  ][i*2  ]=te1.chars[1];
						runes[w-1 - j*2-1][i*2  ]=te1.chars[0];
						runes[w-1 - j*2  ][i*2+1]=te1.chars[3];
						runes[w-1 - j*2-1][i*2+1]=te1.chars[2];
						break;
					case 3:
						runes[j*2  ][h-1 - i*2  ]=te1.chars[2];
						runes[j*2+1][h-1 - i*2  ]=te1.chars[3];
						runes[j*2  ][h-1 - i*2-1]=te1.chars[0];
						runes[j*2+1][h-1 - i*2-1]=te1.chars[1];
						break;
					case 2:
						runes[i*2  ][j*2  ]=te1.chars[0];
						runes[i*2+1][j*2  ]=te1.chars[1];
						runes[i*2  ][j*2+1]=te1.chars[2];
						runes[i*2+1][j*2+1]=te1.chars[3];
						break;
					default:
						runes[w-1 - i*2  ][h-1 - j*2  ]=te1.chars[3];
						runes[w-1 - i*2-1][h-1 - j*2  ]=te1.chars[2];
						runes[w-1 - i*2  ][h-1 - j*2-1]=te1.chars[1];
						runes[w-1 - i*2-1][h-1 - j*2-1]=te1.chars[0];
						break;
				}
			}
		}

		int x,y=0;
		for (x = 0; x < runes.length; x++) {
			for (y = 0; y < runes[x].length; y++) {
				int id = runes[x][y]<ItemRune.EnumRune.values().length?runes[x][y]:0;
				if(Objects.equals(ItemRune.EnumRune.values()[id].getType(), "activator"))break;
			}
			y=y<runes[x].length?y:0;
			int id = runes[x][y]<ItemRune.EnumRune.values().length?runes[x][y]:0;
			if(Objects.equals(ItemRune.EnumRune.values()[id].getType(), "activator"))break;
		}
		x=x<runes.length?x:0;

		if(!Objects.equals(ItemRune.EnumRune.values()[runes[x][y]].getType(), "activator"))return null;

		float power=ItemRune.EnumRune.values()[runes[x][y]].getPower();
		int cd=ItemRune.EnumRune.values()[runes[x][y]].getCD();

		NBTTagCompound activator_tag = new NBTTagCompound();
		activator_tag.setInteger("id", ItemRune.EnumRune.values()[runes[x][y]].getID());

		if(!Objects.equals(ItemRune.EnumRune.values()[runes[x+1][y]].getType(), "action_type"))return null;

		power+=ItemRune.EnumRune.values()[runes[x+1][y]].getPower();
		cd+=ItemRune.EnumRune.values()[runes[x+1][y]].getCD();

		NBTTagCompound action_type_tag = new NBTTagCompound();
		action_type_tag.setInteger( "id", ItemRune.EnumRune.values()[runes[x+1][y]].getID() );

		if(
				!Objects.equals( ItemRune.EnumRune.values()[runes[x+2][y]].getType().substring(0,6), "action" )||
						!Objects.equals(ItemRune.EnumRune.values()[runes[x+2][y]].getType().substring(7,ItemRune.EnumRune.values()[runes[x+2][y]].getType().length()), ItemRune.EnumRune.values()[runes[x+1][y]].getName()))return null;

		power+=ItemRune.EnumRune.values()[runes[x+2][y]].getPower();
		cd+=ItemRune.EnumRune.values()[runes[x+2][y]].getCD();

		NBTTagCompound action_tag = new NBTTagCompound();
		action_tag.setInteger( "id", ItemRune.EnumRune.values()[runes[x+2][y]].getID() );

		action_type_tag.setTag( "action", action_tag );

		activator_tag.setTag( "action_type", action_type_tag );

		tag.setTag("activator", activator_tag);
		tag.setInteger("CD", cd);
		tag.setFloat("power", power);

		int[] runes1 = new int[256];

		for(int i = 0; i < runes1.length; i++) {
			runes1[i] = 0;
		}

		for (int i = x; i < runes.length; i++) {
			for (int j = y; j < runes[i].length; j++) {
				runes1[(i-x)*16+(j-y)] = runes[i][j];
			}
		}

		tag.setIntArray( "runes", runes1 );

		return tag;
	}

	private static TileEntityWritingTable getTE(World world,BlockPos pos) {
		return (TileEntityWritingTable)world.getTileEntity(pos);
	}
}