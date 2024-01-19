package org.pixelhud;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.minecraft.client.MinecraftClient;
import org.pixelhud.client.Config.HudConfigScreen;
import org.pixelhud.client.HudOverlay;

public class ModMenuApiIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            // Return the screen here with the one you created from Cloth Config Builder
            return HudConfigScreen.getConfigScreen(MinecraftClient.getInstance().currentScreen);
        };
    }
}