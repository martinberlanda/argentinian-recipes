package martin.argentinianrecipes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import martin.argentinianrecipes.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ArgentinianRecipes implements ModInitializer {
    public static final String MOD_ID = "argentinian-recipes";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        ModItems.register();
        registerMobLoot();
        LOGGER.info("Argentinian Recipes loaded");
    }

    private void registerMobLoot() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) return;

            String keyStr = key.toString();
            if (keyStr.contains("minecraft:entities/cow")
                    || keyStr.contains("minecraft:entities/pig")
                    || keyStr.contains("minecraft:entities/sheep")) {

                tableBuilder.pool(fatPool());
            }
        });
    }

    private LootPool fatPool() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(
                        LootItem.lootTableItem(ModItems.FAT)
                                .apply(SetItemCountFunction.setCount(
                                        UniformGenerator.between(1, 3)
                                ))
                )
                .build();
    }
}