package com.astrazoey.scorch.mixins;

import com.astrazoey.scorch.StriderHairInterface;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.StriderEntityRenderer;
import net.minecraft.client.render.entity.model.StriderEntityModel;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(StriderEntityRenderer.class)
public abstract class StriderEntityRendererMixin extends MobEntityRenderer<StriderEntity, StriderEntityModel<StriderEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/strider/strider.png");
    private static final Identifier COLD_TEXTURE = new Identifier("textures/entity/strider/strider_cold.png");
    private static final Identifier TEXTURE_HAIRLESS = new Identifier("textures/entity/strider/strider_no_hair.png");
    private static final Identifier COLD_TEXTURE_HAIRLESS = new Identifier("textures/entity/strider/strider_cold_no_hair.png");

    private static final Identifier TEXTURE_HAIR_1 = new Identifier("textures/entity/strider/strider_hair_1.png");
    private static final Identifier COLD_TEXTURE_HAIR_1 = new Identifier("textures/entity/strider/strider_cold_hair_1.png");

    private static final Identifier TEXTURE_HAIR_2 = new Identifier("textures/entity/strider/strider_hair_2.png");
    private static final Identifier COLD_TEXTURE_HAIR_2 = new Identifier("textures/entity/strider/strider_cold_hair_2.png");

    private static final Identifier TEXTURE_HAIR_3 = new Identifier("textures/entity/strider/strider_hair_3.png");
    private static final Identifier COLD_TEXTURE_HAIR_3 = new Identifier("textures/entity/strider/strider_cold_hair_3.png");

    private static final Identifier TEXTURE_HAIR_4 = new Identifier("textures/entity/strider/strider_hair_4.png");
    private static final Identifier COLD_TEXTURE_HAIR_4 = new Identifier("textures/entity/strider/strider_cold_hair_4.png");

    private static final Identifier TEXTURE_HAIR_5 = new Identifier("textures/entity/strider/strider_hair_5.png");
    private static final Identifier COLD_TEXTURE_HAIR_5 = new Identifier("textures/entity/strider/strider_cold_hair_5.png");

    public StriderEntityRendererMixin(EntityRendererFactory.Context context, StriderEntityModel<StriderEntity> entityModel, float f) {
        super(context, entityModel, f);
    }


    /**
     * @author Astrazoey
     */
    @Overwrite
    public Identifier getTexture(StriderEntity striderEntity) {

        StriderHairInterface x = (StriderHairInterface) striderEntity;

        if(striderEntity.isCold()) {
            if(x.hasHair()) {
                return switch (x.getHairStyle()) {
                    case 1 -> COLD_TEXTURE_HAIR_1;
                    case 2 -> COLD_TEXTURE_HAIR_2;
                    case 3 -> COLD_TEXTURE_HAIR_3;
                    case 4 -> COLD_TEXTURE_HAIR_4;
                    case 5 -> COLD_TEXTURE_HAIR_5;
                    default -> COLD_TEXTURE;
                };
            } else {
                return COLD_TEXTURE_HAIRLESS;
            }
        } else {
            if(x.hasHair()) {
                return switch (x.getHairStyle()) {
                    case 1 -> TEXTURE_HAIR_1;
                    case 2 -> TEXTURE_HAIR_2;
                    case 3 -> TEXTURE_HAIR_3;
                    case 4 -> TEXTURE_HAIR_4;
                    case 5 -> TEXTURE_HAIR_5;
                    default -> TEXTURE;
                };
            } else {
                return TEXTURE_HAIRLESS;
            }
        }
    }
}
