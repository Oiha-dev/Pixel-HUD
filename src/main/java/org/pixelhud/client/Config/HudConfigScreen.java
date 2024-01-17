package org.pixelhud.client.Config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

@Config(name = "pixelhud")
public class HudConfigScreen{

    public static int playerCoordTextColor = 0xFFFFFFFF;
    private static int playerCoordBackgroundColor = -2147483648;
    public static int playerCoordXPercentage = 50;
    private static int playerCoordYPercentage = 50;

    private static int armorItemTextColor = 0xFFFFFFFF;
    private static int armorItemBackgroundColor = -2147483648;
    private static int armorItemXPercentage = 50;
    private static int armorItemYPercentage = 50;

    private static int elytraTextColor = 0xFFFFFFFF;
    private static int elytraBackgroundColor = -2147483648;
    private static int elytraXPercentage = 50;
    private static int elytraYPercentage = 50;

    private static int timeDisplayTextColor = 0xFFFFFFFF;
    private static int timeDisplayBackgroundColor = -2147483648;
    private static int timeDisplayXPercentage = 50;
    private static int timeDisplayYPercentage = 50;

    private static int fpsDisplayTextColor = 0xFFFFFFFF;
    private static int fpsDisplayBackgroundColor = -2147483648;
    private static int fpsDisplayXPercentage = 50;
    private static int fpsDisplayYPercentage = 50;

    public static Screen getConfigScreen(Screen parentScreen){
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/oak_planks.png"))
                .setTitle(new TranslatableText("config.PixelHud.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // About
        ConfigCategory about = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.about"));
        about.addEntry(
                entryBuilder.startTextDescription(new LiteralText("Thank you for using Pixel-HUD! \nHello, Minecraft community! I'm a passionate developer crafting Pixel-HUD to enhance your minecraft adventures. As a small dev, your support is invaluable. If you enjoy Pixel-HUD and want to back my efforts, consider buying me a coffee on Ko-fi. Your generosity fuels ongoing development. Happy gaming!\n \n").append(new LiteralText("Support me on Ko-fi:").setStyle(Style.EMPTY
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://ko-fi.com/oiha_dev"))
                                .withColor(Formatting.AQUA).withBold(Boolean.TRUE).withUnderline(Boolean.TRUE)))).build());







        // Player Coordinates
        ConfigCategory playerCoord = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.playerCoordinates"));
        playerCoord.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.playerCoordinates.xPercentage"), playerCoordXPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> playerCoordXPercentage = xPercent)
                .build());

        playerCoord.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.playerCoordinates.yPercentage"), playerCoordYPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(yPercent -> playerCoordYPercentage = yPercent)
                .build());

        playerCoord.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.playerCoordinates.textColor"), playerCoordTextColor)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(text_Color -> playerCoordTextColor = text_Color)
                .build());

        playerCoord.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.playerCoordinates.backgroundColor"), playerCoordBackgroundColor)
                .setDefaultValue(-2147483648)
                .setSaveConsumer(back_Color -> playerCoordBackgroundColor = back_Color)
                .build());



        // Armor and Item
        ConfigCategory armorItem = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.armorItem"));
        armorItem.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.armorItem.xPercentage"), armorItemXPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> armorItemXPercentage = xPercent)
                .build());

        armorItem.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.armorItem.yPercentage"), armorItemYPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(yPercent -> armorItemYPercentage = yPercent)
                .build());

        armorItem.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.armorItem.textColor"), armorItemTextColor)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(text_Color -> armorItemTextColor = text_Color)
                .build());

        armorItem.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.armorItem.backgroundColor"), armorItemBackgroundColor)
                .setDefaultValue(-2147483648)
                .setSaveConsumer(back_Color -> armorItemBackgroundColor = back_Color)
                .build());



        // Elytra
        ConfigCategory elytra = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.elytra"));
        elytra.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.elytra.xPercentage"), elytraXPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> elytraXPercentage = xPercent)
                .build());

        elytra.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.elytra.yPercentage"), elytraYPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(yPercent -> elytraYPercentage = yPercent)
                .build());

        elytra.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.elytra.textColor"), elytraTextColor)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(text_Color -> elytraTextColor = text_Color)
                .build());

        elytra.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.elytra.backgroundColor"), elytraBackgroundColor)
                .setDefaultValue(-2147483648)
                .setSaveConsumer(back_Color -> elytraBackgroundColor = back_Color)
                .build());



        // Time Display
        ConfigCategory timeDisplay = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.timeDisplay"));
        timeDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.timeDisplay.xPercentage"), timeDisplayXPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> timeDisplayXPercentage = xPercent)
                .build());

        timeDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.timeDisplay.yPercentage"), timeDisplayYPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(yPercent -> timeDisplayYPercentage = yPercent)
                .build());

        timeDisplay.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.timeDisplay.textColor"), timeDisplayTextColor)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(text_Color -> timeDisplayTextColor = text_Color)
                .build());

        timeDisplay.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.timeDisplay.backgroundColor"), timeDisplayBackgroundColor)
                .setDefaultValue(-2147483648)
                .setSaveConsumer(back_Color -> timeDisplayBackgroundColor = back_Color)
                .build());



        // FPS Display
        ConfigCategory fpsDisplay = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.fpsDisplay"));
        fpsDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.fpsDisplay.xPercentage"), fpsDisplayXPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> fpsDisplayXPercentage = xPercent)
                .build());

        fpsDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.fpsDisplay.yPercentage"), fpsDisplayYPercentage, 1, 100)
                .setDefaultValue(50)
                .setSaveConsumer(yPercent -> fpsDisplayYPercentage = yPercent)
                .build());

        fpsDisplay.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.fpsDisplay.textColor"), fpsDisplayTextColor)
                .setDefaultValue(0xFFFFFFFF)
                .setSaveConsumer(text_Color -> fpsDisplayTextColor = text_Color)
                .build());

        fpsDisplay.addEntry(entryBuilder.startAlphaColorField(new TranslatableText("config.PixelHud.fpsDisplay.backgroundColor"), fpsDisplayBackgroundColor)
                .setDefaultValue(-2147483648)
                .setSaveConsumer(back_Color -> fpsDisplayBackgroundColor = back_Color)
                .build());

        builder.setSavingRunnable(() -> System.out.println(fpsDisplayTextColor));

        return builder.build();
    }
}
