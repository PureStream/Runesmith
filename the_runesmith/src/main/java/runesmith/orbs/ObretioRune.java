package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;
import runesmith.actions.runes.ObretioRuneAction;

public class ObretioRune extends RuneOrb {

    public static final Logger logger = LogManager.getLogger(ObretioRune.class.getName());

    public static final int basePotency = 3;

    private static Texture img1, img2, img3, img4;

    private static float dist1 = 10F * Settings.scale, dist2 = 20F * Settings.scale;

    private AnimCycle anim = new AnimCycle(dist1, dist2, 0.5F, 0.75F, 1.2F);

    private static float slope1 = (float) Math.tan(Math.toRadians(60));
    private static float slope2 = (float) Math.tan(Math.toRadians(-35));

    private static int TIER1 = 6;
    private static int TIER2 = 12;

    private boolean side;

    public ObretioRune(int potential) {
        super("Obretio",
                false,
                potential);

        if(img1 == null){
            img1 = ImageMaster.loadImage("runesmith/images/orbs/Obretio/Obre1.png");
            img2 = ImageMaster.loadImage("runesmith/images/orbs/Obretio/Obre2.png");
            img3 = ImageMaster.loadImage("runesmith/images/orbs/Obretio/Obre3.png");
            img4 = ImageMaster.loadImage("runesmith/images/orbs/Obretio/Obre4.png");
        }

        side = MathUtils.randomBoolean();

//        if(potential > getOverchargeAmt()){
//            isOvercharged = true;
//        }
    }

    @Override
    public void onEndOfTurn() {
        this.activateEndOfTurnEffect();
        AbstractDungeon.actionManager.addToBottom(new ObretioRuneAction(potential));
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(new ObretioRuneAction(potential));
        AbstractDungeon.actionManager.addToTop(new ObretioRuneAction(potential));
        this.activateEffect();
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        float ft1 = anim.y1 + dist1;
        float ft2 = anim.y2 + dist2;
        float dX1 = ft1 / (1 + slope1);
        float dY1 = ft1 / (1 + 1 / slope1);
        float dX2 = - ft2 / (1 - slope2);
        float dY2 = ft2 / (1 - 1 / slope2);

        if((potential >= TIER1 && side) || potential >= TIER2) {
            sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if((potential >= TIER1 && !side) || potential >= TIER2) {
            sb.draw(img4, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX1
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        renderOthers(sb);
    }

    @Override
    public void updateAnimation() {
        this.anim.update();
        super.updateAnimation();
    }

    private class AnimCycle{
        private float dist1, dist2, speed1, speed2, peak2Dist;
        float y1,y2;
        private float time;
        private float bound1, bound2, bound3, bound4;

        AnimCycle(float dist1, float dist2, float speed1, float speed2, float peak2Dist){
            this.dist1 = dist1;
            this.dist2 = dist2;
            this.speed1 = speed1;
            this.speed2 = speed2;
            this.peak2Dist = peak2Dist;
            this.time = MathUtils.random(0.0F, 2.0F/speed1);
            float len1 = 1/speed1;
            float len2 = 1/speed2;
            bound1 = len1-this.peak2Dist/2-len2/2;
            bound2 = len1-this.peak2Dist/2;
            bound3 = len1+this.peak2Dist/2;
            bound4 = len1+this.peak2Dist/2+len2/2;
            //logger.info("b1: " + bound1 + "b2: " + bound2 + "b3: " + bound3 + "b4: " + bound4);
        }

        public void update(){
            this.y1 = - MathUtils.cosDeg(this.time*this.speed1*360) * dist1;
            if(this.time>=bound1 && this.time<bound2) {
                this.y2 = - MathUtils.cosDeg((this.time-bound1)*this.speed2*360) * dist2;
            }else if(this.time>=bound2 && this.time<bound3){
                this.y2 = dist2;
            }else if(this.time>=bound3 && this.time<bound4) {
                this.y2 = MathUtils.cosDeg((this.time-bound3)*this.speed2*360) * dist2;
            }else{
                this.y2 = - dist2;
            }
            //logger.info("t = "+ time +" y1 = " + this.y1 + "  y2 = " + this.y2);
            this.time += Gdx.graphics.getDeltaTime();

            if(this.time>2F/speed1){
                this.time-=2F/speed1;
            }
        }
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ObretioRune(this.potential);
    }

//    public static int getOverchargeAmt(){
//        return OVERCHARGE_MULT * basePotency;
//    }
}