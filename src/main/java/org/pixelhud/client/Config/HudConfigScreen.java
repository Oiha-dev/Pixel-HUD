package org.pixelhud.client.Config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class HudConfigScreen {

    private static int xCoordinate = 1;
    private static int yCoordinate = 1;

    public static Screen getConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/oak_planks.png"))
                .setTitle(new TranslatableText("config.PixelHud.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory coordinate = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.coordinate"));

        coordinate.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.coordinate.x"), xCoordinate)
                .setDefaultValue(1)
                .setSaveConsumer(x_coo -> xCoordinate = x_coo)
                .build());

        coordinate.addEntry(entryBuilder.startIntField(new TranslatableText("config.PixelHud.coordinate.y"), yCoordinate)
                .setDefaultValue(1)
                .setSaveConsumer(y_coo -> yCoordinate = y_coo)
                .build());


        builder.setSavingRunnable(() -> {
            System.out.println("GG");
        });

        return builder.build();
    }
}
