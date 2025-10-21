package com.astrazoey.scorch.registry;

import com.astrazoey.scorch.GunpowderRevision;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GunpowderRevisionSounds {

    public static final Identifier APPLY_MAGMA_TO_STRIDER = new Identifier("scorch:apply_magma_to_strider");
    public static final SoundEvent APPLY_MAGMA_TO_STRIDER_EVENT = new SoundEvent(APPLY_MAGMA_TO_STRIDER);

    public static final Identifier IGNISTONE_DROPS_LAVA = new Identifier("scorch:ignistone_drops_lava");
    public static final SoundEvent IGNISTONE_DROPS_LAVA_EVENT = new SoundEvent(IGNISTONE_DROPS_LAVA);

    public static final Identifier PYRACK_IGNITES = new Identifier("scorch:pyrack_ignites");
    public static final SoundEvent PYRACK_IGNITES_EVENT = new SoundEvent(PYRACK_IGNITES);

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, GunpowderRevisionSounds.APPLY_MAGMA_TO_STRIDER, APPLY_MAGMA_TO_STRIDER_EVENT);
        Registry.register(Registry.SOUND_EVENT, GunpowderRevisionSounds.IGNISTONE_DROPS_LAVA, IGNISTONE_DROPS_LAVA_EVENT);
        Registry.register(Registry.SOUND_EVENT, GunpowderRevisionSounds.PYRACK_IGNITES, PYRACK_IGNITES_EVENT);
    }
}
