package com.astrazoey.scorch.mixins;

import com.astrazoey.scorch.GunpowderRevision;
import com.astrazoey.scorch.StriderHairInterface;
import com.astrazoey.scorch.StriderInteractInterface;
import com.astrazoey.scorch.registry.GunpowderRevisionSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ThreadLocalRandom;

@Mixin(StriderEntity.class)
public abstract class StriderEntityMixin extends AnimalEntity implements Shearable, StriderHairInterface, StriderInteractInterface {

    @Shadow public abstract boolean isSaddled();

    @Shadow @Final private SaddledComponent saddledComponent;
    private static final TrackedData<Boolean> HAIR_STATE;
    private static final TrackedData<Integer> HAIR_GROWTH;
    private static final TrackedData<Integer> HAIR_STYLE;

    public StriderEntityMixin(EntityType<? extends AnimalEntity> entityType, World world, SaddledComponent saddledComponent) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"), cancellable = true)
    public void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(HAIR_STATE, true);
        this.dataTracker.startTracking(HAIR_GROWTH, 0);
        this.dataTracker.startTracking(HAIR_STYLE, 0);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"), cancellable = true)
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("HasHair", this.hasHair());
        nbt.putInt("HairGrowth", this.getHairGrowth());
        nbt.putInt("HairStyle", this.getHairStyle());
    }


    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"), cancellable = true)
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        this.setHasHair(nbt.getBoolean("HasHair"));
        this.setHairGrowth(nbt.getInt("HairGrowth"));
        this.setHairStyle(nbt.getInt("HairStyle"));
    }

    public void setHasHair(boolean hasHair) {
        this.dataTracker.set(HAIR_STATE, hasHair);
    }

    public boolean hasHair() {
        return this.dataTracker.get(HAIR_STATE);
    }

    public void setHairGrowth(int hairGrowth) {this.dataTracker.set(HAIR_GROWTH, hairGrowth);}

    public int getHairGrowth() {return this.dataTracker.get(HAIR_GROWTH);}

    public void setHairStyle(int hairStyle) {this.dataTracker.set(HAIR_STYLE, hairStyle);}

    public int getHairStyle() {return this.dataTracker.get(HAIR_STYLE);}

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
        if (!this.world.isClient()) {
            this.setHasHair(false);
            this.dropStack(new ItemStack(Items.STRING, ThreadLocalRandom.current().nextInt(2, 4 + 1)), 1.2F);
        }
    }

    public void fedRoots(SoundCategory fedSoundCategory) {
        this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_STRIDER_EAT, fedSoundCategory, 1.0F, 1.0F);

        if (this.world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.SMOKE, this.getX(), this.getY()+0.75, this.getZ(), 50,  0.4d, 0.4d, 0.4d, 0.0d);
        }

        if (!this.world.isClient()) {
            int hairGrowthIncrease = this.getHairGrowth() / 2;
            this.setHairGrowth(this.getHairGrowth() + hairGrowthIncrease);
        }
    }

    public void styleStrider(SoundCategory styleSoundCategory) {
        this.world.playSoundFromEntity((PlayerEntity)null, this, GunpowderRevisionSounds.APPLY_MAGMA_TO_STRIDER_EVENT, styleSoundCategory, 1.0F, 1.0F);

        if (this.world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.CLOUD, this.getX(), this.getY()+0.75, this.getZ(), 7,  0.4d, 0.4d, 0.4d, 0.0d);
        }

        int hairStyle = this.getHairStyle();
        if(hairStyle == 5) {
            this.setHairStyle(0);
        } else {
            this.setHairStyle(hairStyle+1);
        }
    }

    public void removeSaddle(SoundCategory removeSaddleSoundCategory) {
        this.world.playSoundFromEntity((PlayerEntity)null, this, SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, removeSaddleSoundCategory, 1.0F, 1.0F);

        if (!this.world.isClient()) {
            this.saddledComponent.setSaddled(false);
            this.dropStack(new ItemStack(Items.SADDLE, 1), 1.2f);
        }
    }

    public void swingHandAndIncrementStat(PlayerEntity player, ItemStack itemStack, Hand hand) {
        player.swingHand(hand, true);
        ItemStack heldItem = player.getStackInHand(hand);
        Item item = heldItem.getItem();
        player.incrementStat(Stats.USED.getOrCreateStat(item));
    }

    public void useShears(PlayerEntity player, ItemStack itemStack, Hand hand) {
        this.emitGameEvent(GameEvent.SHEAR, player);
        itemStack.damage(1, player, (playerx) -> {
            playerx.sendToolBreakStatus(hand);
        });
        swingHandAndIncrementStat(player,itemStack,hand);
    }



    //Fires when Strider is interacted with shears
    public ActionResult shearStrider(PlayerEntity player, ItemStack itemStack, Hand hand, SoundCategory soundCategory) {
        if (this.isShearable()) {
            this.sheared(soundCategory);
            useShears(player,itemStack,hand);
            if(player instanceof ServerPlayerEntity) {
                GunpowderRevision.SHEAR_STRIDER.trigger((ServerPlayerEntity) player);
            }
            return ActionResult.SUCCESS;
        } else if (!this.isShearable() && this.isSaddled() && player.isSneaking()) {
            this.removeSaddle(soundCategory);
            useShears(player,itemStack,hand);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    //Fires when Strider is interacted with magma cream
    public ActionResult creamStrider(PlayerEntity player, ItemStack itemStack, Hand hand, SoundCategory soundCategory) {
        if(this.hasHair() && (!this.isSaddled() || (this.isSaddled() && player.isSneaking()))) {
            this.styleStrider(SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.MOB_INTERACT, player);
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
            if (player instanceof ServerPlayerEntity) {
                GunpowderRevision.STYLE_STRIDER.trigger((ServerPlayerEntity) player);
            }
            swingHandAndIncrementStat(player,itemStack,hand);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    //Fires when Strider is interacted with Warped Roots
    public ActionResult feedStrider(PlayerEntity player, ItemStack itemStack, Hand hand, SoundCategory soundCategory) {
        if(!this.hasHair() && (!this.isSaddled() || (this.isSaddled() && player.isSneaking()))) {
            this.fedRoots(SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.MOB_INTERACT, player);
            if (!player.isCreative()) {
                itemStack.decrement(1);
            }
            swingHandAndIncrementStat(player,itemStack,hand);
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    public boolean isShearable()  {
        return this.isAlive() && this.hasHair() &&!this.isSaddled();
    }

    @Shadow public abstract boolean isCold();

    @Inject(method = "tick", at = @At("TAIL"),cancellable = true)
    public void growHair(CallbackInfo ci) {
        if(!this.hasHair() && !this.isCold()) {
            this.setHairGrowth(getHairGrowth()+1);
            if(getHairGrowth() >= 12000) {
                this.setHairGrowth(0);
                this.setHasHair(true);
            }
        }
    }

    static {
        HAIR_STATE = DataTracker.registerData(StriderEntityMixin.class, TrackedDataHandlerRegistry.BOOLEAN);
        HAIR_GROWTH = DataTracker.registerData(StriderEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);
        HAIR_STYLE = DataTracker.registerData(StriderEntityMixin.class, TrackedDataHandlerRegistry.INTEGER);
    }
}