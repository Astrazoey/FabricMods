package com.astrazoey.scorch;

import net.minecraft.item.Item;

public interface SetItemFireproof {
    static void set(Item item, boolean fireproof) {
        ((SetItemFireproof) item).setItemFireproof(fireproof);
    }

    void setItemFireproof(boolean fireproof);
}
