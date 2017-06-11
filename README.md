@Override
    public void generate(Random rnd, int chunkX, int chunkZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        //корды центрального чанка острова
        int cchunkX = 0;
        int cchunkZ = 0;

        boolean cg = false;
        for(int k = 0; k < 49; k++) {
            //проходим по квадрату 7x7 в поисках центра острова, если такой находится, мы генерируем чанк.
            int i = k%7-3;
            int j = k/7-3;
            if(canGenerateHere(world, chunkX+i, chunkZ+j)){
                cchunkX = chunkX + i;
                cchunkZ = chunkZ + j;

                cg = true;
                break;
            }

        }

        if(!cg)return;//если центр найден не был прекращаем генерацию

        //берем центр чанка в качестве центра острова
        int x = cchunkX*16+8;
        int z = cchunkZ*16+8;
        int y = 100;

        //здесь нужен свой рандом, зависящий от центра острова
        rnd = new Random((long)x * 341873128712L + (long)z * 132897987541L + world.getWorldInfo().getSeed() + (long)27644437);

        int r = 16+rnd.nextInt(17);//радиус острова от 16 до 32 блоков

        NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(rnd, 2);

        //Теперь мы будем проходить цикл только в пределах нужного чанка
        int iMin = ( chunkX-cchunkX ) * 16-8;
        int iMax = ( chunkX-cchunkX+1 ) * 16-8;
        int jMin = ( chunkZ-cchunkZ ) * 16-8;
        int jMax = ( chunkZ-cchunkZ+1 ) * 16-8;

        for(int i = iMin;  i <= iMax; i++) {
            for(int j = jMin; j <= jMax; j++) {
                double l = Math.sqrt(i*i+j*j);

                int hT = (int)( perlin.getValue((x+i)/40., (z+j)/40.)*2 - (i*i+j*j)/(10.*r) );//верхний шум
                int hB = (int)( perlin.getValue((x+i), (z+j))*( ((r-l)/(double)r)*10>0?((r-l)/r)*10:0 ) );
                //hB = hB<hT-1?hB:hT-2;
                hB += (int)l-r;

                for(int k = hB; k <= hT; k++) {

                    BlockPos pos = new BlockPos(x+i, y+k, z+j);

                    IBlockState block;

                    if(k == hT) {
                        block = Blocks.GRASS.getDefaultState();
                    }else if(k > hT-4) {
                        block = Blocks.DIRT.getDefaultState();
                    }else {
                        block = Blocks.STONE.getDefaultState();
                    }
                    world.setBlockState(pos, block);
                }
            }
        }

    }