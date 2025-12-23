package martin.argentinianrecipes.registry;

import java.util.function.Function;

import martin.argentinianrecipes.ArgentinianRecipes;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

public final class ModItems {

    private ModItems() {
    }

    public static final Item FAT = register("fat", food(1, 0.2f));
    public static final Item CHORIPAN = register("choripan", food(9, 0.8f));
    public static final Item TORTA_FRITA = register("torta_frita", food(6, 0.7f));
    public static final Item RAW_SAUSAGE_BEEF = register("raw_sausage_beef", food(2, 0.2f));
    public static final Item COOKED_SAUSAGE_BEEF = register("cooked_sausage_beef", food(8, 0.8f));
    public static final Item RAW_SAUSAGE_PORK = register("raw_sausage_pork", food(2, 0.2f));
    public static final Item COOKED_SAUSAGE_PORK = register("cooked_sausage_pork", food(8, 0.8f));
    public static final Item RAW_SAUSAGE_LAMB = register("raw_sausage_lamb", food(2, 0.2f));
    public static final Item COOKED_SAUSAGE_LAMB = register("cooked_sausage_lamb", food(8, 0.8f));
    public static final Item RAW_SAUSAGE_CHICKEN = register("raw_sausage_chicken", food(2, 0.2f));
    public static final Item COOKED_SAUSAGE_CHICKEN = register("cooked_sausage_chicken", food(7, 0.7f));

    public static void register() {
        addToGroup(CreativeModeTabs.INGREDIENTS, FAT);

        addToGroup(
                CreativeModeTabs.FOOD_AND_DRINKS,
                RAW_SAUSAGE_BEEF,
                COOKED_SAUSAGE_BEEF,
                RAW_SAUSAGE_PORK,
                COOKED_SAUSAGE_PORK,
                RAW_SAUSAGE_LAMB,
                COOKED_SAUSAGE_LAMB,
                RAW_SAUSAGE_CHICKEN,
                COOKED_SAUSAGE_CHICKEN,
                CHORIPAN,
                TORTA_FRITA
        );
    }

    private static Item register(String path, Function<Item.Properties, Item> factory) {
        Identifier id = Identifier.fromNamespaceAndPath(
                ArgentinianRecipes.MOD_ID,
                path);

        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);

        Item.Properties properties = new Item.Properties().setId(key);

        Item item = factory.apply(properties);

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    private static Function<Item.Properties, Item> food(int nutrition, float saturation) {
        FoodProperties food = new FoodProperties.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturation)
                .build();

        return props -> new Item(
                props.food(food));
    }

    private static void addToGroup(
            ResourceKey<CreativeModeTab> group,
            Item... items) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> {
            for (Item item : items) {
                entries.accept(item);
            }
        });
    }
}
