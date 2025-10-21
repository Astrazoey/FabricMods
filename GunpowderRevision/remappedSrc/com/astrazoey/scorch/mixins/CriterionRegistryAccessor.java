package com.astrazoey.scorch.mixins;


import com.astrazoey.scorch.criterion.ShearStriderCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("PublicStaticMixinMember")
@Mixin(Criteria.class)
public interface CriterionRegistryAccessor {

    @Invoker("register")
    static <T extends Criterion<?>> T registerCriterion(T criterion) {
        System.out.println("Registered criterion!");
        return null;
    }
}
