/*
 * Decompiled with CFR 0_122.
 */
package just.monika.IQ救不了主播你因为你反编译我端子.gui.click.components;

import java.util.ArrayList;

import just.monika.IQ救不了主播你因为你反编译我端子.DokiDokiLegitClient;
import just.monika.IQ救不了主播你因为你反编译我端子.gui.click.ui.UI;

public class MainPanel {
    public float x;
    public float y;
    public String headerString;
    public float dragX;
    public float dragY;
    public float lastDragX;
    public float lastDragY;
    public boolean dragging;
    UI theme;
    public CategoryPanel currentPanel = null;
    public ArrayList<CategoryButton> typeButton;
    public ArrayList<CategoryPanel> typePanel;
    public ArrayList<SLButton> slButtons;

    public MainPanel(String header, float x, float y, UI theme) {
        this.headerString = header;
        this.x = x;
        this.y = y;
        this.theme = theme;
        this.typeButton = new ArrayList();
        this.typePanel = new ArrayList();
        this.slButtons = new ArrayList();
        theme.panelConstructor(this, x, y);
    }

    public void mouseClicked(int x, int y, int state) {
        for (UI theme : DokiDokiLegitClient.getClickGui().getThemes()) {
            theme.panelMouseClicked(this, x, y, state);
        }
    }

    public void mouseMovedOrUp(int x, int y, int state) {
        for (UI theme : DokiDokiLegitClient.getClickGui().getThemes()) {
            theme.panelMouseMovedOrUp(this, x, y, state);
        }
    }

    public void draw(int mouseX, int mouseY) {
        for (UI theme : DokiDokiLegitClient.getClickGui().getThemes()) {
            theme.mainPanelDraw(this, mouseX, mouseY);
        }
    }

    public void keyPressed(int key) {
        for (UI theme : DokiDokiLegitClient.getClickGui().getThemes()) {
            theme.mainPanelKeyPress(this, key);
        }
    }
}

