package exter.foundry.material;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.api.material.IMaterialRegistry;
import exter.foundry.util.hashstack.HashableItem;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public final class MaterialRegistry implements IMaterialRegistry {

    private HashMap<HashableItem, String> materials;
    private HashMap<HashableItem, String> types;

    private Set<String> material_names;
    private Set<String> type_names;

    public static MaterialRegistry instance = new MaterialRegistry();

    @SideOnly(Side.CLIENT)
    private Map<String, ItemStack> material_icons;

    @SideOnly(Side.CLIENT)
    private Map<String, ItemStack> type_icons;

    private MaterialRegistry() {
        materials = new HashMap<HashableItem, String>();
        types = new HashMap<HashableItem, String>();
        material_names = new HashSet<String>();
        type_names = new HashSet<String>();
    }

    public void InitIcons() {
        material_icons = new HashMap<String, ItemStack>();
        type_icons = new HashMap<String, ItemStack>();
    }

    @Override
    public void RegisterItem(String oredict_name, String material, String type) {
        for (ItemStack item : OreDictionary.getOres(oredict_name)) {
            RegisterItem(item, material, type);
        }
    }

    @Override
    public void RegisterItem(ItemStack item, String material, String type) {
        HashableItem hs = new HashableItem(item);
        materials.put(hs, material);
        types.put(hs, type);
        material_names.add(material);
        type_names.add(type);
    }

    @Override
    public String GetMaterial(ItemStack item) {
        return materials.get(HashableItem.Cache(item));
    }

    @Override
    public String GetType(ItemStack item) {
        return types.get(HashableItem.Cache(item));
    }

    @Override
    public Set<String> GetMaterialNames() {
        return Collections.unmodifiableSet(material_names);
    }

    @Override
    public Set<String> GetTypeNames() {
        return Collections.unmodifiableSet(type_names);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void RegisterMaterialIcon(String material, ItemStack icon) {
        material_icons.put(material, icon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void RegisterTypeIcon(String type, ItemStack icon) {
        type_icons.put(type, icon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack GetMaterialIcon(String material) {
        return material_icons.get(material);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack GetTypeIcon(String type) {
        return type_icons.get(type);
    }
}
