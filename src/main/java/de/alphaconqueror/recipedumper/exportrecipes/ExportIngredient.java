package de.alphaconqueror.recipedumper.exportrecipes;

import lombok.Getter;
import net.minecraft.item.ItemStack;

public class ExportIngredient {

    @Getter
    private final String item;
    @Getter
    private final int metadata;
    @Getter
    private final int amount;

    public ExportIngredient(final ItemStack itemStack) {
        this.item = itemStack.getItem().delegate.name();
        this.metadata = itemStack.getMetadata();
        this.amount = itemStack.stackSize;
    }
}
