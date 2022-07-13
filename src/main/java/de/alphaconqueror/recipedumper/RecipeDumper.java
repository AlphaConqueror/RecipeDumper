package de.alphaconqueror.recipedumper;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import de.alphaconqueror.recipedumper.lib.CommandHandler;
import de.alphaconqueror.recipedumper.lib.RecipeManager;
import de.alphaconqueror.recipedumper.lib.Reference;
import de.alphaconqueror.recipedumper.modules.*;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class RecipeDumper {

    @Instance(Reference.MOD_ID)
    public static RecipeDumper instance;
    public RecipeManager recipemanager;

    public RecipeDumper() {
        this.recipemanager = new RecipeManager(this);
    }

    @EventHandler
    public void load(final FMLInitializationEvent ignored) {
        CommandHandler.initCommand();
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent ignored) {
        this.recipemanager.registerModule(new ShapedRecipesModule());
        this.recipemanager.registerModule(new ShapelessModule());
        this.recipemanager.registerModule(new ShapedOreModule());
        this.recipemanager.registerModule(new ShapelessOreModule());
        this.recipemanager.registerModule(new SmeltingModule());
    }
}
