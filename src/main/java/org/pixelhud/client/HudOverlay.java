package org.pixelhud.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
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
import org.pixelhud.client.Config.HudConfigScreen;

import static org.pixelhud.client.Config.HudConfigScreen.*;

public class HudOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        HudConfigScreen.loadConfig();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            // Player coordinates:
            assert client.player != null;

            BlockPos playerPos = client.player.getBlockPos();
            String xCoordinate = String.format("X: %d", playerPos.getX());
            String yCoordinate = String.format("Y: %d", playerPos.getY());
            String zCoordinate = String.format("Z: %d", playerPos.getZ());

            int widthBackgroundsCoordinates = calculateBackgroundWidth(xCoordinate, yCoordinate, zCoordinate);

            renderBackground(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (4 + widthBackgroundsCoordinates)) * playerCoordXPercentage / 100.0) - 5, (int) (((MinecraftClient.getInstance().getWindow().getScaledHeight() - 4) * playerCoordYPercentage) / 100.0) - 5, 9 + widthBackgroundsCoordinates, 37, playerCoordBackgroundColor); // Transparent black background
            renderTextCoordinate(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (4 + widthBackgroundsCoordinates)) * playerCoordXPercentage / 100.0),  (int) (((MinecraftClient.getInstance().getWindow().getScaledHeight() - 4) * playerCoordYPercentage) / 100.0), xCoordinate, yCoordinate, zCoordinate, 1.0f, playerCoordTextColor);
            // Take the item of each slot
            ItemStack mainHand = client.player.getEquippedStack(EquipmentSlot.MAINHAND);
            ItemStack head = client.player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack legs = client.player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack feet = client.player.getEquippedStack(EquipmentSlot.FEET);
            ItemStack offHand = client.player.getEquippedStack(EquipmentSlot.OFFHAND);

            // Render armor and hands
            renderTextureArmorHands(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth()) * armorItemXPercentage / 100.0),  (int) ((MinecraftClient.getInstance().getWindow().getScaledHeight()) * armorItemYPercentage / 100.0), mainHand, head, chest, legs, feet, offHand);
            renderElytra(matrixStack, chest);
            renderTime(matrixStack);

            String fps = MinecraftClient.getInstance().fpsDebugString.substring(0, MinecraftClient.getInstance().fpsDebugString.indexOf('s')+1);
            renderBackground(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (calculateBackgroundWidth(fps) + 10)) * fpsDisplayXPercentage / 100.0) - 5, (int) ((MinecraftClient.getInstance().getWindow().getScaledHeight()) * fpsDisplayYPercentage / 100.0), calculateBackgroundWidth(fps) + 10, 17, fpsDisplayBackgroundColor);
            renderText(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (calculateBackgroundWidth(fps) + 10)) * fpsDisplayXPercentage / 100.0), (int) ((MinecraftClient.getInstance().getWindow().getScaledHeight()) * fpsDisplayYPercentage / 100.0)+5, fps, fpsDisplayTextColor);
        }
    }
    private void renderTime(MatrixStack matrixStack){
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        long daytime = MinecraftClient.getInstance().player.world.getTimeOfDay()+6000;
        int hours=(int) (daytime / 1000)%24;
        int minutes = (int) ((daytime % 1000)*60/1000);
        String formattedTime = String.format("%02d:%02d", hours, minutes);
        renderBackground(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (textRenderer.getWidth(formattedTime) + 10)) * timeDisplayXPercentage / 100.0) - 5, (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * timeDisplayYPercentage / 100.0) - 5, textRenderer.getWidth(formattedTime) + 10, 17, timeDisplayBackgroundColor);
        renderText(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (textRenderer.getWidth(formattedTime) + 10)) * timeDisplayXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * timeDisplayYPercentage / 100.0), formattedTime, timeDisplayTextColor);
    }

    private void renderElytra(MatrixStack matrixStack, ItemStack chestplate) {
        if (chestplate.getItem() == Items.ELYTRA) {
            int unbreakingNumber = EnchantmentHelper.getLevel(Enchantments.UNBREAKING, chestplate);
            int timeRemaining = determineTimeLeft(chestplate)-unbreakingNumber;
            String elytraTimeLeftString = formatTime(timeRemaining);
            renderBackground(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (calculateBackgroundWidth(elytraTimeLeftString)+10)) * elytraXPercentage / 100.0) - 5, (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * elytraYPercentage / 100.0) - 5, calculateBackgroundWidth(elytraTimeLeftString)+10, 17, elytraBackgroundColor);
            renderText(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (calculateBackgroundWidth(elytraTimeLeftString)+10)) * elytraXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * elytraYPercentage / 100.0), elytraTimeLeftString, elytraTextColor);
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
                    if ((durability + "/" + maxDurability + " ").length() > stringBackground.length()) {
                        stringBackground = (durability + "/" + maxDurability + " ");
                    }
                } else if (item.getCount() > 0) {
                    if (("x" + item.getCount() + " ").length() > stringBackground.length()) {
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
                renderBackground(matrixStack, (int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0) - 3, (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0) - 3, 22 + widthBackground, 16 * numberItems + 6, armorItemBackgroundColor);
            }
            int iconSize = 16; // Adjust the size of the armor if there is a texture pack

            numberOfItems += renderItemWithDurability(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0), mainHand, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0) + numberOfItems * iconSize, head, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0) + numberOfItems * iconSize, chest, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0) + numberOfItems * iconSize, legs, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0) + numberOfItems * iconSize, feet, iconSize);
            numberOfItems += renderItemWithDurability(matrixStack,(int) ((MinecraftClient.getInstance().getWindow().getScaledWidth() - (22 + widthBackground)) * armorItemXPercentage / 100.0), (int) (MinecraftClient.getInstance().getWindow().getScaledHeight() * armorItemYPercentage / 100.0) + numberOfItems * iconSize, offHand, iconSize);}
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
                renderText(matrixStack, x + iconSize + 2, y + 4, durability + "/" + maxDurability, armorItemTextColor);
            } else if (itemStack.getCount() > 0) {
                renderText(matrixStack, x + iconSize + 2, y + 4, "x" + itemStack.getCount(), armorItemTextColor);
            }

            return 1; // One item is rendered
        }
        return 0; // No item is rendered
    }

    private void renderText(MatrixStack matrixStack, int x, int y, String text,int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        matrixStack.push();
        matrixStack.scale((float) 1.0, (float) 1.0, 1.0F);
        textRenderer.draw(matrixStack, new LiteralText(text), x, y, color);
        matrixStack.pop();
    }

    private void renderItemIcon(MatrixStack matrixStack, int x, int y, ItemStack itemStack, int iconSize) {
        MinecraftClient.getInstance().getItemRenderer().renderInGuiWithOverrides(itemStack, x, y);
    }

    private void renderBackground(MatrixStack matrixStack, int x, int y, int width, int height, int color) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        DrawableHelper.fill(matrixStack, x, y, x + width, y + height, color);
    }

    private void renderTextCoordinate(MatrixStack matrixStack, int x, int y, String xText, String yText, String zText, float scale, int color) {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        matrixStack.push();
        matrixStack.scale(scale, scale, 1.0F);
        textRenderer.draw(matrixStack, new LiteralText(xText), x, y, color);
        textRenderer.draw(matrixStack, new LiteralText(yText), x, y + 10, color);
        textRenderer.draw(matrixStack, new LiteralText(zText), x, y + 20, color);
        matrixStack.pop();
    }
}
