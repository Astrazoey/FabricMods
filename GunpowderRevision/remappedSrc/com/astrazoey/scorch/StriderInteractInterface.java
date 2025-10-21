package com.astrazoey.scorch;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public interface StriderInteractInterface {
    void interactWithStrider(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir);
    void useShears(PlayerEntity player, ItemStack itemStack, Hand hand);
    void sheared(SoundCategory shearedSoundCategory);
    ActionResult shearStrider(PlayerEntity player, ItemStack itemStack, Hand hand, SoundCategory soundCategory);
    ActionResult creamStrider(PlayerEntity player, ItemStack itemStack, Hand hand, SoundCategory soundCategory);
    ActionResult feedStrider(PlayerEntity player, ItemStack itemStack, Hand hand, SoundCategory soundCategory);
}
