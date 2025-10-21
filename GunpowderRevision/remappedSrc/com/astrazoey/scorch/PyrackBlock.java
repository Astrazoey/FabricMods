package com.astrazoey.scorch;

import com.astrazoey.scorch.registry.GunpowderRevisionSounds;
import net.minecraft.block.*;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import com.astrazoey.scorch.registry.GunpowderRevisionBlocks;

public class PyrackBlock extends OreBlock {



    public PyrackBlock(Settings settings) {
        super(settings, UniformIntProvider.create(1, 10));
    }


    @Deprecated
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        ItemStack stack = player.getMainHandStack();

        if (EnchantmentHelper.getLevel(Enchantments.FIRE_ASPECT, stack) > 0) {
            prime(world, pos);
            Hand hand = player.getActiveHand();
            stack.damage(1, player, (playerx) -> {
                playerx.sendToolBreakStatus(hand);
            });
        }
        super.onBlockBreakStart(state, world, pos, player);
    }


    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            Entity entity = projectile.getOwner();



            if (projectile.isOnFire() && projectile.canModifyAt(world, blockPos)) {
                prime(world, blockPos);

                if (entity instanceof ServerPlayerEntity) {
                    if(projectile instanceof ArrowEntity) {
                        GunpowderRevision.SHOOT_PYRACK.trigger((ServerPlayerEntity) entity);
                    }
                    if(projectile instanceof FireballEntity) {
                        GunpowderRevision.GHAST_PYRACK.trigger((ServerPlayerEntity) entity);
                    }
                }

            }
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
        prime(world, blockPos);
    }



    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!itemStack.isOf(Items.FLINT_AND_STEEL) && !itemStack.isOf(Items.FIRE_CHARGE)) {
            return super.onUse(state, world, pos, player, hand, hit);
        } else {
            prime(world, pos);
            Item item = itemStack.getItem();
            if (!player.isCreative()) {
                if (itemStack.isOf(Items.FLINT_AND_STEEL)) {
                    itemStack.damage(1, player, (playerx) -> {
                        playerx.sendToolBreakStatus(hand);
                    });
                } else {
                    itemStack.decrement(1);
                }
            }

            player.incrementStat(Stats.USED.getOrCreateStat(item));
            return ActionResult.success(world.isClient);
        }
    }

    public static void prime(World world, BlockPos pos) {
        world.playSound(null, pos, GunpowderRevisionSounds.PYRACK_IGNITES_EVENT, SoundCategory.BLOCKS, 1.0f, 2.0f);
        world.setBlockState(pos, GunpowderRevisionBlocks.PRIMED_PYRACK.getDefaultState());
    }


    public static void detonate(World world, BlockPos blockPos) {

        //get center of block
        Vector3d centeredBlockPos = new Vector3d(blockPos.getX()+0.5, blockPos.getY()+0.5, blockPos.getZ()+0.5);


        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.FLAME, centeredBlockPos.x, centeredBlockPos.y, centeredBlockPos.z, 5,  0.1d, 0.1d, 0.1d, 0.2d);
            serverWorld.spawnParticles(ParticleTypes.LAVA, centeredBlockPos.x, centeredBlockPos.y, centeredBlockPos.z, 3,  0.1d, 0.1d, 0.1d, 0.2d);
        }
        world.createExplosion(null, DamageSource.GENERIC, null, centeredBlockPos.x, centeredBlockPos.y, centeredBlockPos.z, 1.85f, true, Explosion.DestructionType.DESTROY);
    }


}
