package de.alphaconqueror.recipedumper.exportrecipes;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ExportPermutationIngredient {

    @Getter
    private final List<ExportIngredient> exportIngredients;

    public ExportPermutationIngredient() {
        this.exportIngredients = new ArrayList<>();
    }
}
