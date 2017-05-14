package ru.def.incantations.proxy;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.def.incantations.Commands.CommandExplStr;
import ru.def.incantations.Core;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.entity.EntityRegister;
import ru.def.incantations.entity.TileEntityBookMonument;
import ru.def.incantations.gui.GuiHandler;
import ru.def.incantations.items.ItemsRegister;

/**
 * Created by Defernus on 10.05.2017.
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new CommandExplStr());
		ItemsRegister.registerItems();
		BlocksRegister.registerBlocks();
		EntityRegister.register();
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}
}
