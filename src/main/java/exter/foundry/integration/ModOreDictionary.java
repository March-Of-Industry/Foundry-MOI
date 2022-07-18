package exter.foundry.integration;


import exter.foundry.item.FoundryItems;
import exter.foundry.item.ItemComponent;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDictionary
{
    public static void init()
    {
        OreDictionary.registerOre("dustSmallGunpowder", FoundryItems.Component(ItemComponent.COMPONENT_GUNPOWDER_SMALL));
        OreDictionary.registerOre("dustSmallBlaze", FoundryItems.Component(ItemComponent.COMPONENT_BLAZEPOWDER_SMALL));
        OreDictionary.registerOre("dustZinc", FoundryItems.Component(ItemComponent.COMPONENT_DUST_ZINC));
        OreDictionary.registerOre("dustBrass", FoundryItems.Component(ItemComponent.COMPONENT_DUST_BRASS));
        OreDictionary.registerOre("dustCupronickel", FoundryItems.Component(ItemComponent.COMPONENT_DUST_CUPRONICKEL));

    }

}
