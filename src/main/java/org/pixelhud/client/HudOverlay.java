package org.pixelhud.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.mixin.networking.accessor.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;

public class HudOverlay implements HudRenderCallback {

    int x_coo = 10;
    int y_coo = 10;

    int x_armor = 10;
    int y_armor = 100;

    int x_elytra = 100;
    int y_elytra = 10;

    int x_time = 200;
    int y_time = 10;

    int x_fps = 400;
    int y_fps = 10;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            // Player coordinates:
            assert client.player != null;

            BlockPos playerPos = client.player.getBlockPos();
            String xCoordinate = String.format("X: %d", playerPos.getX());
            String yCoordinate = String.format("Y: %d", playerPos.getY());
            String zCoordinate = String.format("Z: %d", playerPos.getZ());

            int widthBackgroundsCoordinates = calculateBackgroundWidth(xCoordinate, yCoordinate, zCoordinate);

            renderBackground(matrixStack, x_coo - 5, y_coo - 5, 9 + widthBackgroundsCoordinates, 37); // Transparent black background
            renderTextCoordinate(matrixStack, x_coo, y_coo, xCoordinate, yCoordinate, zCoordinate, 1.0f);
    
            // Take the item of each slot
            ItemStack mainHand = client.player.getEquippedStack(EquipmentSlot.MAINHAND);
            ItemStack head = client.player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack legs = client.player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack feet = client.player.getEquippedStack(EquipmentSlot.FEET);
            ItemStack offHand = client.player.getEquippedStack(EquipmentSlot.OFFHAND);

            // Render armor and hands
            renderTextureArmorHands(matrixStack, x_armor, y_armor, mainHand, head, chest, legs, feet, offHand);
            renderElytra(matrixStack, chest);
            renderTime(matrixStack);

            String fps = MinecraftClient.getInstance().fpsDebugString.substring(0, MinecraftClient.getInstance().fpsDebugString.indexOf('s')+1);
            renderBackground(matrixStack, x_fps - 5, y_fps - 5, calculateBackgroundWidth(fps) + 10, 17);
            renderText(matrixStack, x_fps, y_fps, fps);
        }
    }
    private void renderTime(MatrixStack matrixStack){
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        long daytime = MinecraftClient.getInstance().player.world.getTimeOfDay()+6000;
        int hours=(int) (daytime / 1000)%24;
        int minutes = (int) ((daytime % 1000)*60/1000);
        String formattedTime = String.format("%02d:%02d", hours, minutes);
        renderBackground(matrixStack, x_time - 5, y_time - 5, textRenderer.getWidth(formattedTime) + 10, 17);
        renderText(matrixStack, x_time, y_time, formattedTime);
    }

    private void renderElytra(MatrixStack matrixStack, ItemStack chestplate) {
        if (chestplate.getItem() == Items.ELYTRA) {
            int unbreakingNumber = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, chestplate);
            int timeRemaining = determineTimeLeft(chestplate)-unbreakingNumber;
            String elytraTimeLeftString = formatTime(timeRemaining);
            renderBackground(matrixStack, x_elytra - 5, y_elytra - 5, calculateBackgroundWidth(elytraTimeLeftString)+10, 17);
            renderText(matrixStack, x_elytra, y_elytra, elytraTimeLeftString);
        }
    }

    private int determineTimeLeft(ItemStack item) {
        int unbreakingNumber = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, item);
        return ((item.getMaxDamage() - item.getDamage()) * (unbreakingNumber + 1)) - 1;
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void renderTextureArmorHands(MatrixStack matrixStack, int x, int y, ItemStack mainHand, ItemStack head, ItemStack chest, ItemStack legs, ItemStack feet, ItemStack offHand) {
        MinecraftClient client = MinecraftClient.getInstance();

        int numberOfItems = 0; // Count the number of items needed to render
        String stringBackground = "";
        int numberItems = 0;
        if (client != null) {
            ItemStack[] Items = {mainHand, head, chest, legs, feet, offHand};
            for (ItemStack item : Items) {
                if (item.isDamageable()) {
                    int durability = item.getMaxDamage() - item.getDamage();
                    int maxDurability = item.getMaxDamage();
                    if (String.valueOf(durability + "/" + maxDurability + " ").length() > stringBackground.length()) {
                        stringBackground = (durability + "/" + maxDurability + " ");
                    }
                } else if (item.getCount() > 0) {
                    if (String.valueOf("x" + item.getCount() + " ").length() > stringBackground.length()) {
                        stringBackground = ("x" + item.getCount() + " ");
                    }
                }
                if (!item.isEmpty()) {
                    numberItems += 1;
                }
            }
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            int widthBackground = textRenderer.getWidth(stringBackground);
            if (numberItems != 0) {
                renderBackground(matrixStack, x_armor - 3, y_armor - 3, 22 + widthBackground, 16 * numberItems + 6);
            }
            int iconSize = 16; // Adjust the size of the armor if there is a texture pack

            numberOfItems += renderItemWithDurability(matrixStack, x, y, mainHand, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack, x, y + numberOfItems * iconSize, head, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack, x, y + numberOfItems * iconSize, chest, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack, x, y + numberOfItems * iconSize, legs, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack, x, y + numberOfItems * iconSize, feet, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack, x, y + numberOfItems * iconSize, offHand, iconSize);
        }
    }

    private int calculateBackgroundWidth(String... texts) {
        int totalWidth = 0;
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        for (String text : texts) {
            totalWidth = Math.max(totalWidth, textRenderer.getWidth(text));
        }
        return totalWidth;
    }

    private int renderItemWithDurability(MatrixStack matrixStack, int x, int y, ItemStack itemStack, int iconSize) {
        if (!itemStack.isEmpty()) {
            renderItemIcon(matrixStack, x, y, itemStack, iconSize);

            // Display durability or item count if present
            if (itemStack.isDamageable()) {
                int durability = itemStack.getMaxDamage() - itemStack.getDamage();
                int maxDurability = itemStack.getMaxDamage();
                renderText(matrixStack, x + iconSize + 2, y + 4, durability + "/" + maxDurability);
            } else if (itemStack.getCount() > 0) {
                renderText(matrixStack, x + iconSize + 2, y + 4, "x" + itemStack.getCount());
            }

            return 1; // One item is rendered
        }
        return 0; // No item is rendered
    }

    private void renderText(MatrixStack matrixStack, int x, int y, String text) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        matrixStack.push();
        matrixStack.scale((float) 1.0, (float) 1.0, 1.0F);
        textRenderer.draw(matrixStack, new LiteralText(text), x, y, 0xFFFFFF);
        matrixStack.pop();
    }

    private void renderItemIcon(MatrixStack matrixStack, int x, int y, ItemStack itemStack, int iconSize) {
        MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(itemStack, x, y);
    }

    private void renderBackground(MatrixStack matrixStack, int x, int y, int width, int height) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DrawableHelper.fill(matrixStack, x, y, x + width, y + height, -2147483648);
    }

    private void renderTextCoordinate(MatrixStack matrixStack, int x, int y, String xText, String yText, String zText, float scale) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        matrixStack.push();
        matrixStack.scale(scale, scale, 1.0F);
        textRenderer.draw(matrixStack, new LiteralText(xText), x, y, 0xFFFFFF);
        textRenderer.draw(matrixStack, new LiteralText(yText), x, y + 10, 0xFFFFFF);
        textRenderer.draw(matrixStack, new LiteralText(zText), x, y + 20, 0xFFFFFF);
        matrixStack.pop();
    }
}