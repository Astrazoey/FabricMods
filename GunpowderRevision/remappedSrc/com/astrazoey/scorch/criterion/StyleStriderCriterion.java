package com.astrazoey.scorch.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class StyleStriderCriterion extends AbstractCriterion<StyleStriderCriterion.Conditions> {

    static final Identifier ID = new Identifier("style_strider");

    @Override
    public Identifier getId() {
        return ID;
    }

    public StyleStriderCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended strider = EntityPredicate.Extended.getInJson(jsonObject, "strider", advancementEntityPredicateDeserializer);
        return new StyleStriderCriterion.Conditions(player, strider);
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (conditions) -> {
            return conditions.matches(player);
        });
    }


    public static class Conditions extends AbstractCriterionConditions {

        private final EntityPredicate.Extended strider;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended strider) {
            super(StyleStriderCriterion.ID, player);
            this.strider = strider;
        }

        public static StyleStriderCriterion.Conditions create() {
            return new StyleStriderCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
