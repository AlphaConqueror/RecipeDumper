package de.alphaconqueror.recipedumper.lib;

import cpw.mods.fml.common.Loader;
import de.alphaconqueror.recipedumper.RecipeDumper;
import de.alphaconqueror.recipedumper.modules.IRecipesModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;


public class RecipeManager {

    RecipeDumper recipeDumper;

    List<IRecipesModule> modules = new ArrayList<IRecipesModule>();

    public RecipeManager(final RecipeDumper rd) {
        this.recipeDumper = rd;
    }

    public void registerModule(final String modulePath) {
        try {
            final Class<?> moduleClass = Loader.instance().getModClassLoader().loadClass(modulePath);
            final IRecipesModule module = (IRecipesModule) moduleClass.getConstructor().newInstance();
            if (module.isEnabled()) {
				this.registerModule(module);
            } else {
                LogHelper.fine("Module " + modulePath + " is disabled");
            }
        } catch (final Exception ex) {
            LogHelper.severe("Exception raised during loading of recipe module: " + modulePath);
            ex.printStackTrace();
        }
    }

    public void registerModule(final IRecipesModule module) {
        if (module.isEnabled()) {
			this.modules.add(module);
        } else {
            LogHelper.fine("Module " + module.getPrefix() + " is disabled");
        }
    }

    public void cmdDumpAll() {
        LogHelper.info("Dumping everything");
		this.dumpRecipes();
		this.dumpOreDictionary();
        // dumpIdMap();
		this.dumpItems();
    }

    public void cmdDumpOreDict() {
        LogHelper.info("Dumping ore dictionary");
		this.dumpOreDictionary();
        // dumpIdMap();
		this.dumpItems();
    }

    public void cmdDumpItemRecipes() {
        LogHelper.info("Dumping recipes");
		this.dumpRecipes();
        // dumpIdMap();
		this.dumpItems();
    }

    public void cmdDumpFluids() {
		this.dumpFluidRegistry();
    }


    private void dumpItems() {
        LogHelper.fine("Dumping items");
        String description;
        try {

            final File fp = Configuration.createIDFile();
            final Writer itemWriter = new FileWriter(fp);

            for (final Entry<Integer, Integer> itemEntry : IDHandler.getEntries()) {
                try {
                    description = Formatter.getISDumpString(itemEntry.getKey(), itemEntry.getValue());
                    itemWriter.write(description);
                    itemWriter.write("\n");
                } catch (final Exception ex) {
                    LogHelper.severe("Exception caught when writing item "
                            + itemEntry.getKey() + ":" + itemEntry.getValue());
                    ex.printStackTrace();
                }
            }

            itemWriter.close();
        } catch (final Exception ex) {
            LogHelper.severe("Exception caught during dumping item ids");
        }
    }

    private void dumpIdMap() {
        LogHelper.fine("Dumping item map");
        String description;
        try {

            final File fp = Configuration.createIdMapFile();
            final Writer mapWriter = new FileWriter(fp);

            for (final Entry<Integer, Integer> itemEntry : IDHandler.getEntries()) {
                try {
                    description = String.format("%s:%s", itemEntry.getKey(), itemEntry.getValue());
                    mapWriter.write(description);
                    mapWriter.write("\n");
                } catch (final Exception ex) {
                    LogHelper.severe("Exception caught when writing item");
                }
            }

            mapWriter.close();
        } catch (final Exception ex) {
            LogHelper.severe("Exception caught during dumping item ids");
        }
    }

    private void dumpRecipes() {
        LogHelper.fine("Dumping recipes");
        int unproc = 0;
        int proc = 0;
        try {
            final File fp = Configuration.createDumpFile();
            final Writer recipeWriter = new FileWriter(fp);

            for (final IRecipesModule module : this.modules) {
                for (final IRecipesModule.IRecipeData recipe : module.getAllRecipes()) {
                    try {
                        recipeWriter.write(recipe.generateDescription());
                        recipeWriter.write("\n");
                        proc++;
                    } catch (final Exception ex) {
                        // LogHelper.info("Cannot describe an object in module "+module.getPrefix());
                        // ex.printStackTrace();
                        unproc++;
                    }
                }
            }

            LogHelper.info("Processed " + proc + "/" + unproc);
            recipeWriter.close();
        } catch (final Exception ex) {
            LogHelper.severe("Exception raised during recipe dumping");
            ex.printStackTrace();
        }
    }

    private void dumpOreDictionary() {
        LogHelper.fine("Dumping ore dictionary...");
        StringBuilder description;
        try {

            final File fp = Configuration.createOreFile();
            final Writer oreWriter = new FileWriter(fp);

            for (final String oreName : OreDictionary.getOreNames()) {

                description = new StringBuilder(String.format("recipedumper:oredict!@%s->", oreName));

                for (final ItemStack is : OreDictionary.getOres(oreName)) {
                    IDHandler.updateMap(is);
                    description.append(Formatter.getISDescription(is));
                }

                oreWriter.write(description + "\n");
            }
            oreWriter.close();
        } catch (final Exception ex) {
            LogHelper.severe("Exception during dumping ore dictionary");
            ex.printStackTrace();
        }
    }

    private void dumpFluidRegistry() {
        LogHelper.fine("Dumping fluid registry...");
        final String fluidFmt = "recipedumper:fluid!@%s->(%d:%d)(%d:%d:%d:%d)(%s) U=%s||L=%s";

        try {

            final File fp = Configuration.createFluidFile();
            final Writer fluidWriter = new FileWriter(fp);

            for (final Entry<String, Fluid> entryFluid : FluidRegistry.getRegisteredFluids().entrySet()) {
                final Fluid fluidSample = entryFluid.getValue();
                final String formattedFluid = String.format(fluidFmt,
                        fluidSample.getName(),
                        fluidSample.getID(),
                        fluidSample.getColor(),
                        fluidSample.getDensity(),
                        fluidSample.getLuminosity(),
                        fluidSample.getTemperature(),
                        fluidSample.getViscosity(),
                        Formatter.getBooleanString(fluidSample.isGaseous()),
                        fluidSample.getUnlocalizedName(),
                        fluidSample.getLocalizedName());
                fluidWriter.write(formattedFluid + "\n");
            }
            fluidWriter.close();
        } catch (final Exception ex) {
            LogHelper.severe("Exception during dumping fluid registry");
            ex.printStackTrace();
        }
    }
}
