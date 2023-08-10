package de.alphaconqueror.recipedumper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import de.alphaconqueror.recipedumper.exportrecipes.ExportRecipesCommand;

@Mod(modid = "recipedumper")
public class RecipeDumper {

    @Mod.EventHandler
    public void onServerStarting(final FMLServerStartingEvent event) {
        event.registerServerCommand(new ExportRecipesCommand());
    }
}
