package ru.def.incantations.world.generator;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Defernus on 18.05.2017.
 */
public class IslandGenerator implements IWorldGenerator {

	private static int level = 200;
	private static final List<Biome> ALLOWED_BIOMES = Arrays.asList(Biomes.OCEAN, Biomes.DEEP_OCEAN);

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.isRemote || !world.getWorldInfo().isMapFeaturesEnabled()) {
			return;
		}

		if(canGenerateInChunk(chunkX, chunkZ, world )) {
			generateNear(random, chunkX*16+8, level, chunkZ*16+8, world);
		}
	}

	private static boolean canGenerateInChunk(int chunkX, int chunkZ, World world ) {

		int i = chunkX;
		int j = chunkZ;

		if (chunkX < 0)
		{
			i = chunkX - 9;
		}

		if (chunkZ < 0)
		{
			j = chunkZ - 9;
		}

		i /= 10;
		j /= 10;
		Random random = new Random((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L + world.getWorldInfo().getSeed() + (long)27644437);
		i *= 10;
		j *= 10;
		i += random.nextInt(7);
		j += random.nextInt(7);

		if(i != chunkX || j != chunkZ) {
			return false;
		}

		if( true){//world.getBiomeProvider().areBiomesViable(chunkX*16+8, chunkZ*16+8, 16, ALLOWED_BIOMES) ) {
			return true;
		}

		return false;
	}

	private static void generateNear(Random random, int x, int y, int z, World world) {

		System.out.println("Generating dungeon at "+x+"_"+y+"_"+z);
		generateIsland(x, y, z, world);

		/*
		for(int i = 1; i < 255; i++) {
			world.setBlockState(new BlockPos(x, i, z), Blocks.GLASS.getDefaultState());
		}

		world.setBlockState(new BlockPos(x, y, z), Blocks.BEACON.getDefaultState());

		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				world.setBlockState(new BlockPos(x+i, y-1, z+j), Blocks.IRON_BLOCK.getDefaultState());
			}
		}
		*/
	}

	public static void generateIsland( int x, int y, int z, World world) {
		Random rnd = new Random((long)x * 341873128712L + (long)z * 132897987541L + world.getWorldInfo().getSeed() + (long)27644437);

		int d = 24+rnd.nextInt(32), h = d;

		int blocks[][] = new int[d][d];
		for(int i = 0; i < d*d; i++) {
			blocks[i/d][i%d] = 0;
		}

		NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(rnd, 3);

		for(int i = -d/2; i < d/2; i++) {
			for(int k = -d/2; k < d/2; k++) {
				int ih = (int)perlin.getValue((x+i)/20f, (z+k)/20f)*7;
				for(int j = -h/2; j < h/2; j++) {
					if( (i*i+k*k)-ih < (d*d/4-9) && (j < 3-h/2 || rnd.nextInt( ((i*i+k*k+j*j)/d)*500+h*h/4 ) < h*h/4) ) {
						blocks[i+d/2][k+d/2]+=1;
					}
				}
			}
		}

		for(int i = -d/2; i < d/2; i++) {
			for(int k = -d/2; k < d/2; k++) {
				int gh = (int)perlin.getValue(i/3,k/3)+4;
				int ih = (int)perlin.getValue((x+i)/15f, (z+k)/15f);
				for(int j = h/2-blocks[i+d/2][k+d/2]; j < h/2; j++) {
					BlockPos pos = new BlockPos(x+i,y+j-h/2-2+ih,z+k);

					IBlockState block;
					if(j == h/2-1) {
						block = Blocks.GRASS.getDefaultState();
					}else if(j < h/2-1 && j > h/2-gh) {
						block = Blocks.DIRT.getDefaultState();
					}else {
						block = Blocks.STONE.getDefaultState();
					}

					world.setBlockState( pos, block );
				}
			}
		}

	}
}
