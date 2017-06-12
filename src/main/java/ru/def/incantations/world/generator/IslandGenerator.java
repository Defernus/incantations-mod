package ru.def.incantations.world.generator;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.common.IWorldGenerator;
import ru.def.incantations.Core;
import ru.def.incantations.blocks.BlocksRegister;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Defernus on 18.05.2017.
 */
public class IslandGenerator implements IWorldGenerator {

	private static int level = 200;
	private static final List<Biome> ALLOWED_BIOMES = Arrays.asList(Biomes.OCEAN, Biomes.DEEP_OCEAN);

	private static IBlockState
			STONE = Blocks.STONE.getDefaultState(),
			GRASS = Blocks.GRASS.getDefaultState(),
			DIRT = Blocks.DIRT.getDefaultState(),
			ORE = BlocksRegister.SKY_IRON_ORE.getDefaultState();

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

		if( i == chunkX && j == chunkZ){//&& world.getBiomeProvider().areBiomesViable(chunkX*16+8, chunkZ*16+8, 16, ALLOWED_BIOMES) ) {
			return true;
		}

		return false;
	}

	private static void generateNear(int chunkX, int chunkZ, int scX, int scZ, int y, World world) {
		Random rnd = new Random((long)chunkX * 341873128712L + (long)chunkZ * 132897987541L + world.getWorldInfo().getSeed() + (long)27644437);

		int d = 24+rnd.nextInt(86);

		System.out.println("Generating island at "+(chunkX+scX)+"_"+(chunkZ+scZ) + "("+chunkX+"_"+chunkZ+")");
		System.out.println("d = " + d);

		generateIsland(d, rnd, chunkX, chunkZ, scX, scZ, y, world);

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

	private static void generateIsland(int d, Random rnd, int chunkX, int chunkZ, int scX, int scZ, int y, World world) {
		int x = chunkX*16+8;
		int z = chunkZ*16+8;

		NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(rnd, 2);

		int iMax, iMin, kMax, kMin;

		iMin = scX*16-8;
		iMax = (scX+1)*16-8;
		kMin = scZ*16-8;
		kMax = (scZ+1)*16-8;

		int c = 0;

		for(int i = iMin; i < iMax; i++) {
			for(int k = kMin; k < kMax; k++) {
				int gh = (int)perlin.getValue(i/3.,k/3.)+4;
				double tp = perlin.getValue((x+i)/35., (z+k)/35.);
				int tH = (int)( tp+ d *(1.-(i*i+k*k)/(d*d/4.))/10. );
				double l = Math.sqrt(i*i+k*k);

				double gr = (d/2-l);
				double grp = gr<0?0:gr;

				double st = perlin.getValue((x+i)+100., (z+k)+100.)*grp/10.;

				double dHPre = perlin.getValue((x+i)/50., (z+k)/50.)*(gr/5.+7) - gr - 4;

				int bH = (int)( st*(dHPre/ d)*10 + dHPre);

				if(i*i+k*k > (d/2)*(d/2)+tH && i*i+k*k < bH) {
					continue;
				}

				for(int j = bH; j < tH; j++) {
					BlockPos pos = new BlockPos(x+i,y+j,z+k);

					IBlockState block;
					if(j == tH-1) {
						block = GRASS;
					}else if(j > tH-gh) {
						block = DIRT;
					}else {
						if(rnd.nextInt( i*i+j*j+k*k+1 ) == 0) {
							block = ORE;
						}else {
							block = STONE;
						}
					}

					world.setBlockState( pos, block );
					c++;
				}
			}
		}
		System.out.println("Generated "+c+" blocks");

		if(scX == 0 && scZ == 0) {
			generateStructure(d, rnd, chunkX, chunkZ, scX, scZ, y+(int)( perlin.getValue(x/35., z/35.) + d * .1 ), world);
		}

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

	private static void generateStructure(int d, Random rnd, int chunkX, int chunkZ, int scX, int scZ, int y, World world) {
		System.out.println("Generating structure at ("+chunkX+"_"+chunkZ+") "+y);
		int x = chunkX*16+8;
		int z = chunkZ*16+8;

		WorldServer worldserver = (WorldServer)world;
		MinecraftServer minecraftserver = world.getMinecraftServer();
		TemplateManager templatemanager = worldserver.getStructureTemplateManager();
		String type = "base";

		if( d > 40 ) {
			type = "ancient";
		}
		if( d > 60 ) {
			type = "legendary";
		}
		if( d > 90 ) {
			type = "mythical";
		}

		Template template = templatemanager.get( minecraftserver, new ResourceLocation(Core.MODID, "sky_island_"+type+"_0") );

		if(template == null)
		{
			System.out.println("NO STRUCTURE");
			return;
		}

		BlockPos size = template.getSize();
		BlockPos pos = new BlockPos(x-size.getX()/2, y-1, z-size.getZ()/2);

		IBlockState iblockstate = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
													  .setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null)
													  .setReplacedBlock(null).setIgnoreStructureBlock(false);

		template.addBlocksToWorldChunk(world, pos, placementsettings);
		/*
		int r = d < 32? 4 : d < 48? 7 : d < 64? 10 : 15;

		IBlockState QUARTZ_BLOCK = Blocks.QUARTZ_BLOCK.getDefaultState();
		IBlockState QUARTZ_PILLAR = Blocks.QUARTZ_BLOCK.getDefaultState();
		IBlockState QUARTZ_BOOKSHELF = BlocksRegister.QUARTZ_BOOKSHELF_ANCIENT.getDefaultState();

		int iMin = scX*16-8;
		int iMax = (scX+1)*16-8;
		int kMin = scZ*16-8;
		int kMax = (scZ+1)*16-8;

		int c = 0;

		for(int i = iMin; i < iMax; i++) {
			for(int k = kMin; k < kMax; k++) {
				if( i*i+k*k < r*r ) {
					world.setBlockState(new BlockPos(i+x, y, k+z), QUARTZ_BLOCK);
				}
			}
		}*/
	}
}
