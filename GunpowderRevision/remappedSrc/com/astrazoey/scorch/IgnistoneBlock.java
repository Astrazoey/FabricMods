package com.astrazoey.scorch;

import com.astrazoey.scorch.registry.GunpowderRevisionSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class IgnistoneBlock extends Block {
    public IgnistoneBlock(Settings settings) {
        super(settings);
    }


    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        ItemStack stack = player.getMainHandStack();
        if ((EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) && (!player.isCreative())) {
            world.playSound(pos.getX(), pos.getY(), pos.getZ(), GunpowderRevisionSounds.IGNISTONE_DROPS_LAVA_EVENT, SoundCategory.BLOCKS, 1.0f, 0.75f, true);
        }
        super.onBreak(world,pos,state,player);

    }


    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, state, blockEntity, stack);
        if ((EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) && (!player.isCreative())) {
            placeLava(world, pos);
        } else if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            if (player instanceof ServerPlayerEntity) {
                GunpowderRevision.MINE_IGNISTONE.trigger((ServerPlayerEntity)player);
            }
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
        placeLava(world, blockPos);
    }

    public void placeLava(World world, BlockPos pos) {
        world.setBlockState(pos, Blocks.LAVA.getDefaultState());

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.LAVA, pos.getX(), pos.getY(), pos.getZ(), 5,  0.1d, 0.1d, 0.1d, 0.2d);
            serverWorld.spawnParticles(ParticleTypes.SMOKE, pos.getX(), pos.getY(), pos.getZ(), 5,  0.1d, 0.1d, 0.1d, 0.2d);
        }
    }
}
