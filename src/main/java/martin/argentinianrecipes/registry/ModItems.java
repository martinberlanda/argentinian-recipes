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

import net.minecraft.world.food.Foods;
import net.minecraft.world.item.component.Consumables;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

public final class ModItems {

    private ModItems() {
    }

    public static final Item FAT = register("fat", food(1, 0.2f));
    public static final Item CHORIPAN = register("choripan", food(9, 0.8f));
    public static final Item TORTA_FRITA = register("torta_frita", food(5, 0.6f));
    public static final Item RAW_SAUSAGE = register("raw_sausage", food(2, 0.2f));
    public static final Item COOKED_SAUSAGE = register("cooked_sausage", food(8, 0.8f));
    public static final Item DOUGH = register("dough", Item::new);
    public static final Item RAW_EMPANADA = register("raw_empanada", food(2, 0.2f));
    public static final Item EMPANADA = register("empanada", food(8, 0.7f));

    public static final Item DULCE_DE_LECHE = register(
            "dulce_de_leche",
            props -> new Item(
                    props
                            .food(Foods.HONEY_BOTTLE, Consumables.HONEY_BOTTLE)
                            .usingConvertsTo(GLASS_BOTTLE)
                            .stacksTo(16)
            )
    );

    public static void register() {
        addToGroup(CreativeModeTabs.INGREDIENTS,DOUGH);

        addToGroup(
                CreativeModeTabs.FOOD_AND_DRINKS,
                FAT,
                RAW_SAUSAGE,
                COOKED_SAUSAGE,
                CHORIPAN,
                TORTA_FRITA,
                RAW_EMPANADA,
                EMPANADA,
                DULCE_DE_LECHE
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
