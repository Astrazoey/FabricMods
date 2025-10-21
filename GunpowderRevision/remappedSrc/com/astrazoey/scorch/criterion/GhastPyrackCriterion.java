package com.astrazoey.scorch.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class GhastPyrackCriterion extends AbstractCriterion<GhastPyrackCriterion.Conditions> {

    static final Identifier ID = new Identifier("ghast_pyrack");

    @Override
    public Identifier getId() {
        return ID;
    }

    public GhastPyrackCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended ghast = EntityPredicate.Extended.getInJson(jsonObject, "ghast", advancementEntityPredicateDeserializer);
        return new GhastPyrackCriterion.Conditions(player, ghast);
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (conditions) -> {
            return conditions.matches(player);
        });
    }


    public static class Conditions extends AbstractCriterionConditions {

        private final EntityPredicate.Extended ghast;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended ghast) {
            super(GhastPyrackCriterion.ID, player);
            this.ghast = ghast;
        }

        public static GhastPyrackCriterion.Conditions create() {
            return new GhastPyrackCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
