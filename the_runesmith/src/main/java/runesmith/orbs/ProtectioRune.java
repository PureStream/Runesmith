package runesmith.orbs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BobEffect;

public class ProtectioRune extends RuneOrb {

    public static final int basePotency = 4;

    private static Texture img1;
    private static Texture img2;
    private static Texture img3;
    private static Texture img4;
    private static Texture img5;
    private static Texture img6;

    private static float dist = 3.0F;

    private BobEffect extraBobEffect1 = new BobEffect(dist * Settings.scale, 2.0F);
    private float currOffset = 0.0F;
    private boolean negSlope = false;
    private static float slope1 = (float) Math.tan(Math.toRadians(0));
    private static float slope2 = (float) Math.tan(Math.toRadians(63));
    private static float slope3 = (float) Math.tan(Math.toRadians(130));
    private int status = MathUtils.random(0,2);

    private boolean[] drawloc = new boolean[6];

    public ProtectioRune(int potential) {
        super("Protectio",
                false,
                potential);

        if(img1 == null){
            img1 = ImageMaster.loadImage("runesmith/images/orbs/Protectio/Pro1.png");
            img2 = ImageMaster.loadImage("runesmith/images/orbs/Protectio/Pro2.png");
            img3 = ImageMaster.loadImage("runesmith/images/orbs/Protectio/Pro3.png");
            img4 = ImageMaster.loadImage("runesmith/images/orbs/Protectio/Pro4.png");
            img5 = ImageMaster.loadImage("runesmith/images/orbs/Protectio/Pro5.png");
            img6 = ImageMaster.loadImage("runesmith/images/orbs/Protectio/Pro6.png");
        }

        int rune_count;

        if(potential < 6){
            rune_count = 1;
        }else if(potential < 8){
            rune_count = 2;
        }else if(potential < 12){
            rune_count = 3;
        }else if(potential < 16){
            rune_count = 4;
        }else if(potential < 20){
            rune_count = 5;
        }else{
            rune_count = 6;
        }

        int remaining_slots = 6;
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
        if(potential > getOverchargeAmt()){
            isOvercharged = true;
        }
    }

    @Override
    public void onEndOfTurn() {
        this.activateEndOfTurnEffect();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.potential)
        );
    }

    @Override
    public void onBreak() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(
                new GainBlockAction(p, p, this.potential)
        );
        AbstractDungeon.actionManager.addToTop(
                new GainBlockAction(p, p, this.potential)
        );
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ProtectioRune(this.potential);
    }

    @Override
    public void render(SpriteBatch sb){
        sb.setColor(this.c);
        if(this.negSlope){
            if(this.extraBobEffect1.y - this.currOffset > 0.0F) {
                status = (this.status + 1) % 3;
                this.negSlope = false;
            }
        }else{
            if(this.extraBobEffect1.y - this.currOffset < 0.0F){
                this.negSlope = true;
            }
        }
        this.currOffset = this.extraBobEffect1.y;
        float dX1 = 0F, dY1 = 0F, dX2 = 0F, dY2 = 0F, dX3 = 0F, dY3 = 0F;
        float offset = this.extraBobEffect1.y + dist;
        switch(status){
            case 0: dX1 = offset / (1 + slope1);
                    //dY1 = - offset / (1 + 1 / slope1);
                    break;
            case 1: dX2 = offset / (1 + slope2);
                    dY2 = offset / (1 + 1 / slope2);
                    break;
            case 2: dX3 = - offset / (1 - slope3);
                    dY3 = offset / (1 - 1 / slope3);
                    break;
        }
        if(drawloc[0]) {
            sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 3.0F + dX1 + dX2 + dX3,
                    this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1 + dY2 + dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[1]) {
            sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 3.0F - dX1 + dX2 + dX3,
                    this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1 + dY2 + dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[2]) {
            sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 3.0F - dX1 - dX2 + dX3,
                    this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1 - dY2 + dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[3]) {
            sb.draw(img4, this.cX - 48.0F + this.bobEffect.y / 3.0F + dX1 + dX2 - dX3,
                    this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1 + dY2 - dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[4]) {
            sb.draw(img5, this.cX - 48.0F + this.bobEffect.y / 3.0F + dX1 - dX2 - dX3,
                    this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1 - dY2 - dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if(drawloc[5]) {
            sb.draw(img6, this.cX - 48.0F + this.bobEffect.y / 3.0F - dX1 - dX2 - dX3,
                    this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1 - dY2 - dY3
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        renderText(sb);
        this.hb.render(sb);
    }

    @Override
    public void updateAnimation() {
        this.extraBobEffect1.update();
        super.updateAnimation();
    }

    @Override
    public int getOverchargeAmt(){
        return OVERCHARGE_MULT * basePotency;
    }
}