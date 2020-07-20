package org.dimdev.dimdoors.command;

import net.fabricmc.fabric.api.registry.CommandRegistry;

public class ModCommands {
    public static void init() {
        CommandRegistry.INSTANCE.register(false, DimTeleportCommand::register);
        CommandRegistry.INSTANCE.register(false, SchematicCommand::register);
    }
}
