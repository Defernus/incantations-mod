package ru.def.incantations.tileentity;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.tileentity.render.RenderBookMonument;
import ru.def.incantations.tileentity.render.RenderSkyChargingTable;
import ru.def.incantations.tileentity.render.RenderWritingTable;

/**
 * Created by Defernus on 12.05.2017.
 */
public class TileEntityRegister {

	public static void register(){
		GameRegistry.registerTileEntity(TileEntityBookMonument.class,"tile_book_monument");
		GameRegistry.registerTileEntity(TileEntityWritingTable.class,"tile_writing_table");
		GameRegistry.registerTileEntity(TileEntitySkyChargingTable.class, "tile_sky_charging_table");
	}

	@SideOnly(Side.CLIENT)
	public static void registerClient(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBookMonument.class, new RenderBookMonument());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWritingTable.class, new RenderWritingTable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySkyChargingTable.class, new RenderSkyChargingTable());
	}
}
