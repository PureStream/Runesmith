package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.utils.HSL;

public class PrismaticaRune extends RuneOrb {

    private ExtraBob extraBob = new ExtraBob(7f* Settings.scale, 2.5f, 120f);
    private ColorCycle colorCycle = new ColorCycle(4.0f);

    private static Texture img11, img21, img31, imgc;
    private static Texture img12, img22, img32;

    private static float[] cos = new float[3];
    private static float[] sin = new float[3];

    static {
        cos[0] = -MathUtils.cosDeg(70);
        sin[0] = MathUtils.sinDeg(70);
        cos[1] = MathUtils.cosDeg(23);
        sin[1] = -MathUtils.sinDeg(23);
        cos[2] = -MathUtils.cosDeg(-33);
        sin[2] = MathUtils.sinDeg(-33);
    }

    PrismaticaRune() {
        this(false);
    }

    public PrismaticaRune(boolean upgraded) {
        super("Prismatica",
                upgraded,
                0);
        this.tc = Color.GREEN.cpy();

        if(img11 == null){
            img11 = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Pris11.png");
            img21 = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Pris21.png");
            img31 = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Pris31.png");
            img12 = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Pris12.png");
            img22 = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Pris22.png");
            img32 = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Pris32.png");
            imgc = ImageMaster.loadImage("runesmith/images/orbs/Prismatica/Prisc.png");
        }
    }

    @Override
    public void onStartOfTurn() {
        this.activateEndOfTurnEffect();
        addRandomCardToHand();
    }

    @Override
    public void onBreak() {
        addRandomCardToHand(true);
        addRandomCardToHand(true);
        this.activateEffect();
    }

    private void addRandomCardToHand() {
        addRandomCardToHand(false);
    }

    private void addRandomCardToHand(boolean onBreak) {
        AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
        if (upgraded)
            tmp.upgrade();
        tmp.setCostForTurn(0);
        if (onBreak)
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(tmp, false));
        else
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(tmp, false));
    }

    @Override
    public AbstractOrb makeCopy() {
        return new PrismaticaRune(upgraded);
    }

    private float[] dX = new float[3], dY = new float[3];

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        for(int i=0;i<3;i++){
            dX[i] = this.extraBob.y[i] * cos[i];
            dY[i] = this.extraBob.y[i] * sin[i];
        }
        sb.draw(img11, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[0]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[0]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(img21, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[1]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[1]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(upgraded){
            sb.setColor(colorCycle.colorHue.toRGB());
            sb.draw(imgc, this.cX - 48.0F + this.bobEffect.y / 4.0F
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
            sb.setColor(Color.WHITE.cpy());
        }

        sb.draw(img31, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[2]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[2]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.setColor(colorCycle.colorHue.toRGB());
        sb.draw(img12, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[0]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[0]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(img22, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[1]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[1]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(img32, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[2]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[2]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        sb.setColor(Color.WHITE.cpy());
        renderOthers(sb);
    }

    @Override
    public void updateAnimation() {
        this.extraBob.update();
        this.colorCycle.update();
        super.updateAnimation();
    }

    private class ExtraBob{
        float dist, speed, sepDeg;
        float time;
        float[] y;

        ExtraBob(float dist, float speed, float sepDeg){
            this.dist = dist;
            this.speed = speed*57;
            this.sepDeg = sepDeg;
            this.time = MathUtils.random(0.0F, 360.0F);
            this.y = new float[3];
        }

        public void update(){
            this.y[0] = (1.0f + MathUtils.sinDeg(this.time)) * dist;
            this.y[1] = (1.0f + MathUtils.sinDeg(this.time + sepDeg)) * dist;
            this.y[2] = (1.0f + MathUtils.sinDeg(this.time + sepDeg + sepDeg)) * dist;
            this.time += Gdx.graphics.getDeltaTime() * this.speed;
            if(this.time>360F){
                this.time-=360;
            }
        }
    }

    private class ColorCycle{
        float cycleTime;
        float time;
//        Color color;
        HSL colorHue;

        ColorCycle(float cycleTime){
            this.time = MathUtils.random(0.0F, cycleTime);
            this.cycleTime = cycleTime;
            colorHue = new HSL(Color.RED);
        }

        public void update(){
            this.time += Gdx.graphics.getDeltaTime();
            if(this.time>cycleTime){
                this.time-=cycleTime;
            }
//            logger.info("time: "+ this.time +"||"+ this.time/this.cycleTime+"");
            colorHue.h = MathUtils.clamp(this.time / cycleTime, 0.0f, 1.0f);
        }
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        if (this.upgraded) {
            if(!this.showBreakValue) {
                //render upgrade +
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                        "+", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                        this.tc, this.fontScale);
            }else{
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                        "2+", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                        this.tc, this.fontScale);
            }
        }else{
            if(this.showBreakValue) {
                FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                        "2", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                        this.tc, this.fontScale);
            }
        }
    }
}
