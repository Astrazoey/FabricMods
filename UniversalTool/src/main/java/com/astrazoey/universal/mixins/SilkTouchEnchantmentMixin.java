package com.astrazoey.universal.mixins;

import com.astrazoey.universal.MattockItem;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.SilkTouchEnchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.enchantment.Enchantment;

@Mixin(SilkTouchEnchantment.class)
public class SilkTouchEnchantmentMixin extends Enchantment {

    protected SilkTouchEnchantmentMixin(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {

        if(stack.getItem() instanceof MattockItem) {
            return true;
        } else {
            return this.target.isAcceptableItem(stack.getItem());
        }
    }
}