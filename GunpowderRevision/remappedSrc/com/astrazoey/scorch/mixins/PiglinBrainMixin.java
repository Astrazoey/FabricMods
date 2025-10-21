package com.astrazoey.scorch.mixins;

import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @Inject(method = "drop", at = @At("HEAD"), cancellable = true)
    private static void addSaddleToDrop(PiglinEntity piglin, List<ItemStack> list, Vec3d vec3d, CallbackInfo ci) {

        if(piglin.world.random.nextInt(6) == 0) {
            if(!list.isEmpty() && list != null) {
                piglin.swingHand(Hand.OFF_HAND);
                ItemStack itemStack = new ItemStack(Items.SADDLE);
                LookTargetUtil.give(piglin, itemStack, vec3d.add(0.0D, 1.0D, 0.0D));
                ci.cancel();
            }
        }
    }
}
