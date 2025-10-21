package com.astrazoey.scorch.criterion;

import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class MineIgnistoneCriterion extends AbstractCriterion<MineIgnistoneCriterion.Conditions> {

    static final Identifier ID = new Identifier("mine_ignistone");

    @Override
    public Identifier getId() {
        return ID;
    }

    public MineIgnistoneCriterion.Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended player, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new MineIgnistoneCriterion.Conditions(player);
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (conditions) -> {
            return conditions.matches(player);
        });
    }


    public static class Conditions extends AbstractCriterionConditions {

        public Conditions(EntityPredicate.Extended player) {
            super(MineIgnistoneCriterion.ID, player);
        }

        public static MineIgnistoneCriterion.Conditions create() {
            return new MineIgnistoneCriterion.Conditions(EntityPredicate.Extended.EMPTY);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
