package runesmith.orbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MedicinaeRune extends RuneOrb {

    public static final int basePotency = 3;

    private static Texture img11, img21, img31, img41;
    private static Texture img12, img22, img32, img42;

    private static float deg = 70;

    private AnimCycle anim = new AnimCycle(1f);

    private static float unitX = 10f* Settings.scale;
    private static float unitY = 9.7f* Settings.scale;

    private boolean[] drawloc = new boolean[4];

    public MedicinaeRune(int potential) {
        super("Medicinae",
                false,
                potential);

        if(img11 == null){
            img11 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi11.png");
            img21 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi21.png");
            img31 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi31.png");
            img41 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi41.png");
            img12 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi12.png");
            img22 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi22.png");
            img32 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi32.png");
            img42 = ImageMaster.loadImage("runesmith/images/orbs/Medicinae/Medi42.png");
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
    public void onBreak() {
        AbstractPlayer p = AbstractDungeon.player;
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.potential));
        //com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.potential));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new MedicinaeRune(this.potential);
    }


    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        float dX1 = anim.x * unitX + anim.y * unitY * MathUtils.cosDeg(deg);
        float dY1 = - anim.y * unitY * MathUtils.sinDeg(deg);

        float dX2 = anim.y * unitX - anim.x * unitY * MathUtils.cosDeg(deg);
        float dY2 = anim.x * unitY * MathUtils.sinDeg(deg);

        if(drawloc[0])
        sb.draw(img11, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[1])
        sb.draw(img21, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[2])
        sb.draw(img31, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[0])
        sb.draw(img12, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[3])
        sb.draw(img41, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[1])
        sb.draw(img22, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[2])
        sb.draw(img32, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY2
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if(drawloc[3])
        sb.draw(img42, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1
                , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        renderOthers(sb);
    }

    @Override
    public void updateAnimation() {
        this.anim.update();
        super.updateAnimation();
    }

    private class AnimCycle{
        private float stepTime, progress;
        float x, y;
        private float time, cycleTime;
        private boolean rev;

        AnimCycle(float stepTime){
            this.stepTime = stepTime;
            this.progress = MathUtils.random(0f, stepTime*7);
            this.rev = MathUtils.randomBoolean();
            this.cycleTime = stepTime*7;
        }

        public void update(){
            float adjProg = progress/stepTime;
            if(adjProg < 0.5f){
                this.x = this.y = 0.0f;
            }else if(adjProg >= 0.5f && adjProg < 1.5f) {
                this.y = adjProg - 0.5f;
                this.x = 0f;
            }else if(adjProg >= 1.5f && adjProg < 3.5f){
                this.y = 1f;
                this.x = 1.5f - adjProg;
            } else if (adjProg >= 3.5f && adjProg < 5.5f){
                this.y = 4.5f - adjProg;
                this.x = -2f;
            }else if(adjProg >= 5.5f && adjProg <= 6.5f){
                this.y = -1f;
                this.x = adjProg - 7.5f;
            }else{
                this.x = this.y = -1f;
            }

            this.time += Gdx.graphics.getDeltaTime();

            if(this.time>stepTime*7f){
                this.time-=stepTime*7f;
                this.rev = !this.rev;
            }
            this.progress = this.rev?this.cycleTime - this.time:this.time;
        }
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        if (!this.showBreakValue && this.showPotentialValue) {
            //End of Turn
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    Integer.toString(this.potential / 2), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET,
                    new Color(0.05F, 0.92F, 0.05F, this.c.a), this.fontScale);
            //Current val
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    Integer.toString(this.potential), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    Color.WHITE.cpy(), this.fontScale);
        }else if(this.showBreakValue && this.showPotentialValue){
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    Integer.toString(this.potential / 2), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET,
                    new Color(0.05F, 0.92F, 0.05F, this.c.a), this.fontScale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential+"", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    new Color(0.05F, 0.92F, 0.05F, this.c.a), this.fontScale);
        }
    }

//    public static int getOverchargeAmt(){
//        return OVERCHARGE_MULT * basePotency;
//    }
}
