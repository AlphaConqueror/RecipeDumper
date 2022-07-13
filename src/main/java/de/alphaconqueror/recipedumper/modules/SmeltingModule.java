package de.alphaconqueror.recipedumper.modules;

import de.alphaconqueror.recipedumper.lib.Formatter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;
import java.util.Map;

public class SmeltingModule implements IRecipesModule {

    @Override
    public IRecipeData[] getAllRecipes() {
        final ArrayList<IRecipeData> list = new ArrayList<IRecipeData>();
        final Map map = FurnaceRecipes.smelting().getSmeltingList();

        for (final Object key : map.keySet()) {
            final ItemStack source = (ItemStack) key;
            final ItemStack result = (ItemStack) map.get(key);

            list.add(new RecipeData(source, result));
        }

        return list.toArray(new IRecipeData[0]);
    }

    @Override
    public String getPrefix() {
        return "recipedumper:furnace";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public class RecipeData implements IRecipeData {
        String description = null;
        ItemStack source;
        ItemStack target;

        public RecipeData(final ItemStack source, final ItemStack target) {
            this.source = source;
            this.target = target;
        }

        @Override
        public String generateDescription() {
            if (this.description != null) {
                return this.description;
            }
            this.description = SmeltingModule.this.getPrefix() + "!";
            this.description += Formatter.getISDescription(this.source);
            this.description += "->" + Formatter.getISDescription(this.target);

            return this.description;
        }

    }
}
