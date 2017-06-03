package ru.def.incantations.world.generator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

import javax.annotation.Nullable;

/**
 * Created by Defernus on 18.05.2017.
 */
public class DungeonGenerator extends MapGenStructure {

	@Override
	public String getStructureName() {
		return "AbandonedLibrary";
	}

	@Nullable
	@Override
	public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
		return null;
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return false;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return null;
	}
}
