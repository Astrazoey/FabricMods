package com.astrazoey.scorch;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class GunpowderRevisionConfiguredStructures {

    public static ConfiguredStructureFeature<?,?> CONFIGURED_DEBUG_STRUCTURE =
            GunpowderRevisionStructures.DEBUG_STRUCTURE.configure(DefaultFeatureConfig.DEFAULT);

    public static ConfiguredStructureFeature<?,?> CONFIGURED_DEBRIS_STRUCTURE =
            GunpowderRevisionStructures.DEBRIS_STRUCTURE.configure(DefaultFeatureConfig.DEFAULT);

    public static ConfiguredStructureFeature<?,?> CONFIGURED_WITHER_SANCTUM =
            GunpowderRevisionStructures.WITHER_SANCTUM.configure(DefaultFeatureConfig.DEFAULT);

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?,?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(GunpowderRevision.MOD_ID, "configured_debug_structure"), CONFIGURED_DEBUG_STRUCTURE);

        //Registry<ConfiguredStructureFeature<?,?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new Identifier(GunpowderRevision.MOD_ID, "configured_debris_structure"), CONFIGURED_DEBRIS_STRUCTURE);

        Registry.register(registry, new Identifier(GunpowderRevision.MOD_ID, "configured_wither_sanctum"), CONFIGURED_WITHER_SANCTUM);
    }

}
