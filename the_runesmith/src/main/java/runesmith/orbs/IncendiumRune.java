package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IncendiumRune extends RuneOrb {

    public static final int basePotency = 4;

    private static Texture img1;
    private static Texture img2;
    private static Texture img3;

    private static int TIER1 = 8;
    private static int TIER2 = 16;

    private float slope = (float) Math.tan(Math.toRadians(70));

    private ExtraBob extraBob = new ExtraBob(10F* Settings.scale, 3F, 30F);

    public IncendiumRune(int potential) {
        super("Incendium",
                false,
                potential);

        if (img1 == null) {
            img1 = ImageMaster.loadImage("runesmith/images/orbs/Incendium/Incen1.png");
            img2 = ImageMaster.loadImage("runesmith/images/orbs/Incendium/Incen2.png");
            img3 = ImageMaster.loadImage("runesmith/images/orbs/Incendium/Incen3.png");
        }

        if(potential > getOverchargeAmt()){
            isOvercharged = true;
        }
    }

    @Override
    public void onEndOfTurn() {
        this.activateEndOfTurnEffect();
        //damage all enemies
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(this.potential, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(this.potential, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToTop(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(this.potential, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE));
        this.activateEffect();
    }

    float[] ft = new float[3],dX = new float[3], dY = new float[3];

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        ft[0] = this.extraBob.y;
        ft[1] = this.extraBob.y2;
        ft[2] = this.extraBob.y3;

        for(int i=0;i<3;i++){
            dX[i] = -ft[i]/(1+this.slope);
            dY[i] = ft[i]/(1+1/this.slope);
        }
        if(potential >= TIER1){
        sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[0]
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[0]
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(potential < TIER1 || potential >= TIER2) {
            sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[1]
                    ,this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[1]
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(potential >= TIER1) {
            sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX[2]
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY[2]
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }

       renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void updateAnimation() {
        this.extraBob.update();
        super.updateAnimation();
    }

    private class ExtraBob{
        float dist = 0, speed = 0, sepDeg = 0;
        float time;
        float y,y2,y3;

        ExtraBob(float dist, float speed, float sepDeg){
            this.dist = dist;
            this.speed = speed*57;
            this.sepDeg = sepDeg;
            this.time = MathUtils.random(0.0F, 359.0F);
            this.y = this.y2 = this.y3 = 0.0F;
        }

        public void update(){
            this.y = MathUtils.sinDeg(this.time) * dist;
            this.y2 = MathUtils.sinDeg(this.time + sepDeg) * dist;
            this.y3 = MathUtils.sinDeg(this.time + sepDeg + sepDeg) * dist;
            this.time += Gdx.graphics.getDeltaTime() * this.speed;
            if(this.time>360F){
                this.time-=360;
            }
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new IncendiumRune(this.potential);
    }

    @Override
    public int getOverchargeAmt(){
        return OVERCHARGE_MULT * basePotency;
    }
}
