package ru.def.incantations;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.def.incantations.proxy.CommonProxy;

/**
 * Created by Defernus on 10.05.2017.
 */

@Mod(modid = Core.MODID, version = Core.VERSION, name = Core.NAME)
public class Core
{
	public static final String MODID = "incantations";
	public static final String NAME = "Incantations mod";
	public static final String VERSION = "1.0";

	@SidedProxy(clientSide = "ru.def.incantations.proxy.ClientProxy", serverSide = "ru.def.incantations.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance
	public static Core instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CreativeTabsHandler.tabRegister();
		proxy.preInit(event);
	}
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.init(event);
	}
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
	}
}
