package de.alphaconqueror.recipedumper.exportrecipes;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.ICraftingHandler;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;

public class ExportRecipesCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "export";
    }

    @Override
    public String getCommandUsage(final ICommandSender iCommandSender) {
        return null;
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] strings) {
        sender.addChatMessage(new ChatComponentText("§eStarting recipe export ..."));

        final List<ExportMods> modRecipes = new ArrayList<>();

        for (final Object key : Item.itemRegistry.getKeys()) {
            final String itemName = (String) key;
            final Item item = (Item) Item.itemRegistry.getObject(key);
            final List<ItemStack> subItems = new ArrayList<>();
            final ArrayList<ICraftingHandler> handlers = new ArrayList<>();

            item.getSubItems(item, CreativeTabs.tabAllSearch, subItems);

            for (final ICraftingHandler craftingHandler : GuiCraftingRecipe.craftinghandlers) {
                try {
                    final ICraftingHandler handler = craftingHandler.getRecipeHandler("item",
                            subItems.toArray(new Object[0]));

                    if (handler.numRecipes() > 0) {
                        handlers.add(handler);
                    }
                } catch (final Exception ignored) {}
            }

            final List<NEIRecipeContainer> recipes = new ArrayList<>();

            for (final ICraftingHandler craftingHandler : handlers) {
                for (int i = 0; i < craftingHandler.numRecipes(); i++) {
                    final PositionedStack result = craftingHandler.getResultStack(i);

                    if (result == null) {
                        continue;
                    }

                    recipes.add(new NEIRecipeContainer(craftingHandler.getIngredientStacks(i),
                            craftingHandler.getResultStack(i)));
                }
            }

            final String modNamespace = itemName.split(":")[0];

            if (modRecipes.stream().noneMatch(mod -> mod.getMod().equals(modNamespace))) {
                modRecipes.add(new ExportMods(modNamespace));
            }

            final ExportMods mod = modRecipes.stream()
                    .filter(exportMods -> exportMods.getMod().equals(modNamespace)).findFirst()
                    .orElse(null);

            if (mod == null) {
                continue;
            }


            for (final NEIRecipeContainer neiRecipeContainer : recipes) {
                final ExportRecipe exportRecipe =
                        new ExportRecipe(neiRecipeContainer.getResult().items[0]);

                for (final PositionedStack ingredient : neiRecipeContainer.getIngredients()) {
                    ingredient.generatePermutations();

                    final ExportPermutationIngredient permutationIngredient =
                            new ExportPermutationIngredient();

                    for (final ItemStack itemStack : ingredient.items) {
                        if (itemStack == null || itemStack.getItem() == null) {
                            continue;
                        }

                        final String ingredientKey = itemStack.getItem().delegate.name();

                        if (ingredientKey == null) {
                            continue;
                        }

                        permutationIngredient.getExportIngredients()
                                .add(new ExportIngredient(itemStack));
                    }

                    if (!permutationIngredient.getExportIngredients().isEmpty()) {
                        exportRecipe.getPermutationIngredients().add(permutationIngredient);
                    }
                }

                if (!exportRecipe.getPermutationIngredients().isEmpty()) {
                    mod.getExportRecipes().add(exportRecipe);
                }
            }
        }

        try {
            final FileWriter writer = new FileWriter("exported_recipes.json");
            final Gson gson = new Gson();
            gson.toJson(modRecipes, writer);
            writer.close();
            sender.addChatMessage(new ChatComponentText("§aExport completed!"));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
