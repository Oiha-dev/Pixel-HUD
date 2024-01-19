package org.pixelhud.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class Pixel_HudKeyBinding {
    private static final String CATEGORY = "Pixel-Hud";
    private static KeyBinding calculatorKeyBinding;

    public static void register() {
        calculatorKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                CATEGORY
        ));
    }
    public static KeyBinding getCalculatorKeyBinding() {
        return calculatorKeyBinding;
    }
}