package com.astrazoey.scorch.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class WitherSkeletonKilledByFireballCriterion extends AbstractCriterion<WitherSkeletonKilledByFireballCriterion.Conditions> {

    static final Identifier ID = new Identifier("wither_skeleton_killed_by_fireball");

    @Override
    public Identifier getId() {
        return ID;
    }

    public WitherSkeletonKilledByFireballCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended wither_skeleton = EntityPredicate.Extended.getInJson(jsonObject, "wither_skeleton", advancementEntityPredicateDeserializer);
        return new WitherSkeletonKilledByFireballCriterion.Conditions(player, wither_skeleton);
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (conditions) -> {
            return conditions.matches(player);
        });
    }


    public static class Conditions extends AbstractCriterionConditions {

        private final EntityPredicate.Extended wither_skeleton;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended wither_skeleton) {
            super(WitherSkeletonKilledByFireballCriterion.ID, player);
            this.wither_skeleton = wither_skeleton;
        }

        public static WitherSkeletonKilledByFireballCriterion.Conditions create() {
            return new WitherSkeletonKilledByFireballCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
