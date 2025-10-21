package com.astrazoey.scorch.mixins;


import com.astrazoey.scorch.SpectralMobs;
import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagGroup;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.html.parser.TagElement;

@Mixin(SpectralArrowEntity.class)
public class SpectralArrowEntityMixin extends PersistentProjectileEntity {

    protected SpectralArrowEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    //I don't like having this here but the game crashes on launch without it.
    @Override
    public ItemStack asItemStack() {
        return new ItemStack(Items.SPECTRAL_ARROW);
    }

    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    public void applySpectralDamage(LivingEntity target, CallbackInfo ci) {
        if(SpectralMobs.isSpectral(target)) {
            target.damage(DamageSource.arrow(this, this.getOwner()), 15f); //+15 damage to "spectral" mobs
            World world = target.getEntityWorld();
            if(world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(ParticleTypes.ELECTRIC_SPARK, target.getX(), target.getY(), target.getZ(), 30, 0.5d, 0.5d, 0.5d, 0d);
            }
        }
    }


}
