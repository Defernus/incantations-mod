package ru.def.incantations.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.net.NetworkHandler;
import ru.def.incantations.tileentity.TileEntityRegister;
import ru.def.incantations.events.EventRegister;
import ru.def.incantations.items.ItemsRegister;
import ru.def.incantations.world.generator.GeneratorsRegister;

/**
 * Created by Defernus on 10.05.2017.
 */
public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		/*ClientCommandHandler.INSTANCE.registerCommand(new CommandExplStr());
		ClientCommandHandler.INSTANCE.registerCommand(new CommandSpawnIsland());*/
		ItemsRegister.registerItems();
		BlocksRegister.registerBlocks();
		TileEntityRegister.register();

		NetworkHandler.init();

		GeneratorsRegister.register();
	}

	public void init(FMLInitializationEvent event) {

	}

	public void postInit(FMLPostInitializationEvent event) {
		EventRegister.regCommon();
	}
}
