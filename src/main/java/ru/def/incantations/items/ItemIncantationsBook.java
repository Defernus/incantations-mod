package ru.def.incantations.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.incantations.IncantationHandler;

/**
 * Created by Defernus on 10.05.2017.
 */
public class ItemIncantationsBook extends Item {

	private final int INCANTATIONS_AMOUNT=2;
	private final int BOOK_CD=40;
	private final float POWER=10;
	private final boolean isTabIcon;

	public ItemIncantationsBook(boolean isTabIcon,String name) {

		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setMaxStackSize(1);
		if(!isTabIcon)this.setCreativeTab(CreativeTabsHandler.MY_TAB);
		this.isTabIcon=isTabIcon;
	}

	private void setNBT(ItemStack stack) {
		stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setInteger("max_inc",INCANTATIONS_AMOUNT);
		stack.getTagCompound().setFloat("max_pow",POWER);
		stack.getTagCompound().setFloat("cur_pow",POWER);
		stack.getTagCompound().setInteger("selected_inc",-1);
		stack.getTagCompound().setInteger("cur_cd",BOOK_CD);
		stack.getTagCompound().setInteger("max_cd",BOOK_CD);

		NBTBase value=new NBTTagCompound();

		stack.getTagCompound().setTag("incantations",value);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if(!isTabIcon)setNBT(stack);

		super.onCreated(stack, worldIn, playerIn);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		if(stack.getTagCompound()==null){
			setNBT(stack);
		}
		return !isTabIcon;
	}

	private float getDurability(NBTTagCompound tag){
		return (int)(tag.getFloat("cur_pow")/tag.getFloat("max_pow")*POWER)/POWER;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		if(stack.getTagCompound()==null){
			setNBT(stack);
		}
		return 1-getDurability(stack.getTagCompound());
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public int getRGBDurabilityForDisplay(ItemStack stack) {
		if(stack.getTagCompound()==null){
			setNBT(stack);
		}
		float dur=getDurability(stack.getTagCompound());
		return MathHelper.hsvToRGB(0.5F, 1-dur/2, dur);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

		if(!isTabIcon)super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

		if(stack.getTagCompound()==null){
			setNBT(stack);
		}
		if(stack.getTagCompound().getFloat("cur_pow")<=0){
			entityIn.replaceItemInInventory(itemSlot,ItemStack.EMPTY);
			return;
		}

		if(stack.getTagCompound().getInteger("cur_cd")>0){
			stack.getTagCompound().setInteger("cur_cd",stack.getTagCompound().getInteger("cur_cd")-1);
		}else if(stack.getTagCompound().getFloat("cur_pow")<stack.getTagCompound().getFloat("max_pow")){
			stack.getTagCompound().setFloat("cur_pow",stack.getTagCompound().getFloat("cur_pow")+0.05f);
		}

		if(stack.getTagCompound().getFloat("cur_pow")>stack.getTagCompound().getFloat("max_pow")){
			stack.getTagCompound().setFloat("cur_pow",stack.getTagCompound().getFloat("max_pow"));
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(worldIn.isRemote)return super.onItemRightClick(worldIn, playerIn, handIn);
		ItemStack stack=playerIn.getHeldItem(handIn);
		if(stack.getTagCompound()==null){
			setNBT(stack);
		}


		NBTTagCompound tag=stack.getTagCompound();
		if(tag.getInteger("cur_cd")<=0){
			NBTTagCompound incs_tag=(NBTTagCompound)tag.getTag("incantations");
			if(tag.getInteger("cur_inc")!=-1){


				if(IncantationHandler.onClicked(playerIn,worldIn,(NBTTagCompound)incs_tag.getTag(""+tag.getInteger("cur_inc")))){
					tag.setInteger("cur_cd",tag.getInteger("max_cd"));
					tag.setFloat("cur_pow",tag.getFloat("cur_pow")-5);
				}
			}
		}




		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.isRemote)super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		ItemStack stack=player.getHeldItem(hand);
		if(stack.getTagCompound()==null){
			setNBT(stack);
		}

		NBTTagCompound tag=stack.getTagCompound();
		if(tag.getInteger("cur_cd")<=0){
			NBTTagCompound incs_tag=(NBTTagCompound)tag.getTag("incantations");
			if(incs_tag.getSize()>0){

				if(IncantationHandler.onUsed(player,pos,worldIn,(NBTTagCompound)incs_tag.getTag(""+tag.getInteger("cur_inc"))))tag.setInteger("cur_cd",tag.getInteger("max_cd"));
			}
		}

		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
