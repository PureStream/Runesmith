package runesmith.ui;

import basemod.ClickableUIElement;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;
import runesmith.relics.CoreCrystal;

public class ElementsCounter extends ClickableUIElement {
    private static Texture ELEMENTS_RED_LAYER1 = ImageMaster.loadImage("images/ui/elements/R1.png");
    private static Texture ELEMENTS_RED_LAYER2 = ImageMaster.loadImage("images/ui/elements/R2.png");
    private static Texture ELEMENTS_RED_LAYER3 = ImageMaster.loadImage("images/ui/elements/R3.png");
    private static Texture ELEMENTS_RED_MASK = ImageMaster.loadImage("images/ui/elements/RMask.png");
    private static Texture ELEMENTS_GREEN_LAYER1 = ImageMaster.loadImage("images/ui/elements/G1.png");
    private static Texture ELEMENTS_GREEN_LAYER2 = ImageMaster.loadImage("images/ui/elements/G2.png");
    private static Texture ELEMENTS_GREEN_LAYER3 = ImageMaster.loadImage("images/ui/elements/G3.png");
    private static Texture ELEMENTS_GREEN_MASK = ImageMaster.loadImage("images/ui/elements/GMask.png");
    private static Texture ELEMENTS_BLUE_LAYER1 = ImageMaster.loadImage("images/ui/elements/B1.png");
    private static Texture ELEMENTS_BLUE_LAYER2 = ImageMaster.loadImage("images/ui/elements/B2.png");
    private static Texture ELEMENTS_BLUE_LAYER3 = ImageMaster.loadImage("images/ui/elements/B3.png");
    private static Texture ELEMENTS_BLUE_MASK = ImageMaster.loadImage("images/ui/elements/BMask.png");
    private static Texture ELEMENTS_FRAME = ImageMaster.loadImage("images/ui/elements/Frame.png");

    public static float fontScale = 0.6F;
    private float rFontScale = fontScale;
    private float gFontScale = fontScale;
    private float bFontScale = fontScale;

    private static final float ELEMENTS_IMG_SCALE = 1.15F;

    private static float x = 198.0F * Settings.scale;
    private static float baseY = 320.0F * Settings.scale;
    private static float y = 320.0F * Settings.scale;
    private static float textX = 198.0F * Settings.scale;
    private static float textY = 320.0F * Settings.scale;
    private static float hb_w = 70.0F * Settings.scale * ELEMENTS_IMG_SCALE;
    private static float hb_h = 96.0F * Settings.scale * ELEMENTS_IMG_SCALE;

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
    private static float ignisH = 31.0F * Settings.scale * ELEMENTS_IMG_SCALE;
    private static float terraH = 34.0F * Settings.scale * ELEMENTS_IMG_SCALE;
    private FrameBuffer fbo;

    public ElementsCounter(Texture image){
        super(image, x, y , hb_w, hb_h);
        this.fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Settings.WIDTH, Settings.HEIGHT, false, false);
        this.ignisHitbox = new Hitbox(x - hb_w/2,y + terraH/2, hb_w,ignisH);
        this.terraHitbox = new Hitbox(x - hb_w/2, y - terraH/2, hb_w,terraH);
        this.aquaHitbox = new Hitbox(x - hb_w/2,y - terraH/2 - ignisH, hb_w,ignisH);
        this.setClickable(false);
    }

    public void setYOffset(float yOffset){
        this.currentYOffset = yOffset;
        y = textY = baseY + yOffset;
        ignisHitbox.translate(ignisHitbox.x, baseY + terraH/2 + yOffset);
        terraHitbox.translate(terraHitbox.x, baseY - terraH/2 + yOffset);
        aquaHitbox.translate(aquaHitbox.x, baseY - terraH/2 - ignisH + yOffset);
    }


    public void render(SpriteBatch sb, float current_x){
        x = current_x;
        textX = current_x;
        sb.end();

        this.fbo.begin();
        {
            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            Gdx.gl.glColorMask(true, true, true, true);
            sb.begin();
            {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb.draw(ELEMENTS_RED_LAYER1, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                sb.draw(ELEMENTS_RED_LAYER2, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, this.angle2, 0, 0, 256, 256, false, false);
                sb.draw(ELEMENTS_RED_LAYER3, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(0, 770);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                sb.draw(ELEMENTS_RED_MASK, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            sb.end();
        }
        this.fbo.end();

        sb.begin();
        TextureRegion drawTex = new TextureRegion(this.fbo.getColorBufferTexture());
        drawTex.flip(false, true);
        sb.draw(drawTex, 0.0F, 0.0F);
//        sb.draw(this.fbo.getColorBufferTexture(), 0.0F, 0.0F,Settings.WIDTH,Settings.HEIGHT,0.0F,0.0F,1.0F,1.0F,0.0F,0,0,Settings.WIDTH,Settings.HEIGHT,false,true );
        sb.end();

        this.fbo.begin();
        {
//            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//            Gdx.gl.glColorMask(true, true, true, true);
            sb.begin();
            {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb.draw(ELEMENTS_GREEN_LAYER1, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                sb.draw(ELEMENTS_GREEN_LAYER2, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, this.angle3, 0, 0, 256, 256, false, false);
                sb.draw(ELEMENTS_GREEN_LAYER3, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, this.angle2, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(0, 770);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                sb.draw(ELEMENTS_GREEN_MASK, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            sb.end();
        }
        this.fbo.end();

        sb.begin();
//        sb.draw(this.fbo.getColorBufferTexture(), 0.0F, (float) (-Settings.HEIGHT) + 2.0F * y);
        drawTex = new TextureRegion(this.fbo.getColorBufferTexture());
        drawTex.flip(false, true);
        sb.draw(drawTex, 0.0F, 0.0F);
        sb.end();

        this.fbo.begin();
        {
//            Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
//            Gdx.gl.glColorMask(true, true, true, true);
            sb.begin();
            {
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(-1, -1);//disable spritebatch blending override
                Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb.draw(ELEMENTS_BLUE_LAYER1, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                sb.draw(ELEMENTS_BLUE_LAYER2, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, this.angle2, 0, 0, 256, 256, false, false);
                sb.draw(ELEMENTS_BLUE_LAYER3, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(0, 770);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                sb.draw(ELEMENTS_BLUE_MASK, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                sb.setBlendFunction(770, 771);
            }
            sb.end();
        }
        this.fbo.end();

        sb.begin();
//        sb.draw(this.fbo.getColorBufferTexture(), 0.0F, (float) (-Settings.HEIGHT) + 2.0F * y);
        drawTex = new TextureRegion(this.fbo.getColorBufferTexture());
        drawTex.flip(false, true);
        sb.draw(drawTex, 0.0F, 0.0F);

        sb.draw(ELEMENTS_FRAME, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ELEMENTS_IMG_SCALE, ELEMENTS_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);

        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontRed, this.ignisCount+"/"+MAX_ELEMENTS, textX, textY + 31.0F * Settings.scale * ELEMENTS_IMG_SCALE, Color.WHITE, rFontScale);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontGreen, this.terraCount+"/"+MAX_ELEMENTS, textX, textY, Color.WHITE, gFontScale);
        FontHelper.renderFontCentered(sb, FontHelper.energyNumFontBlue, this.aquaCount+"/"+MAX_ELEMENTS, textX, textY - 31.0F * Settings.scale * ELEMENTS_IMG_SCALE, Color.WHITE, bFontScale);
    }

    @Override
    protected void onHover() {
//        InputHelper.mX
//        TipHelper.renderGenericTip(300.0F * Settings.scale, 350.0F * Settings.scale, IgnisText[0], IgnisText[1]);
    }

    @Override
    protected void onUnhover() {

    }

    @Override
    protected void onClick() {

    }

    private void onIgnisHover(){
        TipHelper.renderGenericTip(300.0F * Settings.scale, 350.0F * Settings.scale + currentYOffset, IgnisText[0], IgnisText[1]);
    }

    private void onTerraHover(){
        TipHelper.renderGenericTip(300.0F * Settings.scale, 350.0F * Settings.scale + currentYOffset, TerraText[0], TerraText[1]);
    }

    private void onAquaHover(){
        TipHelper.renderGenericTip(300.0F * Settings.scale, 350.0F * Settings.scale + currentYOffset, AquaText[0], AquaText[1]);
    }

    @Override
    protected void updateHitbox(){
        ignisHitbox.update();
        terraHitbox.update();
        aquaHitbox.update();
    }

    public static void setMaxElements(int elem){
        MAX_ELEMENTS = elem;
    }

    @Override
    public void update(){
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
        if(CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT )
        {
//            super.update();
            updateHitbox();
            if(this.ignisHitbox.hovered){
                onIgnisHover();
            } else if (this.terraHitbox.hovered) {
                onTerraHover();
            } else if (this.aquaHitbox.hovered){
                onAquaHover();
            }
//            if(!checkMax) {
//                if (AbstractDungeon.player.hasRelic(CoreCrystal.ID)) {
//                    MAX_ELEMENTS = 20;
//                    checkMax = true;
//                }
//            }
            int prev;
            prev = this.ignisCount;
            this.ignisCount = p.hasPower(IgnisPower.POWER_ID)? p.getPower(IgnisPower.POWER_ID).amount:0;
            if(prev != this.ignisCount) rFontScale = 1.0F;
            prev = this.terraCount;
            this.terraCount = p.hasPower(TerraPower.POWER_ID)? p.getPower(TerraPower.POWER_ID).amount:0;
            if(prev != this.terraCount) gFontScale = 1.0F;
            prev = this.aquaCount;
            this.aquaCount = p.hasPower(AquaPower.POWER_ID)? p.getPower(AquaPower.POWER_ID).amount:0;
            if(prev != this.aquaCount) bFontScale = 1.0F;
        }
    }
}
