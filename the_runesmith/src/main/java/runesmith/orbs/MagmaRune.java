package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BobEffect;
import runesmith.actions.runes.RandomFireDamageAction;

public class MagmaRune extends RuneOrb {

    public static final int basePotency = 4;

    private static Texture imgC;
    private static Texture img1;
    private static Texture img2;
    private static Texture img3;
    private static Texture img4;

    private BobEffect extraBobEffect1 = new BobEffect(22.0F * Settings.scale, 1F);

    private static float slope1 = (float) Math.tan(Math.toRadians(43));
    private static float slope2 = (float) Math.tan(Math.toRadians(-27));

    private boolean[] drawloc = new boolean[4];

    public MagmaRune(int potential) {
        super("Magma",
                false,
                potential);

        if(imgC == null){
            imgC = ImageMaster.loadImage("runesmith/images/orbs/Magma/MagmaC.png");
            img1 = ImageMaster.loadImage("runesmith/images/orbs/Magma/Magma1.png");
            img2 = ImageMaster.loadImage("runesmith/images/orbs/Magma/Magma2.png");
            img3 = ImageMaster.loadImage("runesmith/images/orbs/Magma/Magma3.png");
            img4 = ImageMaster.loadImage("runesmith/images/orbs/Magma/Magma4.png");
        }

        int rune_count;

        if(potential < 8){
            rune_count = 0;
        }else if(potential < 12){
            rune_count = 1;
        }else if(potential < 16){
            rune_count = 2;
        }else if(potential < 20){
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
    public void onEndOfTurn() {
        this.activateEndOfTurnEffect();
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToBottom(new RandomFireDamageAction(
                new DamageInfo(p, this.potential, DamageInfo.DamageType.THORNS)));
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.potential / 2)
        );
    }

    @Override
    public void onBreak() {
        AbstractPlayer p = AbstractDungeon.player;

        AbstractDungeon.actionManager.addToTop(
                new GainBlockAction(p, p, this.potential / 2)
        );
        AbstractDungeon.actionManager.addToTop(new RandomFireDamageAction(
                new DamageInfo(p, this.potential, DamageInfo.DamageType.THORNS)));

        AbstractDungeon.actionManager.addToTop(
                new GainBlockAction(p, p, this.potential / 2)
        );
        AbstractDungeon.actionManager.addToTop(new RandomFireDamageAction(
                new DamageInfo(p, this.potential, DamageInfo.DamageType.THORNS)));

        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new MagmaRune(this.potential);
    }

    @Override
    protected void renderText(SpriteBatch sb) {
        if (!this.showBreakValue && this.showPotentialValue) {
            //Block
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential / 2 + "", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F ,
                    new Color(0.4F, 0.5F, 0.9F, this.c.a), this.fontScale);
            //Damage
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential + "", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    this.c, this.fontScale);
        }else if(this.showBreakValue && this.showPotentialValue){
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential / 2 + "+" + this.potential / 2, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F ,
                    new Color(0.4F, 0.5F, 0.9F, this.c.a), this.fontScale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential + "+" + this.potential, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    this.c, this.fontScale);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        float ft = this.extraBobEffect1.y;
        float dX1 = ft / (1 + slope1);
        float dY1 = ft / (1 + 1 / slope1);
        float dX2 = - ft / (1 - slope2);
        float dY2 = ft / (1 - 1 / slope2);

        if (drawloc[0]) {
            sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX2
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY2
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if (drawloc[1]) {
            sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX1
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY1
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        sb.draw(imgC, this.cX - 48.0F + this.bobEffect.y / 4.0F
                , this.cY - 48.0F + this.bobEffect.y / 4.0F
                , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        if (drawloc[2]) {
            sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX2
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F + dY2
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        }
        if (drawloc[3]) {
            sb.draw(img4, this.cX - 48.0F + this.bobEffect.y / 4.0F - dX1
                    , this.cY - 48.0F + this.bobEffect.y / 4.0F - dY1
                    , 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);

        }
        renderOthers(sb);
    }

    @Override
    public void updateAnimation() {
        this.extraBobEffect1.update();
        super.updateAnimation();
    }

//    public static int getOverchargeAmt(){
//        return OVERCHARGE_MULT * basePotency;
//    }
}