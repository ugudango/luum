package net.ugudango.luum.setup;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.ugudango.luum.blocks.floor_loom.FloorLoomContainer;

import java.rmi.registry.RegistryHandler;

public class ModContainers {
    public static final RegistryObject<ContainerType<FloorLoomContainer>> FLOOR_LOOM_CONTAINER = Registration.CONTAINERS.register(
            "floor_loom",
            () -> IForgeContainerType.create((windowID, inv, data) -> {
                BlockPos blockPos = data.readBlockPos();
                World world = inv.player.getEntityWorld();
                return new FloorLoomContainer(windowID, world, blockPos, inv, inv.player);
            })
    );

    public static void register() { }
}
