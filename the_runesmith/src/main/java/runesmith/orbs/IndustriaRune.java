package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IndustriaRune extends RuneOrb {

    private AbstractPlayer p;

    private static Texture imgr1, imgr2, imgc1, imgc2, imgl1, imgl2;
    private static float dist = 12f;

    private AnimCycle anim = new AnimCycle(1.5f, 1.5f, 0.4f);

    IndustriaRune() {
        this(false);
    }

    public IndustriaRune(Boolean upgraded) {
        super("Industria",
                upgraded,
                0);

        if(imgr1 == null){
            imgr1 = ImageMaster.loadImage("runesmith/images/orbs/Industria/Indusr1.png");
            imgr2 = ImageMaster.loadImage("runesmith/images/orbs/Industria/Indusr2.png");
            imgc1 = ImageMaster.loadImage("runesmith/images/orbs/Industria/Indusc1.png");
            imgc2 = ImageMaster.loadImage("runesmith/images/orbs/Industria/Indusc2.png");
            imgl1= ImageMaster.loadImage("runesmith/images/orbs/Industria/Indusl1.png");
            imgl2 = ImageMaster.loadImage("runesmith/images/orbs/Industria/Indusl2.png");
        }
        this.passiveAmount = 1;
        p = AbstractDungeon.player;
        this.tc = Color.GREEN.cpy();
    }

    @Override
    public void onStartOfTurn() {
        this.activateEndOfTurnEffect();
        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.passiveAmount));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.passiveAmount));
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.passiveAmount));
        if (upgraded)
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.passiveAmount));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.passiveAmount));
        if (upgraded)
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.passiveAmount));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new IndustriaRune(this.upgraded);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        float dX = anim.y * dist;

        sb.draw(imgr1, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(imgl1, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        if(upgraded)
            sb.draw(imgc1, this.cX - 48.0F + this.bobEffect.y / 4.0F
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(imgr2, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(imgl2, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        if(upgraded)
            sb.draw(imgc2, this.cX - 48.0F + this.bobEffect.y / 4.0F
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        renderOthers(sb);
    }

    private class AnimCycle{
        private float time1, time2;
        float y;
        private float totalTime, currTime, animTime;
        private Interpolation anim1 = Interpolation.exp5In;
        private Interpolation anim2 = Interpolation.exp5In;

        AnimCycle(float time1, float time2, float cooldown){
            this.time1 = time1;
            this.time2 = time2;
            this.animTime = time1+time2;
            this.totalTime = animTime+cooldown;
            this.currTime = MathUtils.random(0.0f, totalTime);
        }

        public void update(){
            if(currTime < time1){
                this.y = anim1.apply(currTime/time1);
            }else if(currTime < animTime){
                this.y = anim2.apply(1.0f - (currTime - time1)/time2);
            }else{
                this.y = 0f;
            }
            //logger.info("t = "+ time +" y1 = " + this.y1 + "  y2 = " + this.y2);
            this.currTime += Gdx.graphics.getDeltaTime();

            if(this.currTime>totalTime){
                this.currTime-=totalTime;
            }
        }
    }

    @Override
    public void updateAnimation() {
        this.anim.update();
        super.updateAnimation();
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
