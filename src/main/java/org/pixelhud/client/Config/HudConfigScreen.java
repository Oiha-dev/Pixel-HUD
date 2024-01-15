package org.pixelhud.client.Config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
public class HudConfigScreen{

    static int a = 1;
    public static Screen getConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/bee_nest_side.png"))
                .setTitle(new TranslatableText("config.PixelHud.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.coordinate"));
        general.addEntry(entryBuilder.startIntField(new TranslatableText("config.PixelHud.coordinate.x"), currentValue)
                .setDefaultValue(1)
                .setSaveConsumer(x_coo -> currentValue = x_coo)
                .build());
        general.addEntry(entryBuilder.startIntField(new TranslatableText("config.PixelHud.coordinate.y"), currentValue)
                .setDefaultValue(1)
                .setSaveConsumer(y_coo -> currentValue = y_coo)
                .build());


        builder.setSavingRunnable(() -> {
            System.out.println("GG");
        });

        return builder.build();
    }
}
