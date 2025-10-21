package com.astrazoey.scorch.mixins;


import com.astrazoey.scorch.GunpowderRevision;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntity {

    public WitherSkeletonEntityMixin(EntityType<? extends WitherSkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
    }


    @Override
    public void onDeath(DamageSource source) {

        if (source.getSource() instanceof FireballEntity) {
            Item skull = Items.WITHER_SKELETON_SKULL;
            dropItem(skull, 0);

            //Grant advancement
            if(source.getAttacker() instanceof PlayerEntity) {
                //Direct hit from player
                GunpowderRevision.WITHER_SKELETON_KILLED_BY_FIREBALL.trigger((ServerPlayerEntity) source.getAttacker());
            } else {
                //not a direct hit from player, award advancement to nearest player
                PlayerEntity nearestPlayer = (PlayerEntity) this.world.getClosestPlayer(this, -1);
                GunpowderRevision.WITHER_SKELETON_KILLED_BY_FIREBALL.trigger((ServerPlayerEntity) nearestPlayer);
            }
        }

        super.onDeath(source);

    }


}
