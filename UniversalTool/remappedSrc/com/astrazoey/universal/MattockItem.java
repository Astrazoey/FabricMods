package com.astrazoey.universal;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;

public class MattockItem extends MattockTypeItem {
    protected MattockItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super((float)attackDamage, attackSpeed, material, BlockTags.PICKAXE_MINEABLE, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {

        float finalSpeed = this.miningSpeed;

        if(state.getBlock().getHardness() <= 0.3) {
            finalSpeed = finalSpeed / 3;
        } else if(state.getBlock().getHardness() <= 0.4) {
            finalSpeed = finalSpeed / 2;
        } else if(state.getBlock().getHardness() <= 20) {
            finalSpeed = finalSpeed / 1;
        } else
            finalSpeed = finalSpeed * 5;


        return finalSpeed;
    }

}
