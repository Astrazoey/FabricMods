package com.astrazoey.universal;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

public class MattockToolItem extends MattockItem {

    public MattockToolItem(ToolMaterial material, int attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }


    public MattockToolItem(MattockToolMaterial instance, int attackDamage, float attackSpeed, Item.Settings settings) {
        super();
    }
}
