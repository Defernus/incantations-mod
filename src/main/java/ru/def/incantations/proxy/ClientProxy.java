package ru.def.incantations.proxy;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.def.incantations.Core;
import ru.def.incantations.blocks.BlocksRegister;
import ru.def.incantations.entity.EntityRegister;
import ru.def.incantations.events.EventRegister;
import ru.def.incantations.items.ItemRune;
import ru.def.incantations.items.ItemsRegister;
import ru.def.incantations.items.renders.RenderIncantationsBook;
import ru.def.incantations.keybinds.KeyBinder;

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

		ModelBakery.registerItemVariants(ItemsRegister.RUNE,
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.BLANK.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.USE.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.RIGHT_CLICK.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.DELAY.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.LOOK_AT.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.EXPLOSION.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.PROJECTILE.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.FIREBALL.getName()),
				new ResourceLocation(Core.MODID, "rune_" + ItemRune.EnumRune.ARROW.getName()));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		EventRegister.regClient();
	}
}
