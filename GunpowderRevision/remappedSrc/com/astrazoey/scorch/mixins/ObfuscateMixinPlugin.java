package com.astrazoey.scorch.mixins;

import jdk.dynalink.linker.support.Lookup;
import net.fabricmc.fabric.api.lookup.v1.custom.ApiLookupMap;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class ObfuscateMixinPlugin implements IMixinConfigPlugin {

    private boolean optifineLoaded;

    @Override
    public void onLoad(String mixinPackage) {
        if(FabricLoader.getInstance().isModLoaded("optifabric")) {
            this.optifineLoaded = true;
        } else {
            this.optifineLoaded = false;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {

        if(mixinClassName.equals("com.astrazoey.scorch.mixins.BackgroundRendererMixin")) {
            if (optifineLoaded) {
                System.out.println("Scorch: Optifabric is installed. BackgroundRenderer mixin skipped.");
                return false;
            } else {
                System.out.println("Scorch: Optifabric is not installed. BackgroundRenderer mixin enabled.");
                return true;
            }
        } else {
            return true; //all other mixins will enable
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
