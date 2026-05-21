package com.chewylopez.pocketsmod.client;

import com.chewylopez.pocketsmod.InventoryInterface.StorageToggleSlot;
import com.chewylopez.pocketsmod.PocketsMod;
import com.chewylopez.pocketsmod.server.ScrollRowPacket;
import com.chewylopez.pocketsmod.server.SelectTabPacket;
import com.chewylopez.pocketsmod.server.SetTabIconPacket;
import com.chewylopez.pocketsmod.server.SetTabNamePacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class StorageConduitScreen extends AbstractContainerScreen<StorageConduitMenu> {

    private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath(PocketsMod.MODID,"textures/gui/container/conduit_texture.png");

    private static final ResourceLocation[] TABS_TOP_UNSELECTED = {
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_2"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_3"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_4"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_5"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_unselected_6"),
    };

    private static final ResourceLocation[] TABS_TOP_SELECTED = {
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_2"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_3"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_4"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_5"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_top_selected_6"),
    };

    private static final ResourceLocation[] TABS_BOTTOM_UNSELECTED = {
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_2"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_3"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_4"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_5"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_unselected_6"),
    };

    private static final ResourceLocation[] TABS_BOTTOM_SELECTED = {
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_2"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_3"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_4"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_5"),
            ResourceLocation.withDefaultNamespace("container/creative_inventory/tab_bottom_selected_6"),
    };


    private static final int TAB_WIDTH = 26;
    private static final int TAB_HEIGHT = 28;
    private static final int TABS_PER_ROW = 5;
    private static final int TABS_PER_PAGE = TABS_PER_ROW * 2;
    private static final int INACTIVE_TABS_SINK = 0;
    private static final int ARROW_SIZE = 20;

    private static final int LEFT_MARGIN = 10;
    private static final int ARROW_L_RELATIVE_X = LEFT_MARGIN - 5;
    private static final int TABS_REL = LEFT_MARGIN + ARROW_SIZE;
    private static final int ARROW_R_RELATIVE_X = LEFT_MARGIN + ARROW_SIZE + TABS_PER_ROW * TAB_WIDTH + 5;

    private static final int SCROLL_X_RELATIVE_POS = 172;
    private static final int SCROLL_W = 12;
    private static final int SCROLL_TOP_REL = 18;
    private static final int SCROLL_TRACK_H = 18*6;

    private int currentPage = 0;
    private int lastActiveTab = -1;
    private boolean isDragging = false;
    private int dragStartY = 0;
    private int dragStartRow = 0;

    private EditBox renameField;
    private int renamingTabIndex = -1;

    private Button prevPageButton;
    private Button nextPageButton;

    public StorageConduitScreen(StorageConduitMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        imageWidth = 200;
        imageHeight = 222;
        inventoryLabelY = imageHeight - 112;
    }

    @Override
    public void init() {
        super.init();
        renameField = addRenderableWidget(new EditBox(font, leftPos, topPos - TAB_HEIGHT - 14, imageWidth/2 - 26, 14, Component.empty()));
        renameField.setMaxLength(32);
        renameField.setVisible(false);

        int aTop = topPos - TAB_HEIGHT + 6;

        prevPageButton = addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            if (currentPage > 0) { currentPage--; updatePageButtons(); }
        }).pos(leftPos + ARROW_L_RELATIVE_X, aTop).size(ARROW_SIZE, ARROW_SIZE).build());

        nextPageButton = addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            int max = (menu.getTabCount() - 1) / TABS_PER_PAGE;
            if (currentPage < max) { currentPage++; updatePageButtons(); }
        }).pos(leftPos + ARROW_R_RELATIVE_X, aTop).size(ARROW_SIZE, ARROW_SIZE).build());

        updatePageButtons();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partial) {
        updatePageButtons();

        int tab = menu.getActiveTab();

        if (tab != lastActiveTab) {
            lastActiveTab = tab;
            if (menu.getScrollRow() != 0) {
                menu.setScrollRowClient(0);
                PacketDistributor.sendToServer(new ScrollRowPacket(0));
            }
        }
        renderBackground(graphics, mouseX, mouseY, partial);

        if (renamingTabIndex >= 0) {
            graphics.fill(renameField.getX() - 2, renameField.getY() - 2, renameField.getX() + renameField.getWidth() + 2, renameField.getY() + renameField.getHeight() + 2, 0xFF000000);
        }
        super.render(graphics, mouseX, mouseY, partial);
        renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        clampPage();
        drawTabs(graphics, false);
        graphics.blit(BG, leftPos, topPos, 0, 0, 191, imageHeight);

        graphics.blit(BG, leftPos, topPos, 0, 0, 176, imageHeight);

        for (Slot slot : menu.slots) {
            if (slot instanceof StorageToggleSlot s && !s.isEnabled()) {
                graphics.fill(
                        leftPos + slot.x, topPos + slot.y,
                        leftPos + slot.x + 16, topPos + slot.y + 16,
                        0x80000000
                );
            }
        }

        drawTabs(graphics, true);

        if (menu.getTabCount() > TABS_PER_PAGE) {
            int max = (menu.getTabCount() - 1) / TABS_PER_PAGE;
            graphics.drawCenteredString(font, (currentPage + 1) + " / " + (max + 1), leftPos + imageWidth / 2 - 5, topPos - TAB_HEIGHT - 10, 0xFFAAAAAA);
        }

        drawScrollbar(graphics);
    }

    private boolean canScroll() {
        return menu.getMaxScrollRow() > 0;
    }

    private int thumbHeight() {
        if (!canScroll()){
            return SCROLL_TRACK_H;
        }
        int totalRows = (menu.getTotalSlots() + 8) / 9;
        return Math.max(12, SCROLL_TRACK_H * 6 / totalRows);
    }

    private int thumbY() {
        int maxRow = menu.getMaxScrollRow();
        if (maxRow == 0){
            return topPos + SCROLL_TOP_REL;
        }
        return topPos + SCROLL_TOP_REL + (SCROLL_TRACK_H - thumbHeight()) * menu.getScrollRow() / maxRow;
    }

    private void drawScrollbar(GuiGraphics graphics) {
        int x = leftPos + SCROLL_X_RELATIVE_POS;
        int y = topPos  + SCROLL_TOP_REL;

        // Track
        //graphics.fill(x, y, x + SCROLL_W, y + SCROLL_TRACK_H, 0xFF000000);
        //graphics.fill(x + 1, y + 1, x + SCROLL_W + 1, y + SCROLL_TRACK_H - 1, 0xFF373737);

        // Thumb
        int ty = thumbY(), th = thumbHeight();
        int thumbColor = canScroll() ? 0xFFAAAAAA : 0xFF555555;
        graphics.fill(x + 1, ty, x + SCROLL_W - 1, ty + th - 2, thumbColor);
        if (canScroll()) {
            graphics.fill(x + 1, ty, x + 2, ty + th - 2, 0xFFCCCCCC); //left highlight
            graphics.fill(x + SCROLL_W - 2, ty, x + SCROLL_W - 1, ty + th - 2, Color.DARK_GRAY.getRGB()); //right highlights

            graphics.fill(x + 1, ty, x + SCROLL_W - 1, ty + 1,  0xFFCCCCCC); //top highlight
            graphics.fill(x + 2, ty + th - 2, x + SCROLL_W - 1, ty + th - 3, Color.DARK_GRAY.getRGB()); //bottom highlight
        }
    }

    private void clampPage() {
        int max = Math.max(0, (menu.getTabCount() - 1) / TABS_PER_PAGE);
        if (currentPage > max) {
            currentPage = max;
        }
    }

    private void drawTabs(GuiGraphics graphics, boolean activeOnly) {
        int active    = menu.getActiveTab();
        int pageStart = currentPage * TABS_PER_PAGE;

        for (int row = 0; row < 2; row++) {
            boolean top = (row == 0);
            int start = pageStart + row * TABS_PER_ROW;
            for (int col = 0; col < TABS_PER_ROW; col++) {
                int idx = start + col;
                if (idx >= menu.getTabCount()) break;
                if ((idx == active) != activeOnly) continue;
                drawSingleTab(graphics, idx, leftPos + TABS_REL + col * TAB_WIDTH, idx == active, top);
            }
        }
    }

    private void drawSingleTab(GuiGraphics graphics, int idx, int x, boolean isActive, boolean top) {
        int col = (idx % TABS_PER_PAGE) % TABS_PER_ROW;
        int tabTop, tabBot;
        ResourceLocation sprite;

        if (top) {
            tabTop = topPos - TAB_HEIGHT + (isActive ? 0 : INACTIVE_TABS_SINK);
            tabBot = topPos + (isActive ? 1 : 0);
            sprite = isActive ? TABS_TOP_SELECTED[col] : TABS_TOP_UNSELECTED[col];
            graphics.blitSprite(sprite, x, tabTop + 3, TAB_WIDTH, tabBot - tabTop);
        } else {
            tabTop = topPos + imageHeight - (isActive ? 1 : 0) - 3;
            tabBot = topPos + imageHeight + TAB_HEIGHT - (isActive ? 0 : INACTIVE_TABS_SINK);
            sprite = isActive ? TABS_BOTTOM_SELECTED[col] : TABS_BOTTOM_UNSELECTED[col];
            graphics.blitSprite(sprite, x, tabTop, TAB_WIDTH, tabBot - tabTop);
        }

        TabMetadata meta = menu.getTabMeta(idx);
        int midX = x + TAB_WIDTH / 2;
        int midYTop = (topPos - (TAB_HEIGHT/2)) + 3;
        int midYBot = (topPos + imageHeight + (TAB_HEIGHT/2)) - 3;
        int midY = (tabTop + tabBot) / 2;
        if (meta.hasIcon()) {
            if(top){
                graphics.renderItem(meta.icon(), midX - 8, midYTop - 8);
                graphics.renderItemDecorations(font, meta.icon(), midX - 8, midYTop - 8, null);
            }
            else {
                graphics.renderItem(meta.icon(), midX - 8, midYBot - 8);
                graphics.renderItemDecorations(font, meta.icon(), midX - 8, midYBot - 8, null);
            }

        } else {
            graphics.drawCenteredString(font, String.valueOf(idx + 1), midX, midY - font.lineHeight / 2, isActive ? 0x404040 : 0xDDDDDD);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        // Any click outside the rename field dismisses it
        if (renamingTabIndex >= 0) {
            exitRenameMode(false);
        }

        // Scrollbar — left click only
        if (button == 0 && canScroll()) {
            int sx = leftPos + SCROLL_X_RELATIVE_POS, sy = topPos + SCROLL_TOP_REL;
            if (mouseX >= sx && mouseX < sx + SCROLL_W && mouseY >= sy && mouseY < sy + SCROLL_TRACK_H) {
                int ty = thumbY(), th = thumbHeight();
                if (mouseY >= ty && mouseY < ty + th) {
                    isDragging = true; dragStartY = (int) mouseY; dragStartRow = menu.getScrollRow();
                } else {
                    float frac = (float)(mouseY - sy - th / 2f) / (SCROLL_TRACK_H - th);
                    applyScroll(Math.round(frac * menu.getMaxScrollRow()));
                }
                return true;
            }
        }

        // Tabs — left click switches, right click edits
        if (button == 0 || button == 1) {
            int pageStart = currentPage * TABS_PER_PAGE;
            for (int row = 0; row < 2; row++) {
                boolean top = (row == 0);
                int hitTop = top ? topPos - TAB_HEIGHT : topPos + imageHeight;
                int hitBot = top ? topPos          : topPos + imageHeight + TAB_HEIGHT;
                if (mouseY < hitTop || mouseY >= hitBot) continue;
                int start = pageStart + row * TABS_PER_ROW;
                for (int col = 0; col < TABS_PER_ROW; col++) {
                    int idx = start + col;
                    if (idx >= menu.getTabCount()) break;
                    int x = leftPos + TABS_REL + col * TAB_WIDTH;
                    if (mouseX >= x && mouseX < x + TAB_WIDTH) {
                        if (button == 0) {
                            PacketDistributor.sendToServer(new SelectTabPacket(idx));
                        } else {
                            ItemStack held = menu.getCarried();
                            if (!held.isEmpty()) {
                                ItemStack icon = held.copyWithCount(1);
                                PacketDistributor.sendToServer(new SetTabIconPacket(idx, icon));
                                menu.setTabMetaIconClient(idx, icon);
                            } else {
                                enterRenameMode(idx);
                            }
                        }
                        return true;
                    }
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void updatePageButtons() {
        boolean multiPage = menu.getTabCount() > TABS_PER_PAGE;
        prevPageButton.visible = multiPage;
        nextPageButton.visible = multiPage;
        if (multiPage) {
            int max = (menu.getTabCount() - 1) / TABS_PER_PAGE;
            prevPageButton.active = currentPage > 0;
            nextPageButton.active = currentPage < max;
        }
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && button == 0 && canScroll()) {
            int th = thumbHeight();
            float rowsPerPx = (float) menu.getMaxScrollRow() / (SCROLL_TRACK_H - th);
            applyScroll(dragStartRow + (int)((mouseY - dragStartY) * rowsPerPx));
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0){
            isDragging = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (canScroll()) {
            applyScroll(menu.getScrollRow() - (int) Math.signum(scrollY));
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    private void applyScroll(int row) {
        row = Math.max(0, Math.min(row, menu.getMaxScrollRow()));
        if (row == menu.getScrollRow()) return;
        menu.setScrollRowClient(row);
        PacketDistributor.sendToServer(new ScrollRowPacket(row));
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (renameField.isFocused()) {
            if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
                exitRenameMode(true);
                return true;
            }
            if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
                exitRenameMode(false);
                return true;
            }
            return renameField.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected boolean hasClickedOutside(double mouseX, double mouseY, int guiLeft, int guiTop, int mouseButton) {
        boolean inTopTabs = mouseY >= topPos - TAB_HEIGHT && mouseY < topPos;
        boolean inBotTabs = mouseY >= topPos + imageHeight && mouseY < topPos + imageHeight + TAB_HEIGHT;
        if (inTopTabs || inBotTabs) {
            return false;
        }
        return super.hasClickedOutside(mouseX, mouseY, guiLeft, guiTop, mouseButton);
    }

    private void enterRenameMode(int tabIdx) {
        renamingTabIndex = tabIdx;
        renameField.setValue(menu.getTabMeta(tabIdx).name());
        renameField.setVisible(true);
        renameField.setFocused(true);
        setFocused(renameField);
    }

    private void exitRenameMode(boolean confirm) {
        if (confirm && renamingTabIndex >= 0) {
            String name = renameField.getValue().trim();
            PacketDistributor.sendToServer(new SetTabNamePacket(renamingTabIndex, name));
            menu.setTabMetaNameClient(renamingTabIndex, name);
        }
        renamingTabIndex = -1;
        renameField.setVisible(false);
        renameField.setFocused(false);
        setFocused(null);
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int MouseX, int MouseY) {
        super.renderTooltip(graphics, MouseX, MouseY);

        int pageStart = currentPage * TABS_PER_PAGE;

        for (int row = 0; row < 2; row++) {
            boolean top = (row == 0);
            int hitTop = top ? topPos - TAB_HEIGHT : topPos + imageHeight;
            int hitBot = top ? topPos          : topPos + imageHeight + TAB_HEIGHT;

            if (MouseY < hitTop || MouseY >= hitBot) continue;

            int start = pageStart + row * TABS_PER_ROW;
            for (int col = 0; col < TABS_PER_ROW; col++) {
                int idx = start + col;
                if (idx >= menu.getTabCount()) break;
                int x = leftPos + TABS_REL + col * TAB_WIDTH;
                if (MouseX >= x && MouseX < x + TAB_WIDTH) {
                    graphics.renderTooltip(font, menu.getTabMeta(idx).displayName(idx), MouseX, MouseY);
                    return;
                }
            }

        }
    }
}