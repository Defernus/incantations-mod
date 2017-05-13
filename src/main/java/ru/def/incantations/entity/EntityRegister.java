package ru.def.incantations.entity;

import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.def.incantations.entity.TileEntityBookMonument;

/**
 * Created by Defernus on 12.05.2017.
 */
public class EntityRegister {
	public static void register(){
		GameRegistry.registerTileEntity(TileEntityBookMonument.class,"tile_book_monument");
	}

}
