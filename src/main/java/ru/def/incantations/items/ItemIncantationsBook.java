package ru.def.incantations.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import ru.def.incantations.CreativeTabsHandler;
import ru.def.incantations.incantations.IncantationHandler;
import ru.def.incantations.net.NetworkHandler;
import ru.def.incantations.net.PacketParticlesMP;

import javax.annotation.Nullable;

/**
 * Created by Defernus on 10.05.2017.
 */
public class ItemIncantationsBook extends Item {

	public enum EnumBooks{
		BASIC("basic_book", 0, 10, 40, 2, EnumRarity.COMMON),
		ANCIENT("ancient_book", 1, 20, 30, 3, EnumRarity.UNCOMMON),
		LEGENDARY("legendary_book", 2, 40, 20, 3, EnumRarity.RARE),
		MYTHICAL("mythical_book", 3, 80, 10, 4, EnumRarity.EPIC);

		private final String NAME;
		private final float POWER;
		private final int ID, CD, AMOUNT;
		private final EnumRarity RARITY;

		EnumBooks(String name, int id, float power, int cd, int amount, EnumRarity rarity){
			NAME=name;
			ID=id;
			POWER=power;
			CD=cd;
			AMOUNT=amount;
			RARITY=rarity;
		}

		public String getName(){
			return NAME;
		}

		public int getID(){
			return ID;
		}

		public int getAmount(){
			return AMOUNT;
		}

		public int getCD(){
			return CD;
		}

		public float getPower(){
			return POWER;
		}

		public EnumRarity getRarity(){
			return RARITY;
		}
	}

	private final int INCANTATIONS_AMOUNT;
	private final int BOOK_CD;
	private final float POWER;
	private final EnumRarity RARITY;

	public ItemIncantationsBook(int tier) {

		this.setUnlocalizedName(EnumBooks.values()[tier].getName());
		this.setRegistryName(EnumBooks.values()[tier].getName());
		this.setMaxStackSize(1);
		this.setCreativeTab(CreativeTabsHandler.MY_TAB);

		this.POWER = EnumBooks.values()[tier].getPower();
		this.BOOK_CD = EnumBooks.values()[tier].getCD();
		this.INCANTATIONS_AMOUNT = EnumBooks.values()[tier].getAmount();
		this.RARITY = EnumBooks.values()[tier].getRarity();
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return RARITY;
	}

	private void setNBT(ItemStack stack) {
		stack.setTagCompound(new NBTTagCompound());

		stack.getTagCompound().setInteger("max_inc",INCANTATIONS_AMOUNT);
		stack.getTagCompound().setFloat("max_pow",POWER);
		stack.getTagCompound().setFloat("cur_pow",POWER);
		stack.getTagCompound().setInteger("cur_cd",BOOK_CD);
		stack.getTagCompound().setInteger("max_cd",BOOK_CD);
		stack.getTagCompound().setInteger("cur_inc",-1);

		NBTBase value=new NBTTagCompound();

		stack.getTagCompound().setTag("incantations",value);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		setNBT(stack);

		super.onCreated(stack, worldIn, playerIn);
	}

	@Override
	public boolean isFull3D() {
		return true;
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

		if(stack.getTagCompound()==null){
			setNBT(stack);
		}

		if(stack.getTagCompound().getInteger("cur_cd")>0){
			stack.getTagCompound().setInteger("cur_cd",stack.getTagCompound().getInteger("cur_cd")-1);
		}else if(stack.getTagCompound().getFloat("cur_pow")<stack.getTagCompound().getFloat("max_pow")){
			stack.getTagCompound().setFloat("cur_pow",stack.getTagCompound().getFloat("cur_pow")+0.05f);
		}

		if(stack.getTagCompound().getFloat("cur_pow")>stack.getTagCompound().getFloat("max_pow")){
			stack.getTagCompound().setFloat("cur_pow",stack.getTagCompound().getFloat("max_pow"));
		}
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

				float power = ((NBTTagCompound)incs_tag.getTag(""+tag.getInteger("cur_inc"))).getFloat("power");
				if((tag.getFloat("cur_pow")-power>0 || RARITY == EnumRarity.COMMON) && IncantationHandler.onClicked(playerIn,worldIn,(NBTTagCompound)incs_tag.getTag(""+tag.getInteger("cur_inc")))){
					tag.setInteger("cur_cd", (tag.getInteger("max_cd") + ((NBTTagCompound)incs_tag.getTag(""+tag.getInteger("cur_inc"))).getInteger("cd") ));
					tag.setFloat("cur_pow",tag.getFloat("cur_pow") - power );
					if(tag.getFloat("cur_pow")<0){
						playerIn.setHeldItem(handIn,ItemStack.EMPTY);
					}
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
