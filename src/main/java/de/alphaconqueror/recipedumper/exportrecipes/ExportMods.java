package de.alphaconqueror.recipedumper.exportrecipes;

import java.util.ArrayList;
import lombok.Getter;

public class ExportMods {
    @Getter
    private final String mod;
    @Getter
    private final ArrayList<ExportRecipe> exportRecipes;

    public ExportMods(final String key) {
        this.mod = key;
        this.exportRecipes = new ArrayList<>();
    }
}
