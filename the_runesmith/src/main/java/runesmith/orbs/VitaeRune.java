package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.actions.RemoveRuneAction;
import runesmith.powers.PotentialDownPower;
import runesmith.powers.PotentialPower;

public class VitaeRune extends RuneOrb {

    public static final int basePotency = 5;

    private static Texture imgr, imgc1, imgc2, imgc3, imgl;

    private AnimCycle anim = new AnimCycle(1.2f, 0.5f, 0.25f, 0.2f);

    private AbstractPlayer p = AbstractDungeon.player;

    private static float dist = 12f * Settings.scale;
    private static float cos1 = MathUtils.cosDeg(110);
    private static float sin1 = MathUtils.sinDeg(110);

    private boolean[] drawLoc = new boolean[3];

    private static int TIER1 = 9, TIER2 = 15, TIER3 = 24;

    public VitaeRune(int potency) {
        super("Vitae",
                false,
                potency);
        if(imgr == null){
            imgr = ImageMaster.loadImage("runesmith/images/orbs/Vitae/Vitr.png");
            imgc1 = ImageMaster.loadImage("runesmith/images/orbs/Vitae/Vitc1.png");
            imgc2 = ImageMaster.loadImage("runesmith/images/orbs/Vitae/Vitc2.png");
            imgc3 = ImageMaster.loadImage("runesmith/images/orbs/Vitae/Vitc3.png");
            imgl = ImageMaster.loadImage("runesmith/images/orbs/Vitae/Vitl.png");
        }

        updateTier();
    }

    private void updateTier(){
        if(this.potential >= TIER1){
            drawLoc[0] = true;
        }
        if(this.potential >= TIER2){
            drawLoc[1] = true;
        }
        if(this.potential >= TIER3){
            drawLoc[2] = true;
        }
    }

    public int losePotency(int lose){
        if(lose <= 0 || this.potential <= 0) return 0;
        if(lose >= this.potential){
            AbstractDungeon.actionManager.addToTop(new RemoveRuneAction(this));
            this.fontScale = 1.5f;
            return this.potential;
        }else{
           this.potential-=lose;
           this.fontScale = 1.5f;
           this.updateDescription();
           this.updateTier();
           return lose;
        }
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p , p, this.potential));

        AbstractDungeon.actionManager.addToTop(new GainBlockAction(p , p, this.potential));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new VitaeRune(potential);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        float dX1 = anim.y1 * dist * cos1;
        float dY1 = anim.y1 * dist * sin1;
        float dX2 = anim.y2 * dist;

        if(drawLoc[0])
        sb.draw(imgr, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        if(drawLoc[2])
        sb.draw(imgc1, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(imgc2, this.cX - 48.0F + this.bobEffect.y / 4.0F
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        if(drawLoc[1])
        sb.draw(imgc3, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawLoc[0])
        sb.draw(imgl, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        renderOthers(sb);
    }


    private class AnimCycle{
        private float time1, time2, timeSep;
        float y1, y2;
        private float totalTime, currTime, animTime;
        private Interpolation anim1 = Interpolation.sineOut;
        private Interpolation anim2 = Interpolation.exp5In;

        AnimCycle(float time1, float time2, float cooldown, float y2SepTime){
            this.time1 = time1;
            this.time2 = time2;
            this.timeSep = y2SepTime;
            this.animTime = time1+time2;
            this.totalTime = animTime+cooldown;
            this.currTime = MathUtils.random(0.0f, totalTime);
        }

        public void update(){
            float currTimePlus = timeSep + currTime;
            if(currTimePlus > totalTime){
                currTimePlus -= totalTime;
            }

            if(currTime < time1){
                this.y1 = anim1.apply(currTime/time1);
            }else if(currTime < animTime){
                this.y1 = anim2.apply(1.0f - (currTime - time1)/time2);
            }else{
                this.y1 = 0f;
            }

            if(currTimePlus < time1){
                this.y2 = anim1.apply(currTimePlus/time1);
            }else if(currTimePlus < animTime){
                this.y2 = anim2.apply(1.0f - (currTimePlus - time1)/time2);
            }else{
                this.y2 = 0f;
            }

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
        if (!this.showBreakValue && this.showPotentialValue) {
            //Block
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential + "", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    new Color(0.93F, 0.85F, 0.18F, this.c.a), this.fontScale);
        }else if(this.showBreakValue && this.showPotentialValue){
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential + "+" + this.potential, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    new Color(0.4F, 0.5F, 0.9F, this.c.a), this.fontScale);
        }
    }

//    public static int getOverchargeAmt(){
//        return OVERCHARGE_MULT * basePotency;
//    }
}
