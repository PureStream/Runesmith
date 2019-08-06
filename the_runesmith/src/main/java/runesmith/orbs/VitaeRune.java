package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.actions.RemoveRuneAction;
import runesmith.powers.PotentialDownPower;
import runesmith.powers.PotentialPower;

public class VitaeRune extends RuneOrb {

    public static final int basePotency = 6;

    private AbstractPlayer p = AbstractDungeon.player;

    public VitaeRune(int potency) {
        super("Vitae",
                false,
                potency);
    }

    public int losePotency(int lose){
        if(lose <= 0) return 0;
        if(lose >= this.potential){
            AbstractDungeon.actionManager.addToTop(new RemoveRuneAction(this));
            this.fontScale = 1.5f;
            return this.potential;
        }else{
           this.potential-=lose;
           this.fontScale = 1.5f;
           this.updateDescription();
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
