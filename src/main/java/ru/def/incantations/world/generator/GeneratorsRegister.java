package ru.def.incantations.world.generator;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Defernus on 18.05.2017.
 */
public class GeneratorsRegister {

	public static IslandGenerator DUNGEON = new IslandGenerator();

	public static void register() {
		GameRegistry.registerWorldGenerator(DUNGEON, 0);
	}
}
