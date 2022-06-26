package runesmith.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import runesmith.RunesmithMod;
import runesmith.patches.ElementsGainedCountField;
import runesmith.powers.UnlimitedPowerPower;
import runesmith.relics.CoreCrystal;

public class ElementsCounter extends ClickableUIElement {
    private static Texture ELEMENTS_RED_LAYER1 = ImageMaster.loadImage("runesmith/images/ui/elements/R1.png");
    private static Texture ELEMENTS_RED_LAYER2 = ImageMaster.loadImage("runesmith/images/ui/elements/R2.png");
    private static Texture ELEMENTS_RED_LAYER3 = ImageMaster.loadImage("runesmith/images/ui/elements/R3.png");
    private static Texture ELEMENTS_RED_MASK = ImageMaster.loadImage("runesmith/images/ui/elements/RMask.png");
    private static Texture ELEMENTS_GREEN_LAYER1 = ImageMaster.loadImage("runesmith/images/ui/elements/G1.png");
    private static Texture ELEMENTS_GREEN_LAYER2 = ImageMaster.loadImage("runesmith/images/ui/elements/G2.png");
    private static Texture ELEMENTS_GREEN_LAYER3 = ImageMaster.loadImage("runesmith/images/ui/elements/G3.png");
    private static Texture ELEMENTS_GREEN_MASK = ImageMaster.loadImage("runesmith/images/ui/elements/GMask.png");
    private static Texture ELEMENTS_BLUE_LAYER1 = ImageMaster.loadImage("runesmith/images/ui/elements/B1.png");
    private static Texture ELEMENTS_BLUE_LAYER2 = ImageMaster.loadImage("runesmith/images/ui/elements/B2.png");
    private static Texture ELEMENTS_BLUE_LAYER3 = ImageMaster.loadImage("runesmith/images/ui/elements/B3.png");
    private static Texture ELEMENTS_BLUE_MASK = ImageMaster.loadImage("runesmith/images/ui/elements/BMask.png");
    private static Texture ELEMENTS_FRAME = ImageMaster.loadImage("runesmith/images/ui/elements/Frame.png");

    public static float fontScale = 0.6F;
    private float rFontScale = fontScale;
    private float gFontScale = fontScale;
    private float bFontScale = fontScale;

    private static final float ELEMENTS_IMG_SCALE = 1.15F * Settings.scale;

    private static final float baseX = 198.0F * Settings.scale;
    private static final float baseY = 320.0F * Settings.scale;
    private float x = baseX;
    private float y = baseY;
    private static float textX = 198.0F * Settings.scale;
    private static float textY = 320.0F * Settings.scale;
    private static float hb_w = 70.0F * ELEMENTS_IMG_SCALE;
    private static float hb_h = 96.0F * ELEMENTS_IMG_SCALE;

    private Rectangle bound = new Rectangle(20.0F * Settings.scale, 160.0F * Settings.scale, 220.0F * Settings.scale, 180.0F * Settings.scale);

    private static int IMG_DIM = 256;

    private static String[] IgnisText = CardCrawlGame.languagePack.getUIString("Runesmith:IgnisElement").TEXT;
    private static String[] TerraText = CardCrawlGame.languagePack.getUIString("Runesmith:TerraElement").TEXT;
    private static String[] AquaText = CardCrawlGame.languagePack.getUIString("Runesmith:AquaElement").TEXT;
    private static int MAX_ELEMENTS = 10;
    private boolean checkMax = false;
    private float angle1 = 0.0F;
    private float angle2 = 0.0F;
    private float angle3 = 0.0F;
    private int ignisCount;
    private int terraCount;
    private int aquaCount;
    private Hitbox ignisHitbox;
    private Hitbox terraHitbox;
    private Hitbox aquaHitbox;
    private float currentYOffset = 0.0F;
    private static float ignisH = 31.0F * ELEMENTS_IMG_SCALE;
    private static float terraH = 34.0F * ELEMENTS_IMG_SCALE;
    private FrameBuffer fbo;
    private SpriteBatch sb2;

    private boolean isDragging = false;
    private float mouseOffsetX = 0;
    private float mouseOffsetY = 0;
    //Elements data for player
    private static int ignis = 0, terra = 0, aqua = 0;
//    public static String IGNIS_ID = "IGNIS_ID", TERRA_ID = "TERRA_ID", AQUA_ID = "AQUA_ID";

    public enum Elements {
        IGNIS,
        TERRA,
        AQUA
    }

    public static int getIgnis() {
        return ignis;
    }

    public static int getTerra() {
        return terra;
    }

    public static int getAqua() {
        return aqua;
    }

    public static int getElementByID(Elements id) {
        switch (id) {
            case IGNIS:
                return ignis;
            case TERRA:
                return terra;
            case AQUA:
                return aqua;
            default:
                return 0;
        }
    }

    public static void applyElements(int ignis, int terra, int aqua) {
        AbstractPlayer p = AbstractDungeon.player;
        if (ignis > 0 || terra > 0 || aqua > 0) {
            int totalElementsGain = 0;
            int bonus = 0;
            if (p.hasPower(UnlimitedPowerPower.POWER_ID)) {
                AbstractPower unlimitedPow = p.getPower(UnlimitedPowerPower.POWER_ID);
                unlimitedPow.flash();
                bonus = unlimitedPow.amount;
                bonus = Math.max(0, bonus);
            }

            double multiplier = (p.hasRelic(CoreCrystal.ID)) ? 1.5 : 1;
            if (ignis > 0) {
                ignis += bonus;
                totalElementsGain += (ignis *= multiplier);
            }
            if (terra > 0) {
                terra += bonus;
                totalElementsGain += (terra *= multiplier);
            }
            if (aqua > 0) {
                aqua += bonus;
                totalElementsGain += (aqua *= multiplier);
            }
            ElementsGainedCountField.elementsCount.set(p, ElementsGainedCountField.elementsCount.get(p) + totalElementsGain);
        }

        if (ignis != 0)
            ElementsCounter.ignis = limitElementBound(ElementsCounter.ignis + ignis);
        if (terra != 0)
            ElementsCounter.terra = limitElementBound(ElementsCounter.terra + terra);
        if (aqua != 0)
            ElementsCounter.aqua = limitElementBound(ElementsCounter.aqua + aqua);

        p.hand.group.forEach(AbstractCard::applyPowers);
    }

    public static void applyElements(Elements id, int amount) {
        switch (id) {
            case IGNIS:
                applyElements(amount, 0, 0);
                break;
            case TERRA:
                applyElements(0, amount, 0);
                break;
            case AQUA:
                applyElements(0, 0, amount);
        }
    }

    private static int limitElementBound(int element) {
        int maxElements = (AbstractDungeon.player.hasRelic(CoreCrystal.ID)) ? CoreCrystal.MAX_ELEMENTS : RunesmithMod.DEFAULT_MAX_ELEMENTS;
        if (element > maxElements)
            return maxElements;
        return Math.max(element, 0);
    }

    public static void resetElements() {
        ignis = 0;
        terra = 0;
        aqua = 0;
    }

    public ElementsCounter(Texture image) {
        super(image, baseX, baseY, hb_w, hb_h);
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, IMG_DIM, IMG_DIM, false, false);
        this.ignisHitbox = new Hitbox(baseX - hb_w / 2, baseY + terraH / 2, hb_w, ignisH);
        this.terraHitbox = new Hitbox(baseX - hb_w / 2, baseY - terraH / 2, hb_w, terraH);
        this.aquaHitbox = new Hitbox(baseX - hb_w / 2, baseY - terraH / 2 - ignisH, hb_w, ignisH);
        this.setClickable(false);

        sb2 = new SpriteBatch(20);
        Matrix4 matrix = new Matrix4();
        matrix.setToOrtho2D(0, 0, IMG_DIM, IMG_DIM);
        sb2.setProjectionMatrix(matrix);
    }

    public void render(SpriteBatch sb, float current_x) {
        float x2 = current_x + x - baseX;
        updateHitboxPosition(x2, y);
//        textX = current_x;
        sb.end();

        this.fbo.begin();
        {
            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glColorMask(true, true, true, true);
            sb2.begin();
            {
                sb2.setColor(Color.WHITE);
                sb2.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb2.draw(ELEMENTS_RED_LAYER1, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, 0.0F, 0, 0, 256, 256, false, false);
                sb2.draw(ELEMENTS_RED_LAYER2, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, this.angle2, 0, 0, 256, 256, false, false);
                sb2.draw(ELEMENTS_RED_LAYER3, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, this.angle1, 0, 0, 256, 256, false, false);
                sb2.setBlendFunction(0, 770);
                sb2.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                sb2.draw(ELEMENTS_RED_MASK, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, 0.0F, 0, 0, 256, 256, false, false);
                sb2.setBlendFunction(770, 771);
            }
            sb2.end();
        }
        this.fbo.end();

        sb.begin();
        sb.draw(this.fbo.getColorBufferTexture(), x2 - 128.0F - Settings.VERT_LETTERBOX_AMT, y - 128.0F - Settings.HORIZ_LETTERBOX_AMT, 128.0F, 128.0F, IMG_DIM, IMG_DIM, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, true);
        sb.end();

        this.fbo.begin();
        {
//            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//            Gdx.gl.glColorMask(true, true, true, true);
            sb2.begin();
            {
                sb2.setColor(Color.WHITE);
                sb2.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb2.draw(ELEMENTS_GREEN_LAYER1, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, 0.0F, 0, 0, 256, 256, false, false);
                sb2.draw(ELEMENTS_GREEN_LAYER2, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, this.angle3, 0, 0, 256, 256, false, false);
                sb2.draw(ELEMENTS_GREEN_LAYER3, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, this.angle2, 0, 0, 256, 256, false, false);
                sb2.setBlendFunction(0, 770);
                sb2.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                sb2.draw(ELEMENTS_GREEN_MASK, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, 0.0F, 0, 0, 256, 256, false, false);
                sb2.setBlendFunction(770, 771);
            }
            sb2.end();
        }
        this.fbo.end();

        sb.begin();
        sb.draw(this.fbo.getColorBufferTexture(), x2 - 128.0F - Settings.VERT_LETTERBOX_AMT, y - 128.0F - Settings.HORIZ_LETTERBOX_AMT, 128.0F, 128.0F, IMG_DIM, IMG_DIM, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, true);
        sb.end();

        this.fbo.begin();
        {
//            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//            Gdx.gl.glColorMask(true, true, true, true);
            sb2.begin();
            {
                sb2.setColor(Color.WHITE);
                sb2.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb2.draw(ELEMENTS_BLUE_LAYER1, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, 0.0F, 0, 0, 256, 256, false, false);
                sb2.draw(ELEMENTS_BLUE_LAYER2, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, this.angle2, 0, 0, 256, 256, false, false);
                sb2.draw(ELEMENTS_BLUE_LAYER3, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, this.angle1, 0, 0, 256, 256, false, false);
                sb2.setBlendFunction(0, 770);
                sb2.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                sb2.draw(ELEMENTS_BLUE_MASK, 0, 0, 128.0F, 128.0F, IMG_DIM, IMG_DIM, 1, 1, 0.0F, 0, 0, 256, 256, false, false);
                sb2.setBlendFunction(770, 771);
            }
            sb2.end();
        }
        this.fbo.end();

        sb.begin();
        sb.draw(this.fbo.getColorBufferTexture(), x2 - 128.0F - Settings.VERT_LETTERBOX_AMT, y - 128.0F - Settings.HORIZ_LETTERBOX_AMT, 128.0F, 128.0F, IMG_DIM, IMG_DIM, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, true);

        sb.draw(ELEMENTS_FRAME, x2 - 128.0F - Settings.VERT_LETTERBOX_AMT, y - 128.0F - Settings.HORIZ_LETTERBOX_AMT, 128.0F, 128.0F, IMG_DIM, IMG_DIM, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);

        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, this.ignisCount + "/" + MAX_ELEMENTS, x2, y + 31.0F * ELEMENTS_IMG_SCALE, Color.WHITE, rFontScale);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontGreen, this.terraCount + "/" + MAX_ELEMENTS, x2, y, Color.WHITE, gFontScale);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, this.aquaCount + "/" + MAX_ELEMENTS, x2, y - 31.0F * ELEMENTS_IMG_SCALE, Color.WHITE, bFontScale);
    }

    @Override
    protected void onHover() {
        if (InputHelper.justClickedLeft && !isDragging) {
            onClick();
        }
    }

    private void onMouseRelease() {
        isDragging = false;
        mouseOffsetX = 0;
        mouseOffsetY = 0;
    }

    @Override
    protected void onUnhover() {
//        if (!InputHelper.isMouseDown)
//        {
//            isDragging = false;
//            mouseOffsetX = 0;
//            mouseOffsetY = 0;
//        }
    }

    @Override
    protected void onClick() {
        isDragging = true;
        mouseOffsetX = x - InputHelper.mX;
        mouseOffsetY = y - InputHelper.mY;
    }

    private void onIgnisHover() {
        if (isDragging) return;
        TipHelper.renderGenericTip(x + 55.0F * Settings.scale, y + 23.0F * Settings.scale, IgnisText[0], IgnisText[1]);
    }

    private void onTerraHover() {
        if (isDragging) return;
        TipHelper.renderGenericTip(x + 55.0F * Settings.scale, y + 23.0F * Settings.scale, TerraText[0], TerraText[1]);
    }

    private void onAquaHover() {
        if (isDragging) return;
        TipHelper.renderGenericTip(x + 55.0F * Settings.scale, y + 23.0F * Settings.scale, AquaText[0], AquaText[1]);
    }

    private void updatePosition(float x, float y) {
        this.x = MathUtils.clamp(x, bound.x, bound.x + bound.width);
        this.y = MathUtils.clamp(y, bound.y, bound.y + bound.height);
    }

    private void updateHitboxPosition(float x, float y) {
        ignisHitbox.translate(x - hb_w / 2, y + terraH / 2);
        terraHitbox.translate(x - hb_w / 2, y - terraH / 2);
        aquaHitbox.translate(x - hb_w / 2, y - terraH / 2 - ignisH);
    }

//    public void setYOffset(float yOffset){
//        updatePosition(baseX, baseY + yOffset);
//    }

    @Override
    protected void updateHitbox() {
        ignisHitbox.update();
        terraHitbox.update();
        aquaHitbox.update();
        hitbox.update();
    }

    public static void setMaxElements(int elem) {
        MAX_ELEMENTS = elem;
    }

    @Override
    public void update() {
        this.angle1 += Gdx.graphics.getDeltaTime() * -20.0F;
        this.angle2 += Gdx.graphics.getDeltaTime() * 10.0F;
        this.angle3 += Gdx.graphics.getDeltaTime() * -8.0F;
        if (rFontScale != fontScale) {
            rFontScale = MathHelper.scaleLerpSnap(rFontScale, fontScale);
        }
        if (gFontScale != fontScale) {
            gFontScale = MathHelper.scaleLerpSnap(gFontScale, fontScale);
        }
        if (bFontScale != fontScale) {
            bFontScale = MathHelper.scaleLerpSnap(bFontScale, fontScale);
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
//            super.update();
            updateHitbox();
            if (this.ignisHitbox.hovered) {
                onHover();
                onIgnisHover();
            } else if (this.terraHitbox.hovered) {
                onHover();
                onTerraHover();
            } else if (this.aquaHitbox.hovered) {
                onHover();
                onAquaHover();
            } else {
                onUnhover();
            }

            if (!InputHelper.isMouseDown) {
                onMouseRelease();
            }
//            if(!checkMax) {
//                if (AbstractDungeon.player.hasRelic(CoreCrystal.ID)) {
//                    MAX_ELEMENTS = 20;
//                    checkMax = true;
//                }
//            }
            int prev;
            prev = this.ignisCount;
            this.ignisCount = getIgnis();
            if (prev != this.ignisCount) rFontScale = 1.0F;
            prev = this.terraCount;
            this.terraCount = getTerra();
            if (prev != this.terraCount) gFontScale = 1.0F;
            prev = this.aquaCount;
            this.aquaCount = getAqua();
            if (prev != this.aquaCount) bFontScale = 1.0F;

            if (isDragging) {
                updatePosition(InputHelper.mX + mouseOffsetX, InputHelper.mY + mouseOffsetY);
            }
        }
    }
}
