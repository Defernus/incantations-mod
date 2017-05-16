package ru.def.incantations.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.entity.TileEntityWritingTable;

import javax.annotation.Nullable;

/**
 * Created by Defernus on 14.05.2017.
 */
public class BlockWritingTable extends Block implements ITileEntityProvider{

	protected static final AxisAlignedBB AABB = new AxisAlignedBB(0D, 0.0D, 0D, 1D, 0.9375D, 1D);

	public BlockWritingTable(Material materialIn) {
		super(materialIn);

		this.setUnlocalizedName("writing_table");
		this.setRegistryName( "writing_table");

		this.setSoundType(SoundType.STONE);

		this.setCreativeTab(CreativeTabsHandler.MY_TAB);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(world.isRemote){
			Minecraft.getMinecraft().player.sendChatMessage("breaking table");

			return;
		}

		TileEntityWritingTable te = ((TileEntityWritingTable)world.getTileEntity(pos));

		BlockPos posWN=te.posWN;
		BlockPos posES=te.posES;

		if(te.hasPaper){

			for(int i=posWN.getX();i<=posES.getX();i++){
				for(int j=posWN.getZ();j<=posES.getZ();j++){
					System.out.println("break at: "+posWN+"; i="+i+"; j="+j);
					((TileEntityWritingTable)world.getTileEntity(new BlockPos(i,pos.getY(),j))).setNoPaper();
					world.spawnEntity(new EntityItem(world,i+0.5f,pos.getY()+0.5f,j+0.5f,new ItemStack(Items.PAPER)));
				}
			}
		}
		world.markTileEntityForRemoval(world.getTileEntity(pos));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(!world.isRemote)
		{

			TileEntityWritingTable te=(TileEntityWritingTable)world.getTileEntity(pos);

			if(!te.hasPaper && player.getHeldItem(hand).getItem()==Items.PAPER){
				player.sendMessage(new TextComponentString("[structure] trying"));

				BlockPos wnPos=findWNTable(world,pos);

				if(wnPos==null)return true;

				int mi=8,mj=8;

				for(int i=0;i<mi;i++){
					for(int j=0;j<mj;j++){
						if(world.getBlockState(wnPos.east(j).south(i)).getBlock()!=BlocksRegister.WRITING_TABLE){
							if(j<2){
								mi=i;
								break;
							}
							mj=j;
							break;
						}
					}
				}

				mi--;
				mj--;



				if(mi<1||mj<1) return true;

				BlockPos esPos=wnPos.east(mj).south(mi);

				if(pos.getX()<wnPos.getX()||pos.getZ()<wnPos.getZ()||pos.getX()>esPos.getX()||pos.getZ()>esPos.getZ())return true;

				if(player.getHeldItem(hand).getCount()<(mi+1)*(mj+1))return true;

				player.getHeldItem(hand).setCount(player.getHeldItem(hand).getCount()-(mi+1)*(mj+1));

				player.sendMessage(new TextComponentString("[structure] size: ("+(mi+1)+"; "+(mj+1)+")"));
				player.sendMessage(new TextComponentString("[structure] cords: ( ("+wnPos.getX()+"; "+wnPos.getY()+"; "+wnPos.getZ()+"); ("+esPos.getX()+"; "+esPos.getY()+"; "+esPos.getZ()+") )"));

				for(int i=0;i<=mi;i++){
					for(int j=0;j<=mj;j++){
						//player.sendMessage(new TextComponentString("[structure] set paper at table: ("+(i)+"; "+(j)+")"));
						((TileEntityWritingTable)world.getTileEntity(wnPos.south(i).east(j))).setPaper(wnPos,esPos);
					}
				}
			}


				/*if(playerIn.getHeldItem(hand).getItem()== Items.PAPER&&!((TileEntityWritingTable)entity).hasPaper){
					((TileEntityWritingTable)entity).togglePaper(worldIn,pos);
					playerIn.setHeldItem(hand,ItemStack.EMPTY);
				}else if(((TileEntityWritingTable)entity).hasPaper){
					((TileEntityWritingTable)entity).togglePaper(worldIn,pos);
					worldIn.spawnEntity(new EntityItem(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,new ItemStack(Items.PAPER)));
				}*/

		}
		return true;
	}

	private BlockPos findWNTable(World world,BlockPos pos){
		int x=8;
		int y=8;


		while(true){
			if( ((TileEntityWritingTable)world.getTileEntity(pos)).hasPaper )return null;

			if(world.getBlockState(pos.north()).getBlock()==BlocksRegister.WRITING_TABLE&&y>0){
				y--;
				pos=pos.north();
				continue;
			}else y=0;
			if(world.getBlockState(pos.west()).getBlock()==BlocksRegister.WRITING_TABLE&&x>0){
				x--;
				pos=pos.west();
				continue;
			}
			break;
		}

		return pos;
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
		return new TileEntityWritingTable();
	}
}
