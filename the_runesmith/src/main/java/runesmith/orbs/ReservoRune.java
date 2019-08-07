package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import runesmith.powers.ReservoPower;

public class ReservoRune extends RuneOrb {

    private AbstractPlayer p = AbstractDungeon.player;

    private static Texture img11, img2, img3, img12;

    private static float dist1 = 18.0F * Settings.scale, dist2 = 16.0F * Settings.scale, dist3 = 10.0F * Settings.scale;

    private float xOffset = 8.0f * Settings.scale, yOffset = -10.0f * Settings.scale;

    private AnimCycle anim = new AnimCycle(1.5f, 1f, 0.5f);

//    private static float slope1 = (float) Math.tan(Math.toRadians(-70));
    private static float cos1 = MathUtils.cosDeg(-70);
    private static float sin1 = MathUtils.sinDeg(-70);
    private static float cos2 = MathUtils.cosDeg(-50);
    private static float sin2 = MathUtils.sinDeg(-50);
//    private static float slope2 = (float) Math.tan(Math.toRadians(-50));

    public ReservoRune(boolean upgraded) {
        super("Reservo",
                upgraded,
                0);
        if(upgraded){
            this.xOffset = 0f;
            this.yOffset = 0f;
        }
        if(img11 == null){
            img11 = ImageMaster.loadImage("runesmith/images/orbs/Reservo/Res11.png");
            img2 = ImageMaster.loadImage("runesmith/images/orbs/Reservo/Res2.png");
            img3 = ImageMaster.loadImage("runesmith/images/orbs/Reservo/Res3.png");
            img12 = ImageMaster.loadImage("runesmith/images/orbs/Reservo/Res12.png");
        }

        this.showPotentialValue = false;
        this.useMultiBreak = true;
        this.tc = Color.GREEN.cpy();
    }

    public ReservoRune() {
        this(false);
    }

    @Override
    public void onCraft() {
        super.onCraft();
        int amount = 1;
        if (this.upgraded) {
            amount = 2;
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RetainCardPower(p, amount), amount));
    }

    @Override
    public void onRemove() {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, RetainCardPower.POWER_ID, 1));
        }
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, RetainCardPower.POWER_ID, 1));
    }

    @Override
    public void onBreak() {
        int amount = 2;
        if (this.upgraded) {
            amount = 4;
        }
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, RetainCardPower.POWER_ID, 1));
        }
        AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, RetainCardPower.POWER_ID, 1));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ReservoPower(amount), amount));
        this.activateEffect();
    }

    @Override
    public void onMultiBreak() {
        int amount = 2;
        if (this.upgraded) {
            amount = 4;
        }
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ReservoPower(amount), amount));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ReservoRune(this.upgraded);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        float ft1 = anim.y * -dist1;
        float ft2 = 0f, ft3 = 0f;
        if(upgraded) {
            ft2 = anim.y * dist2;
            ft3 = anim.y * dist3;
        }
        float dX1 = ft1 * cos1;
        float dY1 = ft1 * sin1;
        float dX2 = 0;
        float dY2 = 0;
        float dX3 = 0;
        float dY3 = 0;
        if(upgraded) {
            dX2 = ft2 * cos2;
            dY2 = ft2 * sin2;
            dX3 = ft3 * cos1;
            dY3 = ft3 * sin1;
        }else{
            dX3 = - ft1 * cos1;
            dY3 = - ft1 * sin1;
        }

        if(upgraded)
        sb.draw(img11, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1 + xOffset
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1 + yOffset
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX3 + xOffset
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY3 + yOffset
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        if(upgraded)
        sb.draw(img12, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        renderOthers(sb);
    }

    private class AnimCycle{
        private float time1, time2;
        float y;
        private float totalTime, currTime, animTime;
        private Interpolation anim1 = Interpolation.pow2In;
        private Interpolation anim2 = Interpolation.bounceIn;

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
                        "4", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
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