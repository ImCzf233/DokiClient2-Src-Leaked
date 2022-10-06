/*
 * Decompiled with CFR 0_122.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package just.monika.IQ救不了主播你因为你反编译我端子.util.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import just.monika.IQ救不了主播你因为你反编译我端子.util.misc.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

public class NharFont {
    private final Pattern patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OG]");
    private final Pattern patternUnsupported = Pattern.compile("(?i)\\u00A7[K-O]");
    private Font theFont;
    private boolean antiAlias = true;
    private Graphics2D theGraphics;
    private FontMetrics theMetrics;
    private float fontSize;
    private int startChar;
    private int endChar;
    private float[] xPos;
    private float[] yPos;
    private BufferedImage bufferedImage;
    private float extraSpacing = 0.0f;
    private DynamicTexture dynamicTexture;
    private ResourceLocation resourceLocation;

    public NharFont(Object font, float size) {
        this(font, size, 0.0f);
    }

    public NharFont(Object font, float size, float spacing) {
        this.fontSize = size;
        this.startChar = 32;
        this.endChar = 255;
        this.extraSpacing = spacing;
        this.xPos = new float[this.endChar - this.startChar];
        this.yPos = new float[this.endChar - this.startChar];
        this.setupGraphics2D();
        this.createFont(font, size);
    }

    private void setupGraphics2D() {
        GlStateManager.pushMatrix();
        this.bufferedImage = new BufferedImage(256, 256, 2);
        this.theGraphics = (Graphics2D)this.bufferedImage.getGraphics();
        if (this.antiAlias) {
            this.theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            this.theGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        GlStateManager.popMatrix();
    }

    private void createFont(Object font, float size) {
        GlStateManager.pushMatrix();
        try {
            this.theFont = font instanceof Font ? (Font)font : (font instanceof File ? Font.createFont(0, (File)font).deriveFont(size) : (font instanceof InputStream ? Font.createFont(0, (InputStream)font).deriveFont(size) : (font instanceof String ? new Font((String)font, 0, Math.round(size)) : new Font("Verdana", 0, Math.round(size)))));
            this.theGraphics.setFont(this.theFont);
        }
        catch (Exception e) {
            e.printStackTrace();
            this.theFont = new Font("Verdana", 0, Math.round(size));
            this.theGraphics.setFont(this.theFont);
        }
        this.theGraphics.setColor(new Color(255, 255, 255, 0));
        this.theGraphics.fillRect(0, 0, 256, 256);
        this.theGraphics.setColor(Color.white);
        this.theMetrics = this.theGraphics.getFontMetrics();
        float x = 5.0f;
        float y = 5.0f;
        for (int i = this.startChar; i < this.endChar; ++i) {
            this.theGraphics.drawString(Character.toString((char)i), x, y + (float)this.theMetrics.getAscent());
            this.xPos[i - this.startChar] = x;
            this.yPos[i - this.startChar] = y - (float)this.theMetrics.getMaxDescent();
            if ((x += (float)this.theMetrics.stringWidth(Character.toString((char)i)) + 2.0f) < (float)(250 - this.theMetrics.getMaxAdvance())) continue;
            x = 5.0f;
            y += (float)(this.theMetrics.getMaxAscent() + this.theMetrics.getMaxDescent()) + this.fontSize / 2.0f;
        }
        this.dynamicTexture = new DynamicTexture(this.bufferedImage);
        this.resourceLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("font" + font.toString() + size, this.dynamicTexture);
        GlStateManager.popMatrix();
    }

    public void drawCenteredString(String text, float x, float y, FontType fontType, int color, int color2) {
        this.drawString(text, x - this.getStringWidth(text) / 2.0f, y, fontType, color, color2);
    }

    public void drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(text, x, y, FontType.SHADOW_THIN, color, -16777216);
    }

    public void drawString(String text, float x, float y, FontType fontType, int color, int color2) {
        GlStateManager.pushMatrix();
        try {
            text = this.stripUnsupported(text);
        }
        catch (Exception e) {
            return;
        }
        GL11.glEnable((int)3042);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        String text2 = this.stripControlCodes(text);
        switch (fontType.ordinal()) {
            case 1: {
                this.drawer(text2, x + 0.5f, y, color2);
                this.drawer(text2, x - 0.5f, y, color2);
                this.drawer(text2, x, y + 0.5f, color2);
                this.drawer(text2, x, y - 0.5f, color2);
                break;
            }
            case 2: {
                this.drawer(text2, x + 0.5f, y + 0.5f, color2);
                break;
            }
            case 3: {
                this.drawer(text2, x + 1.0f, y + 1.0f, color2);
                break;
            }
            case 4: {
                this.drawer(text2, x, y + 0.5f, color2);
                break;
            }
            case 5: {
                this.drawer(text2, x, y - 0.5f, color2);
                break;
            }
        }
        this.drawer(text, x, y, color);
        GlStateManager.scale(2.0f, 2.0f, 2.0f);
        GlStateManager.popMatrix();
    }

    private void drawer(String text, float x, float y, int color) {
        y -= 5.0f;
        y *= 2.0f;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        float alpha = (float)(color >> 24 & 255) / 255.0f;
        float red = (float)(color >> 16 & 255) / 255.0f;
        float green = (float)(color >> 8 & 255) / 255.0f;
        float blue = (float)(color & 255) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
        float startX = x *= 2.0f;
        for (int i = 0; i < text.length(); ++i) {
            int colorCode;
            if (text.charAt(i) == '\u00a7' && i + 1 < text.length()) {
                char oneMore = Character.toLowerCase(text.charAt(i + 1));
                if (oneMore == 'n') {
                    y += (float)(this.theMetrics.getAscent() + 2);
                    x = startX;
                }
                if ((colorCode = "0123456789abcdefklmnorg".indexOf(oneMore)) < 16) {
                    try {
                        int newColor = Minecraft.getMinecraft().fontRendererObj.colorCode[colorCode];
                        GlStateManager.color((float)(newColor >> 16) / 255.0f, (float)(newColor >> 8 & 255) / 255.0f, (float)(newColor & 255) / 255.0f, alpha);
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (oneMore == 'f') {
                    GlStateManager.color(1.0f, 1.0f, 1.0f, alpha);
                } else if (oneMore == 'r') {
                    GlStateManager.color(red, green, blue, alpha);
                } else if (oneMore == 'g') {
                    GlStateManager.color(0.47f, 0.67f, 0.27f, alpha);
                }
                ++i;
                continue;
            }
            try {
                char c = text.charAt(i);
                this.drawChar(c, x, y);
                x += this.getStringWidth(Character.toString(c)) * 2.0f;
                continue;
            }
            catch (ArrayIndexOutOfBoundsException indexException) {
                colorCode = text.charAt(i);
            }
        }
        GlStateManager.popMatrix();
    }

    public float getStringWidth(String text) {
        try {
            return (float)(this.getBounds(text).getWidth() + (double)this.extraSpacing) / 2.0f;
        }
        catch (Exception e) {
            return 0.0f;
        }
    }

    public float getStringHeight(String text) {
        try {
            return (float)this.getBounds(text).getHeight() / 2.0f;
        }
        catch (Exception e) {
            return 0.0f;
        }
    }

    private Rectangle2D getBounds(String text) {
        return this.theMetrics.getStringBounds(StringUtils.stripControlCodes(text), this.theGraphics);
    }

    private void drawChar(char character, float x, float y) throws ArrayIndexOutOfBoundsException {
        GlStateManager.pushMatrix();
        Rectangle2D bounds = this.theMetrics.getStringBounds(Character.toString(character), this.theGraphics);
        this.drawTexturedModalRect(x, y, this.xPos[character - this.startChar], this.yPos[character - this.startChar], (float)bounds.getWidth(), (float)bounds.getHeight() + (float)this.theMetrics.getMaxDescent() + 1.0f);
        GlStateManager.popMatrix();
    }

    public List listFormattedStringToWidth(String s, int width) {
        return Arrays.asList(this.wrapFormattedStringToWidth(s, width).split("\n"));
    }

    String wrapFormattedStringToWidth(String s, float width) {
        int wrapWidth = this.sizeStringToWidth(s, width);
        if (s.length() <= wrapWidth) {
            return s;
        }
        String split = s.substring(0, wrapWidth);
        String split2 = this.getFormatFromString(split) + s.substring(wrapWidth + (s.charAt(wrapWidth) == ' ' || s.charAt(wrapWidth) == '\n' ? 1 : 0));
        try {
            return split + "\n" + this.wrapFormattedStringToWidth(split2, width);
        }
        catch (Exception e) {
            ChatUtil.printChat("Cannot wrap string to width.");
            return "";
        }
    }

    private int sizeStringToWidth(String par1Str, float par2) {
        int var5;
        int var3 = par1Str.length();
        float var4 = 0.0f;
        int var6 = -1;
        boolean var7 = false;
        for (var5 = 0; var5 < var3; ++var5) {
            char var8 = par1Str.charAt(var5);
            switch (var8) {
                case '\n': {
                    --var5;
                    break;
                }
                case '\u00a7': {
                    char var9;
                    if (var5 >= var3 - 1) break;
                    if ((var9 = par1Str.charAt(++var5)) != 'l' && var9 != 'L') {
                        if (var9 != 'r' && var9 != 'R' && !this.isFormatColor(var9)) break;
                        var7 = false;
                        break;
                    }
                    var7 = true;
                    break;
                }
                case ' ': {
                    var6 = var5;
                }
                case '-': {
                    var6 = var5;
                }
                case '_': {
                    var6 = var5;
                }
                case ':': {
                    var6 = var5;
                }
                default: {
                    String text = String.valueOf(var8);
                    var4 += this.getStringWidth(text);
                    if (!var7) break;
                    var4 += 1.0f;
                }
            }
            if (var8 == '\n') {
                var6 = ++var5;
                continue;
            }
            if (var4 > par2) break;
        }
        return var5 != var3 && var6 != -1 && var6 < var5 ? var6 : var5;
    }

    private String getFormatFromString(String par0Str) {
        String var1 = "";
        int var2 = -1;
        int var3 = par0Str.length();
        while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
            if (var2 >= var3 - 1) continue;
            char var4 = par0Str.charAt(var2 + 1);
            if (this.isFormatColor(var4)) {
                var1 = Character.toString('\u00a7') + var4;
                continue;
            }
            if (!this.isFormatSpecial(var4)) continue;
            var1 = var1 + Character.toString('\u00a7') + var4;
        }
        return var1;
    }

    private boolean isFormatColor(char par0) {
        return par0 >= '0' && par0 <= '9' || par0 >= 'a' && par0 <= 'f' || par0 >= 'A' && par0 <= 'F';
    }

    private boolean isFormatSpecial(char par0) {
        return par0 >= 'k' && par0 <= 'o' || par0 >= 'K' && par0 <= 'O' || par0 == 'r' || par0 == 'R';
    }

    private void drawTexturedModalRect(float x, float y, float textureX, float textureY, float width, float height) {
        GlStateManager.pushMatrix();
        float var7 = 0.00390625f;
        float var8 = 0.00390625f;
        Tessellator var9 = Tessellator.getInstance();
        WorldRenderer var10 = var9.getWorldRenderer();
        var10.startDrawingQuads();
        var10.addVertexWithUV(x + 0.0f, y + height, 0.0, (textureX + 0.0f) * var7, (textureY + height) * var8);
        var10.addVertexWithUV(x + width, y + height, 0.0, (textureX + width) * var7, (textureY + height) * var8);
        var10.addVertexWithUV(x + width, y + 0.0f, 0.0, (textureX + width) * var7, (textureY + 0.0f) * var8);
        var10.addVertexWithUV(x + 0.0f, y + 0.0f, 0.0, (textureX + 0.0f) * var7, (textureY + 0.0f) * var8);
        var9.draw();
        GlStateManager.popMatrix();
    }

    public String stripControlCodes(String s) {
        return this.patternControlCode.matcher(s).replaceAll("");
    }

    public String stripUnsupported(String s) {
        return this.patternUnsupported.matcher(s).replaceAll("");
    }

    public Graphics2D getGraphics() {
        return this.theGraphics;
    }

    public Font getFont() {
        return this.theFont;
    }

    public static enum FontType {
        NORMAL,
        OUTLINE_THIN,
        SHADOW_THIN,
        SHADOW_THICK,
        EMBOSS_TOP,
        EMBOSS_BOTTOM;
        

        private FontType() {
        }
    }

}

