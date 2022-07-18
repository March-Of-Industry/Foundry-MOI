package exter.foundry.registry;

import exter.foundry.api.registry.IItemRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.ItemStack;

@Deprecated
public class ItemRegistry implements IItemRegistry {
    private Map<String, ItemStack> items;
    public static ItemRegistry instance = new ItemRegistry();

    @Deprecated
    private ItemRegistry() {
        items = new HashMap<String, ItemStack>();
    }

    @Override
    @Deprecated
    public ItemStack GetItem(String name) {
        return items.get(name).copy();
    }

    @Deprecated
    public void RegisterItem(String name, ItemStack item) {
        items.put(name, item);
    }
}
