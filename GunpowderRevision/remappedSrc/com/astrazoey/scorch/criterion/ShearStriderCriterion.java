package com.astrazoey.scorch.criterion;

import com.astrazoey.scorch.mixins.StriderEntityMixin;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.DamagePredicate;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ShearStriderCriterion extends AbstractCriterion<com.astrazoey.scorch.criterion.ShearStriderCriterion.Conditions> {

    static final Identifier ID = new Identifier("shear_strider");

    @Override
    public Identifier getId() {
        return ID;
    }

    public ShearStriderCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        EntityPredicate.Extended strider = EntityPredicate.Extended.getInJson(jsonObject, "strider", advancementEntityPredicateDeserializer);
        return new ShearStriderCriterion.Conditions(player, strider);
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (conditions) -> {
            return conditions.matches(player);
        });
    }


    public static class Conditions extends AbstractCriterionConditions {

        private final EntityPredicate.Extended strider;

        public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended strider) {
            super(ShearStriderCriterion.ID, player);
            this.strider = strider;
        }

        public static ShearStriderCriterion.Conditions create() {
            return new ShearStriderCriterion.Conditions(EntityPredicate.Extended.EMPTY, EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
