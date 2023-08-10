package de.alphaconqueror.recipedumper.exportrecipes;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import net.minecraft.item.ItemStack;

public class ExportRecipe {
    @Getter
    private final String key;
    @Getter
    private final int metadata;
    @Getter
    private final int amountCrafted;
    @Getter
    private final List<ExportPermutationIngredient> permutationIngredients;

    public ExportRecipe(final ItemStack itemStack) {
        this.key = itemStack.getItem().delegate.name();
        this.metadata = itemStack.getMetadata();
        this.amountCrafted = itemStack.stackSize;
        this.permutationIngredients = new ArrayList<>();
    }
}
