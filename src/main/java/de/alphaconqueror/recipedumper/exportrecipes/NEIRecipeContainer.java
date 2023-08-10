package de.alphaconqueror.recipedumper.exportrecipes;

import codechicken.nei.PositionedStack;
import java.util.List;

public class NEIRecipeContainer {

    private final List<PositionedStack> ingredients;
    private final PositionedStack result;

    public NEIRecipeContainer(final List<PositionedStack> ingredients,
            final PositionedStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public List<PositionedStack> getIngredients() {
        return this.ingredients;
    }

    public PositionedStack getResult() {
        return this.result;
    }
}
