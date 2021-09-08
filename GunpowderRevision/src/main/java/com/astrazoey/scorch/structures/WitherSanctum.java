package com.astrazoey.scorch.structures;

import com.astrazoey.scorch.GunpowderRevision;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.structure.MarginedStructureStart;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class WitherSanctum extends StructureFeature<DefaultFeatureConfig> {

   static int validY;

    public WitherSanctum(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public StructureStartFactory<DefaultFeatureConfig> getStructureStartFactory() {
        return WitherSanctum.Start::new;
    }

    private static final Pool<SpawnSettings.SpawnEntry> STRUCTURE_MONSTERS = Pool.of(
            new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 100, 3, 7)
    );
    @Override
    public Pool<SpawnSettings.SpawnEntry> getMonsterSpawns() {
        return STRUCTURE_MONSTERS;
    }


    @Override
    protected boolean shouldStartAt(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, ChunkRandom chunkRandom, ChunkPos chunkPos, Biome biome, ChunkPos chunkPos2, DefaultFeatureConfig featureConfig, HeightLimitView heightLimitView) {

        int structureSize = 12;
        int lowestY = 40;
        int highestY = 110;
        int minimumSolidBlocksBeneath = 50;
        BlockPos centerOfChunk = new BlockPos(chunkPos.x * 16, lowestY+1, chunkPos.z * 16);
        boolean blockIsAir;
        int solidBlocksBeneath = 0;


        //Check for valid Y level
        for(int y = lowestY; y <= highestY; y++) {
            blockIsAir = true;
            centerOfChunk = centerOfChunk.add(0,1,0);

            //make sure all the blocks on this Y level in a 10x10 area is air.
            for (int x = 1; x <= structureSize; x++) {
                centerOfChunk = centerOfChunk.add(1,0,0);
                for (int z = 1; z <= structureSize; z++) {
                    centerOfChunk = centerOfChunk.add(0,0,1);
                    VerticalBlockSample verticalBlockSample = chunkGenerator.getColumnSample(centerOfChunk.getX(), centerOfChunk.getZ(), heightLimitView);
                    BlockState blockState = verticalBlockSample.getState(centerOfChunk);
                    if(!blockState.isAir()) {
                        blockIsAir = false;
                        break;
                    }
                }
                if(!blockIsAir) {
                    break;
                }
            }


            //if the Y level is pure air, move on to the next check
            if(blockIsAir) {

                //Counts the number of solid blocks under the Y level to make sure there's enough ground here
                //to place the structure on
                BlockPos centerOfChunk2 = new BlockPos(chunkPos.x * 16, y-1, chunkPos.z * 16);
                for (int x = 1; x <= structureSize; x++) {
                    centerOfChunk2 = centerOfChunk2.add(1,0,0);
                    for (int z = 1; z <= structureSize; z++) {
                        centerOfChunk2 = centerOfChunk2.add(0,0,1);
                        VerticalBlockSample verticalBlockSample = chunkGenerator.getColumnSample(centerOfChunk2.getX(), centerOfChunk2.getZ(), heightLimitView);
                        BlockState blockState = verticalBlockSample.getState(centerOfChunk2);
                        if(!blockState.isAir()) {
                            solidBlocksBeneath++;
                            if(solidBlocksBeneath >= minimumSolidBlocksBeneath) {
                                break;
                            }
                        }
                    }
                    if(solidBlocksBeneath >= minimumSolidBlocksBeneath) {
                        break;
                    }
                }

                if(solidBlocksBeneath >= minimumSolidBlocksBeneath) {
                    System.out.println("Found valid spawning space at Y = " + y);
                    validY = y;
                    return true;
                }
            }
            //runs if either not enough solid blocks were found, or if the Y level isn't air.
            //then move onto the next Y level
            solidBlocksBeneath = 0;
        }
        return false;
    }

    /*
    public static BlockPos getHighestLand(ChunkGenerator chunkGenerator, MutableBoundingBox boundingBox, boolean canBeOnLiquid) {
        BlockPos.Mutable mutable = new BlockPos.Mutable().set(
                boundingBox.getCenter().getX(),
                chunkGenerator.getGenDepth() - 20,
                boundingBox.getCenter().getZ());

        IBlockReader blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ());
        BlockState currentBlockstate;
        while (mutable.getY() > chunkGenerator.getSeaLevel()) {
            currentBlockstate = blockView.getBlockState(mutable);
            if (!currentBlockstate.canOcclude()) {
                mutable.move(Direction.DOWN);
                continue;
            }
            else if (blockView.getBlockState(mutable.offset(0, 3, 0)).getMaterial() == Material.AIR && (canBeOnLiquid ? !currentBlockstate.isAir() : currentBlockstate.canOcclude())) {
                return mutable;
            }
            mutable.move(Direction.DOWN);
        }

        return mutable;
    }*/

    public static class Start extends MarginedStructureStart<DefaultFeatureConfig> {
        public Start(StructureFeature<DefaultFeatureConfig> structureIn, ChunkPos chunkPos, int referenceInt, long seedIn) {
            super(structureIn, chunkPos, referenceInt, seedIn);
        }

        @Override
        public void init(DynamicRegistryManager dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager structureManager, ChunkPos chunkPos, Biome biome, DefaultFeatureConfig defaultFeatureConfig,
                         HeightLimitView heightLimitView) {

            int x = chunkPos.x * 16;
            int z = chunkPos.z * 16;
            BlockPos.Mutable centerPos = new BlockPos.Mutable(x, validY+1, z);

            VerticalBlockSample blockView = chunkGenerator.getColumnSample(centerPos.getX(), centerPos.getZ(), heightLimitView);


            StructurePoolFeatureConfig structureSettingsAndStartPool =
                    new StructurePoolFeatureConfig(() -> dynamicRegistryManager.get(Registry.STRUCTURE_POOL_KEY)
                    .get(new Identifier(GunpowderRevision.MOD_ID, "wither_sanctum/start_pool")),
                    5);

            StructurePoolBasedGenerator.generate(
                    dynamicRegistryManager,
                    structureSettingsAndStartPool,
                    PoolStructurePiece::new,
                    chunkGenerator,
                    structureManager,
                    centerPos,
                    this,
                    this.random,
                    false, // boundary adjust for villages. keep false.
                    false, //place at heightmap. keep false if in nether to prevent bedrock roof structures
                    heightLimitView);

            this.setBoundingBoxFromChildren();

        }

    }

}
