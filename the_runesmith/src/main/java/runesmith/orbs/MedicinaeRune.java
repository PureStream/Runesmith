package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MedicinaeRune extends RuneOrb {

    public static final int basePotency = 3;

    public MedicinaeRune(int potential) {
        super("Medicinae",
                false,
                potential);

    }

    @Override
    public void onBreak() {
        AbstractPlayer p = AbstractDungeon.player;
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.potential));
        com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new HealAction(p, p, this.potential));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new MedicinaeRune(this.potential);
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
                    this.potential + "+" + this.potential, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    Color.WHITE.cpy(), this.fontScale);
        }
    }

}
