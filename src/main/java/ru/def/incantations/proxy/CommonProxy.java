package ru.def.incantations.proxy;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.def.incantations.commands.CommandExplStr;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.commands.CommandSpawnIsland;
import ru.def.incantations.entity.EntityRegister;
import ru.def.incantations.items.ItemsRegister;
import ru.def.incantations.net.PacketHandler;
import ru.def.incantations.world.generator.GeneratorsRegister;

/**
 * Created by Defernus on 10.05.2017.
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new CommandExplStr());
		ClientCommandHandler.instance.registerCommand(new CommandSpawnIsland());
		ItemsRegister.registerItems();
		BlocksRegister.registerBlocks();
		EntityRegister.register();

		PacketHandler.registerMessages();

		GeneratorsRegister.register();
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {

	}
}
