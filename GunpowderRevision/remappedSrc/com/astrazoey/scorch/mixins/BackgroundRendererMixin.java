package com.astrazoey.scorch.mixins;


import com.astrazoey.scorch.Config;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;


@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @ModifyConstant(method = "applyFog", constant = @Constant(floatValue = 192.0F, ordinal = 1))
    private static float modifyFogEnd(float distance) {
        //TODO: change this value depending on the biome the player is in

        if(Config.thinnerNetherFogValue == true) {
            return 3072.0F;
        } else {
            return 192.0F;
        }

    }

    @ModifyConstant(method = "applyFog", constant = @Constant(floatValue = 0.05F))
    private static float modifyFogStart(float distance) {
        return 0.01F;
    }
}
