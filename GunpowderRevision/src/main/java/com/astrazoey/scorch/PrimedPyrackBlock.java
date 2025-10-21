package com.astrazoey.scorch;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.tick.OrderedTick;

import java.util.Random;

public class PrimedPyrackBlock extends PyrackBlock {

    public PrimedPyrackBlock(Settings settings) {super (settings); }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        //1.17
        //world.getBlockTickScheduler().schedule(pos, this, 3);

        //1.18
        OrderedTick<Block> orderedTick = new OrderedTick<Block>(this, pos, 3,0);
        world.getBlockTickScheduler().scheduleTick(orderedTick);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.removeBlock(pos, false);
        detonate(world, pos);
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {
        //empty
    }



}
