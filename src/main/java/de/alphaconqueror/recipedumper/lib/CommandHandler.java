package de.alphaconqueror.recipedumper.lib;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommandHandler {

    public static void initCommand() {
        final CommandRPDumper commandRPDumper = new CommandRPDumper();

        FMLCommonHandler.instance().bus().register(commandRPDumper);
        MinecraftForge.EVENT_BUS.register(commandRPDumper);
        ClientCommandHandler.instance.registerCommand(commandRPDumper);
    }
}
