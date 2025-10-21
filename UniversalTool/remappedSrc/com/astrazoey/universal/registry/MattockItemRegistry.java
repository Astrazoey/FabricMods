package com.astrazoey.universal.registry;

import com.astrazoey.universal.MattockMain;
import com.astrazoey.universal.MattockToolItem;
import com.astrazoey.universal.MattockToolMaterial;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MattockItemRegistry {
    public static final ToolItem MATTOCK = new MattockToolItem(
            MattockToolMaterial.INSTANCE,
            3,
            -3.0F,
            new Item.Settings());

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(MATTOCK);
    }

    public static void registerItems() {
        Registry.register(Registries.ITEM, new Identifier(MattockMain.MOD_ID, "mattock"), MATTOCK);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(MattockItemRegistry::addItemsToToolsTabItemGroup);

    }
}
