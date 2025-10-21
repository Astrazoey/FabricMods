package com.astrazoey.scorch;
import com.astrazoey.scorch.criterion.*;
import com.astrazoey.scorch.mixins.CriterionRegistryAccessor;
import com.astrazoey.scorch.registry.GunpowderRevisionBlocks;
import com.astrazoey.scorch.registry.GunpowderRevisionEvents;
import com.astrazoey.scorch.registry.GunpowderRevisionItems;
import com.astrazoey.scorch.registry.GunpowderRevisionSounds;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.RuleTestType;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
//import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.heightprovider.UniformHeightProvider;
import java.util.List;
import java.util.Random;


public class GunpowderRevision implements ModInitializer {

    public static final String MOD_ID = "scorch";



    public static final PlacedFeature PYRACK_CONFIGURED_LOW =
            Feature.ORE.configure(
                    new OreFeatureConfig(List.of(
                            OreFeatureConfig.createTarget(new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER),
                                    GunpowderRevisionBlocks.PYRACK.getDefaultState())), 27))
                    .withPlacement(List.of(
                            CountPlacementModifier.of(Config.lowPyrackSpawnRate),
                            HeightRangePlacementModifier.uniform(YOffset.fixed(5),YOffset.fixed(15)),
                            SquarePlacementModifier.of()
                            )
                    );

    public static final PlacedFeature PYRACK_CONFIGURED_HIGH =
            Feature.ORE.configure(
                            new OreFeatureConfig(List.of(
                                    OreFeatureConfig.createTarget(new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER),
                                            GunpowderRevisionBlocks.PYRACK.getDefaultState())), 15))
                    .withPlacement(List.of(
                                    CountPlacementModifier.of(Config.highPyrackSpawnRate),
                                    HeightRangePlacementModifier.uniform(YOffset.fixed(40),YOffset.fixed(120)),
                                    SquarePlacementModifier.of()
                            )
                    );


    /*
    public static final PlacedFeature PYRACK_CONFIGURED_LOW = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_NETHER, GunpowderRevisionBlocks.PYRACK.getDefaultState(),
                    27))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(
                    YOffset.fixed(5),
                    YOffset.fixed(15)))))
            .spreadHorizontally()
            .repeat(Config.lowPyrackSpawnRate);

    public static final PlacedFeature PYRACK_CONFIGURED_HIGH = Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_NETHER, GunpowderRevisionBlocks.PYRACK.getDefaultState(),
            15))
            .decorate(Decorator.RANGE.configure(new RangeDecoratorConfig(UniformHeightProvider.create(
                    YOffset.fixed(40),
                    YOffset.fixed(120)))))
            .spreadHorizontally()
            .repeat(Config.highPyrackSpawnRate);
    */

    public static ShearStriderCriterion SHEAR_STRIDER = new ShearStriderCriterion();
    public static StyleStriderCriterion STYLE_STRIDER = new StyleStriderCriterion();
    public static ShootPyrackCriterion SHOOT_PYRACK = new ShootPyrackCriterion();
    public static GhastPyrackCriterion GHAST_PYRACK = new GhastPyrackCriterion();
    public static MineIgnistoneCriterion MINE_IGNISTONE = new MineIgnistoneCriterion();
    public static WitherSkeletonKilledByFireballCriterion WITHER_SKELETON_KILLED_BY_FIREBALL = new WitherSkeletonKilledByFireballCriterion();



    private void successfullyUsedItem(PlayerEntity player, ItemStack heldItem, Item item) {
        player.incrementStat(Stats.USED.getOrCreateStat(item));
        if(!player.isCreative()) {
            heldItem.decrement(1);
        }
    }


    @Override
    public void onInitialize() {

        Config.load();

        //Fireproof items
        SetItemFireproof.set(Items.WITHER_SKELETON_SKULL, true);

        //Registration
        GunpowderRevisionBlocks.registerBlocks();
        GunpowderRevisionItems.registerItems();
        GunpowderRevisionSounds.registerSounds();
        GunpowderRevisionEvents.registerEvents();

        //Criterion Registration
        CriterionRegistryAccessor.registerCriterion(SHEAR_STRIDER);
        CriterionRegistryAccessor.registerCriterion(STYLE_STRIDER);
        CriterionRegistryAccessor.registerCriterion(SHOOT_PYRACK);
        CriterionRegistryAccessor.registerCriterion(GHAST_PYRACK);
        CriterionRegistryAccessor.registerCriterion(MINE_IGNISTONE);
        CriterionRegistryAccessor.registerCriterion(WITHER_SKELETON_KILLED_BY_FIREBALL);


        //Ore Generation
        RegistryKey<PlacedFeature> pyrackLow = RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("scorch", "pyrack_low"));
        RegistryKey<PlacedFeature> pyrackHigh = RegistryKey.of(Registry.PLACED_FEATURE_KEY,
                new Identifier("scorch", "pyrack_high"));

        Registry.register(BuiltinRegistries.PLACED_FEATURE, pyrackLow.getValue(), PYRACK_CONFIGURED_LOW);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, pyrackHigh.getValue(), PYRACK_CONFIGURED_HIGH);

        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, pyrackLow);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.CRIMSON_FOREST), GenerationStep.Feature.UNDERGROUND_ORES, pyrackHigh);


        //STRUCTURES

        /*
        GunpowderRevisionStructures.setupAndRegisterStructureFeatures();
        GunpowderRevisionConfiguredStructures.registerConfiguredStructures();


        /*
        BiomeModifications.create(new Identifier(MOD_ID, "debug_structure"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheNether(),
                        context -> {
                    context.getGenerationSettings().addBuiltInStructure(GunpowderRevisionConfiguredStructures.CONFIGURED_DEBUG_STRUCTURE);
                        }
                        );

         */

        /*
        BiomeModifications.create(new Identifier(MOD_ID, "debris_structure"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.foundInTheNether(),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(GunpowderRevisionConfiguredStructures.CONFIGURED_DEBRIS_STRUCTURE);
                        }
                );

        BiomeModifications.create(new Identifier(MOD_ID, "wither_sanctum"))
                .add(ModificationPhase.ADDITIONS,
                        BiomeSelectors.includeByKey(BiomeKeys.NETHER_WASTES, BiomeKeys.SOUL_SAND_VALLEY, BiomeKeys.CRIMSON_FOREST),
                        context -> {
                            context.getGenerationSettings().addBuiltInStructure(GunpowderRevisionConfiguredStructures.CONFIGURED_WITHER_SANCTUM);
                        }
                );

         */

    }


}