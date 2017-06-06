package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
import ru.def.incantations.tileentity.TileEntityBookMonument;
import ru.def.incantations.items.ItemIncantationsBook;
import ru.def.incantations.items.ItemsRegister;

import javax.annotation.Nullable;

/**
 * Created by Defernus on 12.05.2017.
 */
public class BlockBookMonument extends Block implements ITileEntityProvider {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);

	public BlockBookMonument(Material materialIn) {
		super(materialIn);

		this.setUnlocalizedName("book_monument");
		this.setRegistryName( "book_monument");

		this.setSoundType(SoundType.STONE);

		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(worldIn.getTileEntity(pos) instanceof TileEntityBookMonument){
			worldIn.spawnEntity(new EntityItem(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,(((TileEntityBookMonument)worldIn.getTileEntity(pos)).stack)));
		}
		worldIn.markTileEntityForRemoval(worldIn.getTileEntity(pos));

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		System.out.println("actived");

		if(!world.isRemote)
		{
			TileEntity entity = world.getTileEntity(pos);
			if(entity instanceof TileEntityBookMonument)
			{
				if(((TileEntityBookMonument)entity).stack.isEmpty() && (player.getHeldItem(hand).getItem() instanceof ItemIncantationsBook)){

					double x = player.getLook(1).xCoord;
					double z = player.getLook(1).zCoord;
					float pitch = (float)(MathHelper.atan2(z,x)/Math.PI*180)-90;//player.getHorizontalFacing().getHorizontalAngle();

					((TileEntityBookMonument)entity).setStack(world,pos,player.getHeldItem(hand),pitch);
					player.setHeldItem(hand,ItemStack.EMPTY);
					return true;

				}else if( !((TileEntityBookMonument)entity).stack.isEmpty() && ((TileEntityBookMonument)entity).stack.getItem() instanceof ItemIncantationsBook && (player.getHeldItem(hand).isEmpty()) ){

					player.setHeldItem(hand,((TileEntityBookMonument)entity).stack);
					((TileEntityBookMonument)entity).setStack(world, pos, ItemStack.EMPTY, player.getHorizontalFacing().getHorizontalAngle());
					return true;

				}else if(!((TileEntityBookMonument)entity).stack.isEmpty() && player.getHeldItem(hand).getItem() == ItemsRegister.WRITTEN_SCROLL){

					NBTTagCompound tag = ((TileEntityBookMonument)entity).stack.getTagCompound();
					int max_inc = tag.getInteger("max_inc");

					NBTTagCompound tag_inc = tag.getCompoundTag("incantations");

					for (int i = 0; i < max_inc; i++) {
						if(!tag_inc.hasKey(""+i)) {
							tag_inc.setTag(""+i,player.getHeldItem(hand).getTagCompound());
							tag.setInteger("cur_inc", i);
							player.setHeldItem(hand, ItemStack.EMPTY);
							return true;
						}
					}

					ItemStack scroll = new ItemStack(ItemsRegister.WRITTEN_SCROLL);
					scroll.setTagCompound(tag_inc.getCompoundTag(""+(max_inc-1)));
					world.spawnEntity(new EntityItem(world,pos.getX()+.5,pos.getY()+0.5f,pos.getZ()+0.5f,scroll));

					for (int i = 1; i < max_inc; i++) {
						tag_inc.setTag(""+i,tag_inc.getTag(""+(i-1)));
					}

					tag_inc.setTag("0",player.getHeldItem(hand).getTagCompound());
					tag.setInteger("cur_inc", 0);
					player.setHeldItem(hand, ItemStack.EMPTY);
				}
			}
		}
		return true;
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

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBookMonument();
	}
}
