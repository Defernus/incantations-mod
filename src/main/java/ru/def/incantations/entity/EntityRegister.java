package ru.def.incantations.entity;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.def.incantations.entity.render.RenderBookMonument;

/**
 * Created by Defernus on 12.05.2017.
 */
public class EntityRegister {

	public static void register(){
		GameRegistry.registerTileEntity(TileEntityBookMonument.class,"tile_book_monument");
	}

	public static void registerClient(){
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBookMonument.class, new RenderBookMonument());
	}
}
