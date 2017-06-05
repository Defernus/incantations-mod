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

		for(int i = -3; i <= 3; i++) {
			for(int j = -3;j  <= 3; j++) {
				if(canGenerateInChunk(chunkX+i, chunkZ+j, world )){
					generateNear(chunkX+i,chunkZ+j, -i, -j,level,world);
					return;
				}
			}
		}
	}

	private static boolean canGenerateInChunk(int chunkX, int chunkZ, World world ) {

		int i = chunkX;
		int j = chunkZ;

		if (chunkX < 0) {
			i = chunkX - 19;
		}

		if (chunkZ < 0) {
			j = chunkZ - 19;
		}

		i /= 20;
		j /= 20;
		i *= 20;
		j *= 20;
		Random random = new Random((long)i * 341873128712L + (long)j * 132897987541L + world.getWorldInfo().getSeed() + (long)27644437);
		i += random.nextInt(12);
		j += random.nextInt(12);

		if( i == chunkX && j == chunkZ ){//&& world.getBiomeProvider().areBiomesViable(chunkX*16+8, chunkZ*16+8, 16, ALLOWED_BIOMES) ) {
			return true;
		}

		return false;
	}

	private static void generateNear(int chunkX, int chunkZ, int scX, int scZ, int y, World world) {

		System.out.println("Generating island at "+chunkX+"_"+chunkZ);
		generateIsland(chunkX, chunkZ, scX, scZ, y, world);

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

	public static void generateIsland(int chunkX, int chunkZ, int scX, int scZ, int y, World world) {
		int x = chunkX*16+8;
		int z = chunkZ*16+8;

		Random rnd = new Random((long)x * 341873128712L + (long)z * 132897987541L + world.getWorldInfo().getSeed() + (long)27644437);

		int d = 24+rnd.nextInt(86);
		int h = d;

		NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(rnd, 2);

		int imax, imin, kmax, kmin;

		imin = scX*16-8;
		imax = (scX+1)*16-8;
		kmin = scZ*16-8;
		kmax = (scZ+1)*16-8;

		int c = 0;

		for(int i = imin; i < imax; i++) {
			for(int k = kmin; k < kmax; k++) {
				int gh = (int)perlin.getValue(i/3.,k/3.)+4;
				double tp = perlin.getValue((x+i)/35., (z+k)/35.);
				int tH = (int)( tp+h*(1.-(i*i+k*k)/(d*d/4.))/10. );
				int bH = (int)( perlin.getValue((x+i)+100., (z+k)+100.) + perlin.getValue((x+i)/50., (z+k)/50.)*10 - ((d/2)-Math.sqrt(i*i+k*k)) - 4);//(d*d)/(i*i+k*k+d) );

				//System.out.println("dH at "+i+"_"+k+" = "+((d/2+2)*(d/2+2)-((i+2)*(i+2)+(k+2)*(k+2)))/h);
				if(i*i+k*k > (d/2)*(d/2)+tH && i*i+k*k < bH) {
					continue;
				}
				for(int j = bH; j < tH; j++) {



					BlockPos pos = new BlockPos(x+i,y+j,z+k);

					IBlockState block;
					if(j == tH-1) {
						block = Blocks.GRASS.getDefaultState();
					}else if(j > tH-gh) {
						block = Blocks.DIRT.getDefaultState();
					}else {
						block = Blocks.STONE.getDefaultState();
					}

					world.setBlockState( pos, block );
					c++;
				}
			}
		}
		System.out.println("Generated "+c+" blocks");

		//old gen
		/*

		int blocks[][] = new int[d][d];
		for(int i = 0; i < d*d; i++) {
			blocks[i/d][i%d] = 0;
		}

		NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(rnd, 2);

		for(int i = -d/2; i < d/2; i++) {
			for(int k = -d/2; k < d/2; k++) {
				int ih = (int)perlin.getValue((x+i)/50f, (z+k)/50f)*10;
				for(int j = -h/2; j < h/2; j++) {
					if( (i*i+k*k)-ih < (d*d/4-9) && (j < 3-h/2 || rnd.nextInt( ((i*i+k*k+j*j)/d)*500+h*h/4 ) < h*h/4) ) {
						blocks[i+d/2][k+d/2]+=1;
					}
				}
			}
		}

		for(int i = -d/2; i < d/2; i++) {
			for(int k = -d/2; k < d/2; k++) {
				int gh = (int)perlin.getValue(i/7,k/7)+4;
				int ih = (int)perlin.getValue((x+i)/35f, (z+k)/35f);
				int l = (int)( h*(1-(i*i+k*k)/(float)(d*d/4))/10 );
				for(int j = h/2-blocks[i+d/2][k+d/2]; j < h/2+l; j++) {
					BlockPos pos = new BlockPos(x+i,y+j-h/2-2+ih,z+k);

					IBlockState block;
					if(j == h/2-1+l) {
						block = Blocks.GRASS.getDefaultState();
					}else if(j > h/2-gh+l) {
						block = Blocks.DIRT.getDefaultState();
					}else {
						block = Blocks.STONE.getDefaultState();
					}

					world.setBlockState( pos, block );
				}
			}
		}
		 */

	}
}
