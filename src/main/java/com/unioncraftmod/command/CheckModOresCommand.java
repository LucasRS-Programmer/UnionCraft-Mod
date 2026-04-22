package com.unioncraftmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.unioncraftmod.util.ModTags;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.List;

public class CheckModOresCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("checkmodores")

                .requires(source ->
                        source.hasPermission(2) &&
                                source.getEntity() instanceof net.minecraft.server.level.ServerPlayer player &&
                                (player.isCreative() || player.isSpectator())
                )

                .executes(context -> {

                    ServerLevel level = context.getSource().getLevel();
                    BlockPos center = BlockPos.containing(context.getSource().getPosition());

                    int chunkX = center.getX() >> 4;
                    int chunkZ = center.getZ() >> 4;

                    int startX = chunkX << 4;
                    int startZ = chunkZ << 4;

                    int oreCount = 0;
                    List<BlockPos> orePositions = new ArrayList<>();

                    // Mutable para performance
                    BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

                    for (int x = startX; x < startX + 16; x++) {
                        for (int z = startZ; z < startZ + 16; z++) {

                            for (int y = -64; y <= 32; y++) {

                                pos.set(x, y, z);
                                var state = level.getBlockState(pos);

                                if (state.is(ModTags.Blocks.MOD_ORE)) {
                                    oreCount++;
                                    orePositions.add(pos.immutable());
                                }
                            }
                        }
                    }

                    // Coordenadas (máx 5)
                    StringBuilder coords = new StringBuilder();
                    for (int i = 0; i < Math.min(5, orePositions.size()); i++) {
                        BlockPos p = orePositions.get(i);
                        coords.append("\n(")
                                .append(p.getX()).append(", ")
                                .append(p.getY()).append(", ")
                                .append(p.getZ()).append(")");
                    }

                    final Component message = Component.literal(
                            "Chunk [" + chunkX + ", " + chunkZ + "]\n" +
                                    "Minérios (MOD_ORE): " + oreCount + " (primeiros 5)" +
                                    coords
                    );

                    context.getSource().sendSuccess(() -> message, false);

                    return 1;
                }));
    }
}