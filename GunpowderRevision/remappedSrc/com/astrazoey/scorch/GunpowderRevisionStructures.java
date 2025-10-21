package com.astrazoey.scorch;

import com.astrazoey.scorch.structures.DebrisStructure;
import com.astrazoey.scorch.structures.DebugStructure;
import com.astrazoey.scorch.structures.WitherSanctum;
import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class GunpowderRevisionStructures {

    public static StructureFeature<DefaultFeatureConfig> DEBUG_STRUCTURE = new DebugStructure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> DEBRIS_STRUCTURE = new DebrisStructure(DefaultFeatureConfig.CODEC);
    public static StructureFeature<DefaultFeatureConfig> WITHER_SANCTUM = new WitherSanctum(DefaultFeatureConfig.CODEC);

    public static void setupAndRegisterStructureFeatures() {
        FabricStructureBuilder.create(new Identifier(GunpowderRevision.MOD_ID, "debug_structure"), DEBUG_STRUCTURE)
                .step(GenerationStep.Feature.UNDERGROUND_STRUCTURES)
                .defaultConfig(new StructureConfig(
                        9, //distance apart in chunks
                        7, //minimum distance apart in chunks
                        452782645 //structure seed to prevent structures spawning over each other
                ))
                .superflatFeature(DEBUG_STRUCTURE.configure(FeatureConfig.DEFAULT))
                //.adjustsSurface() //if surrounding land will be modified to conform to bottom of the structure
                .register();

        FabricStructureBuilder.create(new Identifier(GunpowderRevision.MOD_ID, "debris_structure"), DEBRIS_STRUCTURE)
                .step(GenerationStep.Feature.UNDERGROUND_STRUCTURES)
                .defaultConfig(new StructureConfig(
                        11, //distance apart in chunks
                        8, //minimum distance apart in chunks
                        655703111 //structure seed to prevent structures spawning over each other
                ))
                .superflatFeature(DEBRIS_STRUCTURE.configure(FeatureConfig.DEFAULT))
                //.adjustsSurface() //if surrounding land will be modified to conform to bottom of the structure
                .register();

        FabricStructureBuilder.create(new Identifier(GunpowderRevision.MOD_ID, "wither_sanctum"), WITHER_SANCTUM)
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)
                .defaultConfig(new StructureConfig(
                        5, //distance apart in chunks
                        4, //minimum distance apart in chunks
                        736186643 //structure seed to prevent structures spawning over each other
                ))
                .superflatFeature(WITHER_SANCTUM.configure(FeatureConfig.DEFAULT))
                .adjustsSurface() //if surrounding land will be modified to conform to bottom of the structure
                .register();

    }

}
