package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.entity.TileEntityBookMonument;
import ru.def.incantations.items.ItemIncantationsBook;

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
			worldIn.spawnEntity(new EntityItem(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,(((TileEntityBookMonument)worldIn.getTileEntity(pos)).inv.getStackInSlot(0))));
		}
		worldIn.markTileEntityForRemoval(worldIn.getTileEntity(pos));

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote)
		{
			TileEntity entity = worldIn.getTileEntity(pos);
			if(entity instanceof TileEntityBookMonument)
			{
				if(((TileEntityBookMonument)entity).inv.getStackInSlot(0).isEmpty() && (playerIn.getHeldItem(hand).getItem() instanceof ItemIncantationsBook)){
					((TileEntityBookMonument)entity).setStack(worldIn,pos,playerIn.getHeldItem(hand),playerIn.getPitchYaw().y);
					playerIn.setHeldItem(hand,ItemStack.EMPTY);
				}else if( !((TileEntityBookMonument)entity).inv.getStackInSlot(0).isEmpty() && ((TileEntityBookMonument)entity).inv.getStackInSlot(0).getItem() instanceof ItemIncantationsBook && (playerIn.getHeldItem(hand).isEmpty()) ){
					playerIn.setHeldItem(hand,((TileEntityBookMonument)entity).inv.getStackInSlot(0));
					((TileEntityBookMonument)entity).setStack(worldIn,pos,ItemStack.EMPTY,playerIn.getPitchYaw().y);
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
