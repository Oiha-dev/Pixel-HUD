package org.pixelhud.client.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Config(name = "pixelhud")
public class HudConfigScreen {

    private static final Path CONFIG_FILE = MinecraftClient.getInstance().runDirectory.toPath().resolve("config/pixelhud.json");

    public static void loadConfig() {
        try {
            if (Files.exists(CONFIG_FILE)) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(new String(Files.readAllBytes(CONFIG_FILE)), JsonObject.class);
                loadConfigValues(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadConfigValues(JsonObject json) {
        playerCoordXPercentage = json.getAsJsonPrimitive("playerCoordXPercentage").getAsInt();
        playerCoordYPercentage = json.getAsJsonPrimitive("playerCoordYPercentage").getAsInt();
        playerCoordTextColor = json.getAsJsonPrimitive("playerCoordTextColor").getAsInt();
        playerCoordBackgroundColor = json.getAsJsonPrimitive("playerCoordBackgroundColor").getAsInt();

        armorItemXPercentage = json.getAsJsonPrimitive("armorItemXPercentage").getAsInt();
        armorItemYPercentage = json.getAsJsonPrimitive("armorItemYPercentage").getAsInt();
        armorItemTextColor = json.getAsJsonPrimitive("armorItemTextColor").getAsInt();
        armorItemBackgroundColor = json.getAsJsonPrimitive("armorItemBackgroundColor").getAsInt();

        elytraXPercentage = json.getAsJsonPrimitive("elytraXPercentage").getAsInt();
        elytraYPercentage = json.getAsJsonPrimitive("elytraYPercentage").getAsInt();
        elytraTextColor = json.getAsJsonPrimitive("elytraTextColor").getAsInt();
        elytraBackgroundColor = json.getAsJsonPrimitive("elytraBackgroundColor").getAsInt();

        timeDisplayXPercentage = json.getAsJsonPrimitive("timeDisplayXPercentage").getAsInt();
        timeDisplayYPercentage = json.getAsJsonPrimitive("timeDisplayYPercentage").getAsInt();
        timeDisplayTextColor = json.getAsJsonPrimitive("timeDisplayTextColor").getAsInt();
        timeDisplayBackgroundColor = json.getAsJsonPrimitive("timeDisplayBackgroundColor").getAsInt();

        fpsDisplayXPercentage = json.getAsJsonPrimitive("fpsDisplayXPercentage").getAsInt();
        fpsDisplayYPercentage = json.getAsJsonPrimitive("fpsDisplayYPercentage").getAsInt();
        fpsDisplayTextColor = json.getAsJsonPrimitive("fpsDisplayTextColor").getAsInt();
        fpsDisplayBackgroundColor = json.getAsJsonPrimitive("fpsDisplayBackgroundColor").getAsInt();
    }

    public static int playerCoordTextColor = 0xFFFFFFFF;
    public static int playerCoordBackgroundColor = -2147483648;
    public static int playerCoordXPercentage = 1;
    public static int playerCoordYPercentage = 2;

    public static int armorItemTextColor = 0xFFFFFFFF;
    public static int armorItemBackgroundColor = -2147483648;
    public static int armorItemXPercentage = 1;
    public static int armorItemYPercentage = 40;

    public static int elytraTextColor = 0xFFFFFFFF;
    public static int elytraBackgroundColor = -2147483648;
    public static int elytraXPercentage = 10;
    public static int elytraYPercentage = 8;

    public static int timeDisplayTextColor = 0xFFFFFFFF;
    public static int timeDisplayBackgroundColor = -2147483648;
    public static int timeDisplayXPercentage = 10;
    public static int timeDisplayYPercentage = 2;

    public static int fpsDisplayTextColor = 0xFFFFFFFF;
    public static int fpsDisplayBackgroundColor = -2147483648;
    public static int fpsDisplayXPercentage = 100;
    public static int fpsDisplayYPercentage = 1;



    private static void saveConfig() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject json = new JsonObject();

        json.addProperty("playerCoordXPercentage", playerCoordXPercentage);
        json.addProperty("playerCoordYPercentage", playerCoordYPercentage);
        json.addProperty("playerCoordTextColor", playerCoordTextColor);
        json.addProperty("playerCoordBackgroundColor", playerCoordBackgroundColor);

        json.addProperty("armorItemXPercentage", armorItemXPercentage);
        json.addProperty("armorItemYPercentage", armorItemYPercentage);
        json.addProperty("armorItemTextColor", armorItemTextColor);
        json.addProperty("armorItemBackgroundColor", armorItemBackgroundColor);

        json.addProperty("elytraXPercentage", elytraXPercentage);
        json.addProperty("elytraYPercentage", elytraYPercentage);
        json.addProperty("elytraTextColor", elytraTextColor);
        json.addProperty("elytraBackgroundColor", elytraBackgroundColor);

        json.addProperty("timeDisplayXPercentage", timeDisplayXPercentage);
        json.addProperty("timeDisplayYPercentage", timeDisplayYPercentage);
        json.addProperty("timeDisplayTextColor", timeDisplayTextColor);
        json.addProperty("timeDisplayBackgroundColor", timeDisplayBackgroundColor);

        json.addProperty("fpsDisplayXPercentage", fpsDisplayXPercentage);
        json.addProperty("fpsDisplayYPercentage", fpsDisplayYPercentage);
        json.addProperty("fpsDisplayTextColor", fpsDisplayTextColor);
        json.addProperty("fpsDisplayBackgroundColor", fpsDisplayBackgroundColor);

        try {
            Files.write(CONFIG_FILE, gson.toJson(json).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Screen getConfigScreen(Screen parentScreen) {
        loadConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parentScreen)
                .setDefaultBackgroundTexture(new Identifier("minecraft:textures/block/oak_planks.png"))
                .setTitle(new TranslatableText("config.PixelHud.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory about = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.about"));
        about.addEntry(
                entryBuilder.startTextDescription(new LiteralText("Thank you for using Pixel-HUD! \nHello, Minecraft community! I'm a passionate developer crafting Pixel-HUD to enhance your minecraft adventures. As a small dev, your support is invaluable. If you enjoy Pixel-HUD and want to back my efforts, consider buying me a coffee on Ko-fi. Your generosity fuels ongoing development. Happy gaming!\n \n").append(new LiteralText("Support me on Ko-fi:").setStyle(Style.EMPTY
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://ko-fi.com/oiha_dev"))
                        .withColor(Formatting.AQUA).withBold(Boolean.TRUE).withUnderline(Boolean.TRUE)))).build());

        ConfigCategory playerCoord = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.playerCoordinates"));
        playerCoord.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.playerCoordinates.xPercentage"), playerCoordXPercentage, 0, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> playerCoordXPercentage = xPercent)
                .build());

        playerCoord.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.playerCoordinates.yPercentage"), playerCoordYPercentage, 0, 100)
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

        ConfigCategory armorItem = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.armorItem"));
        armorItem.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.armorItem.xPercentage"), armorItemXPercentage, 0, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> armorItemXPercentage = xPercent)
                .build());

        armorItem.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.armorItem.yPercentage"), armorItemYPercentage, 0, 100)
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

        ConfigCategory elytra = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.elytra"));
        elytra.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.elytra.xPercentage"), elytraXPercentage, 0, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> elytraXPercentage = xPercent)
                .build());

        elytra.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.elytra.yPercentage"), elytraYPercentage, 0, 100)
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

        ConfigCategory timeDisplay = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.timeDisplay"));
        timeDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.timeDisplay.xPercentage"), timeDisplayXPercentage, 0, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> timeDisplayXPercentage = xPercent)
                .build());

        timeDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.timeDisplay.yPercentage"), timeDisplayYPercentage, 0, 100)
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

        ConfigCategory fpsDisplay = builder.getOrCreateCategory(new TranslatableText("config.PixelHud.fpsDisplay"));
        fpsDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.fpsDisplay.xPercentage"), fpsDisplayXPercentage, 0, 100)
                .setDefaultValue(50)
                .setSaveConsumer(xPercent -> fpsDisplayXPercentage = xPercent)
                .build());

        fpsDisplay.addEntry(entryBuilder.startIntSlider(new TranslatableText("config.PixelHud.fpsDisplay.yPercentage"), fpsDisplayYPercentage, 0, 100)
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

        builder.setSavingRunnable(() -> saveConfig());

        return builder.build();
    }
}
