package ru.def.incantations.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.entity.EntityRegister;
import ru.def.incantations.items.ItemsRegister;

/**
 * Created by Defernus on 10.05.2017.
 */
public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}

	@Override
	public void init(FMLInitializationEvent event) {

		ItemsRegister.registerRenders();
		BlocksRegister.registerRenders();

		EntityRegister.registerClient();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {

	}
}
