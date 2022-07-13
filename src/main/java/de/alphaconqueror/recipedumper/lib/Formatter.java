package de.alphaconqueror.recipedumper.lib;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class Formatter {
    public static String getISDescription(final ItemStack is) {
        if (is == null) {
            return "(None)";
        } else {
            IDHandler.updateMap(is);
            return String.format("(%d:%d,%d)", Item.getIdFromItem(is.getItem()), is.getItemDamage(), is.stackSize);
        }
    }

    public static String getItemOreDescription(final Object obj) {
        final ArrayList<ItemStack> aList = (ArrayList<ItemStack>) obj;
        final ItemStack is = aList.get(0);
        String outputName = null;
        for (final String oreName : OreDictionary.getOreNames()) {
            for (final ItemStack item : OreDictionary.getOres(oreName)) {
                if (Item.getIdFromItem(item.getItem()) == Item.getIdFromItem(is.getItem()) && item.getItemDamage() == is.getItemDamage()) {
                    outputName = oreName;
                }
            }
        }
        return String.format("(@%s,%d)", outputName, is.stackSize);
    }

    @SuppressWarnings("unused")
    public static String getISDumpString(final int itemId, final int meta) {
        final ItemStack is = new ItemStack(Item.getItemById(itemId), 1, meta);

        return String.format("recipedumper:item!%d:%d U=%s||L=%s", Item.getIdFromItem(is.getItem()), is.getItemDamage(), getUnlocalizedName(is), getDisplayName(is));
    }

    public static String getWHString(final int width, final int height) {
        return String.format("(w=%d,h=%d)", width, height);
    }

    private static String getUnlocalizedName(final ItemStack is) {
        String out = "None";
        try {
            out = is.getUnlocalizedName();
        } catch (final Exception ex) {
            //
        }
        return out;
    }

    private static String getDisplayName(final ItemStack is) {
        String out = "None";
        try {
            out = is.getDisplayName();
        } catch (final Exception ex) {
            //
        }
        return out;
    }

    public static String getBooleanString(final boolean value) {
        return value ? "True" : "False";
    }

}
