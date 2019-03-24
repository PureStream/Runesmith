package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IndustriaRune extends RuneOrb {

    private AbstractPlayer p;

    IndustriaRune() {
        this(false);
    }

    public IndustriaRune(Boolean upgraded) {
        super("Industria",
                upgraded,
                0);
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
