package org.pixelhud.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.MessageType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.pixelhud.client.Config.HudConfigScreen;

public class Pixel_HUDClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(new HudOverlay());
        Pixel_HudKeyBinding.register();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (Pixel_HudKeyBinding.getCalculatorKeyBinding().isPressed()) {
                MinecraftClient.getInstance().setScreen(HudConfigScreen.getConfigScreen(MinecraftClient.getInstance().currentScreen));
            }
        });
    }
}