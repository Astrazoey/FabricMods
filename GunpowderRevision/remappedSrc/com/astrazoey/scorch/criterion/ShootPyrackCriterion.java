package com.astrazoey.scorch.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ShootPyrackCriterion extends AbstractCriterion<ShootPyrackCriterion.Conditions> {

    static final Identifier ID = new Identifier("shoot_pyrack");

    @Override
    public Identifier getId() {
        return ID;
    }

    public ShootPyrackCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended arrow = EntityPredicate.Extended.getInJson(jsonObject, "arrow", advancementEntityPredicateDeserializer);
        return new ShootPyrackCriterion.Conditions(player, arrow);
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (conditions) -> {
            return conditions.matches(player);
        });
    }


    public static class Conditions extends AbstractCriterionConditions {

        private final EntityPredicate.Extended arrow;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended arrow) {
            super(ShootPyrackCriterion.ID, player);
            this.arrow = arrow;
        }

        public static ShootPyrackCriterion.Conditions create() {
            return new ShootPyrackCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
