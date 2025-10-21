package com.astrazoey.scorch.mixins;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(GhastEntity.class)
public class GhastEntityMixin extends FlyingEntity {

    protected GhastEntityMixin(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean cannotDespawn() {

        if(this.hasVehicle()) {
            return true;
        }

        PlayerEntity nearestPlayer = this.world.getClosestPlayer(this, -1.0D);
        if(nearestPlayer == null) {
            return false;
        }

        double playerDistance = nearestPlayer.squaredDistanceTo(this);
        int despawnRange = this.getType().getSpawnGroup().getImmediateDespawnRange();

        if((playerDistance > (despawnRange * despawnRange)) && this.canImmediatelyDespawn(playerDistance)) {
            return false;
        } else {
            return true;
        }
    }

}
