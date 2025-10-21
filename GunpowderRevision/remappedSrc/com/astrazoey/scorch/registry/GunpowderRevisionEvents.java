package com.astrazoey.scorch.registry;

import com.astrazoey.scorch.StriderInteractInterface;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class GunpowderRevisionEvents {


    public static void registerEvents() {

        System.out.println("Scorch events registered.");

        //Interact with Strider
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (entity instanceof StriderEntity) {
                StriderEntity striderEntity = (StriderEntity) entity;
                StriderInteractInterface x = (StriderInteractInterface) striderEntity;

                if (striderEntity != null) {
                    ItemStack heldItem = player.getStackInHand(hand);
                    if (player.getStackInHand(hand).getItem() == Items.SHEARS) {
                        return x.shearStrider(player, heldItem, hand,SoundCategory.PLAYERS);
                    } else if (player.getStackInHand(hand).getItem() == Items.WARPED_ROOTS) {
                        return x.feedStrider(player, heldItem, hand,SoundCategory.PLAYERS);
                    } else if (player.getStackInHand(hand).getItem() == Items.MAGMA_CREAM) {
                        return x.creamStrider(player, heldItem, hand,SoundCategory.PLAYERS);
                    } else {
                        return ActionResult.PASS;
                    }
                } else {
                    return ActionResult.PASS;
                }
            } else {
                return ActionResult.PASS;
            }
        });
    }
}
