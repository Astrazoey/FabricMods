package com.astrazoey.scorch.mixins;

import com.astrazoey.scorch.SetItemFireproof;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Item.class)
public class ItemMixin implements SetItemFireproof {
    @Shadow
    @Final
    @Mutable
    private boolean fireproof;

    @Override
    public void setItemFireproof(boolean fireproof) {
        this.fireproof = fireproof;
    }
}
