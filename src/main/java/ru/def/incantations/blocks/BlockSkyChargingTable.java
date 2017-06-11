package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.items.IChargeable;
import ru.def.incantations.items.ItemSkyChargingWand;
import ru.def.incantations.items.ItemSkyIronIngot;
import ru.def.incantations.items.ItemsRegister;
import ru.def.incantations.net.NetworkHandler;
import ru.def.incantations.net.PacketParticlesMP;
import ru.def.incantations.tileentity.TileEntitySkyChargingTable;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Defernus on 06.06.2017.
 */
public class BlockSkyChargingTable extends Block implements ITileEntityProvider {

	private static final AxisAlignedBB AABB = new AxisAlignedBB(2/16., 0, 2/16., 14/16., 0.938, 14/16.);

	public BlockSkyChargingTable() {
		super(Material.ROCK);

		this.setRegistryName("sky_charging_table");
		this.setUnlocalizedName("sky_charging_table");
		this.setLightLevel(.4F);
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

		this.setHarvestLevel("pickaxe", 3);
		this.setResistance(20F);
		this.setHardness(3F);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(worldIn.getTileEntity(pos) instanceof TileEntitySkyChargingTable){
			worldIn.spawnEntity(new EntityItem(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,(((TileEntitySkyChargingTable)worldIn.getTileEntity(pos)).stack)));
		}
		worldIn.markTileEntityForRemoval(worldIn.getTileEntity(pos));

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote)
		{
			if(world.getTileEntity(pos) instanceof TileEntitySkyChargingTable)
			{
				TileEntitySkyChargingTable te = (TileEntitySkyChargingTable)world.getTileEntity(pos);

				if( te.stack.isEmpty() ) {
					if( player.getHeldItem(hand).getItem() instanceof IChargeable) {
						ItemStack stack = player.getHeldItem(hand);
						double x = player.getLook(1).xCoord;
						double z = player.getLook(1).zCoord;
						float yaw = (float)(MathHelper.atan2(z,x)/Math.PI*180)-90;//player.getHorizontalFacing().getHorizontalAngle();

						te.setStack(stack.splitStack(1), yaw);
						return false;

					}
				}else {
					if(player.getHeldItem(hand).getItem() instanceof ItemSkyChargingWand) {
						boolean ch = te.charge(player);
						if(ch) {
							NetworkHandler.sendToServer(new PacketParticlesMP(pos.getX(), pos.getY(), pos.getZ()));
							return false;
						}
					}

					if(player.inventory.addItemStackToInventory(te.stack)) {
						double x = player.getLook(1).xCoord;
						double z = player.getLook(1).zCoord;
						float yaw = (float)(MathHelper.atan2(z,x)/Math.PI*180)-90;//player.getHorizontalFacing().getHorizontalAngle();

						te.setStack(ItemStack.EMPTY, yaw);
					}
					return true;

				}
			}
		}
		return true;
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(ItemsRegister.SKY_CHARGING_TABLE, 1, this.damageDropped(state));
	}

	@Override
	public Item getItemDropped(IBlockState p_getItemDropped_1_, Random p_getItemDropped_2_, int p_getItemDropped_3_) {
		return ItemsRegister.SKY_CHARGING_TABLE;
	}


	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntitySkyChargingTable();
	}
}
