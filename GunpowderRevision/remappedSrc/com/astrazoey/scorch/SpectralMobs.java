package com.astrazoey.scorch;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.mob.VexEntity;

public class SpectralMobs {

    public static boolean isSpectral(LivingEntity entity) {
        if(entity instanceof GhastEntity || entity instanceof VexEntity || entity instanceof PhantomEntity || entity instanceof WitherEntity) {
            return true;
        } else {
            return false;
        }
    }

}
