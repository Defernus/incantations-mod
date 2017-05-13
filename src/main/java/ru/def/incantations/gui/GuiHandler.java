package ru.def.incantations.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.def.incantations.containers.ContainerBookMonument;
import ru.def.incantations.entity.TileEntityBookMonument;

import javax.annotation.Nullable;

/**
 * Created by Defernus on 12.05.2017.
 */
public class GuiHandler implements IGuiHandler {

	public static final int GUI_BOOK_MONUMENT=0;

	@Nullable
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID)
		{
			case GUI_BOOK_MONUMENT: return new ContainerBookMonument(player.inventory, (TileEntityBookMonument) world.getTileEntity(new BlockPos(x, y, z)));
			default: return null;
		}
	}

	@Nullable
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch (ID)
		{
			case GUI_BOOK_MONUMENT: return new GuiBookMonument(new ContainerBookMonument(player.inventory, (TileEntityBookMonument) world.getTileEntity(new BlockPos(x, y, z))));
			default: return null;
		}
	}
}
