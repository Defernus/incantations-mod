package ru.def.incantations.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.def.incantations.Core;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.gui.GuiHandler;
import ru.def.incantations.entity.TileEntityBookMonument;

import javax.annotation.Nullable;

/**
 * Created by Defernus on 12.05.2017.
 */
public class BlockBookMonument extends BlockContainer {

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D);

	public BlockBookMonument(Material materialIn) {
		super(materialIn);

		this.setUnlocalizedName("book_monument");
		this.setRegistryName( "book_monument");

		this.setSoundType(SoundType.STONE);

		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!worldIn.isRemote)
		{
			TileEntity entity = worldIn.getTileEntity(pos);
			if(entity instanceof TileEntityBookMonument)
			{
				playerIn.openGui(Core.instance, GuiHandler.GUI_BOOK_MONUMENT, worldIn, pos.getX(), pos.getY(), pos.getZ());
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

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBookMonument();
	}
}
