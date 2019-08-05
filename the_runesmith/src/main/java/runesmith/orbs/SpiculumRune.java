package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.ThornsPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.powers.SpiculumDownPower;

public class SpiculumRune extends RuneOrb {
    public static int basePotency = 3;

    private static Texture img1, img2, img3, img4;
    private static Texture img5, img6, img7, img8;

    private AnimCycle anim = new AnimCycle(30F* Settings.scale, 0.9f);

    private static float slope1 = (float) Math.tan(Math.toRadians(-70));
    private static float slope2 = (float) Math.tan(Math.toRadians(43));
    private static float slope3 = (float) Math.tan(Math.toRadians(-27));

    public static final Logger logger = LogManager.getLogger(SpiculumRune.class.getName());

    private AbstractPlayer p = AbstractDungeon.player;

    private boolean[] drawloc = new boolean[4];

    public SpiculumRune(int potential) {
        super("Spiculum",
                false,
                potential);
        this.showPotentialValue = true;
        this.useMultiBreak = true;

        if(img1 == null){
            img1 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic1.png");
            img2 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic2.png");
            img3 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic3.png");
            img4 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic4.png");
            img5 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic5.png");
            img6 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic6.png");
            img7 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic7.png");
            img8 = ImageMaster.loadImage("runesmith/images/orbs/Spiculum/Spic8.png");
        }

        int rune_count;

        if(potential < 6){
            rune_count = 1;
        }else if(potential < 10){
            rune_count = 2;
        }else if(potential < 16){
            rune_count = 3;
        }else{
            rune_count = 4;
        }

        int remaining_slots = 4;
        for (int i = 0; i < rune_count; i++) {
            int loc = MathUtils.random(0,remaining_slots-1);
            for (int j = 0; j < drawloc.length; j++) {
                if(!drawloc[j]){
                    if(loc == 0){
                        drawloc[j] = true;
                        break;
                    }else{
                        loc--;
                    }
                }
            }
            remaining_slots--;
        }

//        if(potential > getOverchargeAmt()){
//            isOvercharged = true;
//        }
    }

    @Override
    public void onCraft() {
        super.onCraft();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential), this.potential));
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential), this.potential));
        //logger.info("Applying Spiculum Down");
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SpiculumDownPower(p, this.potential * 2), this.potential * 2));
        this.activateEffect();
    }

    @Override
    public void onMultiBreak() {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential * 2), this.potential * 2));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new SpiculumDownPower(p, this.potential * 2), this.potential * 2));
        this.activateEffect();
    }

    @Override
    public void onRemove() {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, "Thorns", this.potential));
    }


    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        float ft1 = anim.y;
        float ft2 = anim.yi;

        float dX1 = - ft1 / (1 - slope1);
        float dY1 = ft1 / (1 - 1 / slope1);
        float dX2 = ft1;
        float dX3 = ft2 / (1 + slope2);
        float dY3 = ft2 / (1 + 1 / slope2);
        float dX4 = - ft2 / (1 - slope3);
        float dY4 = ft2 / (1 - 1 / slope3);

        if(drawloc[0])
        sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[1])
            sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[1])
            sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX2
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[0]) {
            sb.draw(img4, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX1
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[2]) {
            sb.draw(img5, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX3
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[3])
        sb.draw(img6, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX4
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY4
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[3])
            sb.draw(img7, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX4
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY4
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[2])
            sb.draw(img8, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX3
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        renderOthers(sb);
    }

    @Override
    public void updateAnimation() {
        this.anim.update();
        super.updateAnimation();
    }

    private class AnimCycle{
        private float dist, speed, time;
        private boolean rev;
        private Interpolation anim = Interpolation.circle;
        float y, yi;

        AnimCycle(float dist, float speed){
            this.dist = dist;
            this.speed = speed;
            this.time = MathUtils.random(0.0F, 1.0F);
            this.rev = MathUtils.randomBoolean();
        }

        public void update(){
            float prog = Math.min(1.0F, this.time);
            this.y = anim.apply(this.rev?1.0F-this.time:this.time) * dist;
            this.yi = dist - this.y;
            this.time += Gdx.graphics.getDeltaTime()*this.speed;
            if(this.time > 1.0F){
                this.time-=1.0F;
                this.rev = !this.rev;
            }
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new SpiculumRune(this.potential);
    }

//    public static int getOverchargeAmt(){
//        return OVERCHARGE_MULT * basePotency;
//    }
}