package com.astrazoey.scorch.registry;

import com.astrazoey.scorch.IgnistoneBlock;
import com.astrazoey.scorch.PyrackBlock;
import com.astrazoey.scorch.PrimedPyrackBlock;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.block.Material;
import net.minecraft.block.Block;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import com.astrazoey.scorch.GunpowderRevision;

public class GunpowderRevisionBlocks {




    private static Block.Settings pyrack() {
        return FabricBlockSettings
                .of(Material.STONE)
                .sounds(BlockSoundGroup.NETHERRACK)
                .strength(3.0f, 0.4f)
                .breakByTool(FabricToolTags.PICKAXES);
    }

    private static Block.Settings primedPyrack() {
        return FabricBlockSettings
                .of(Material.STONE)
                .sounds(BlockSoundGroup.NETHERRACK)
                .strength(3.0f, 500f)
                .breakByTool(FabricToolTags.PICKAXES)
                //.emissiveLighting((state, world, pos) -> true)
                .luminance(3);
    }

    private static Block.Settings ignistone() {
        return FabricBlockSettings
                .of(Material.STONE)
                .sounds(BlockSoundGroup.STONE)
                .strength(3.0f, 6.0f)
                .breakByTool(FabricToolTags.PICKAXES);
    }

    public static final Block PYRACK = new PyrackBlock(pyrack());
    public static final Block PRIMED_PYRACK = new PrimedPyrackBlock(primedPyrack());
    public static final Block IGNISTONE = new IgnistoneBlock(ignistone());

    public static void registerBlocks() {
        FlammableBlockRegistry.getDefaultInstance().add(PYRACK, 50, 100);

        Registry.register(Registry.BLOCK, new Identifier(GunpowderRevision.MOD_ID, "pyrack"), PYRACK);
        Registry.register(Registry.BLOCK, new Identifier(GunpowderRevision.MOD_ID, "primed_pyrack"), PRIMED_PYRACK);
        Registry.register(Registry.BLOCK, new Identifier(GunpowderRevision.MOD_ID, "ignistone"), IGNISTONE);
    }
}
