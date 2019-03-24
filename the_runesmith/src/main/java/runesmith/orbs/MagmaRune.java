package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.actions.runes.RandomFireDamageAction;

public class MagmaRune extends RuneOrb {

    public static final int basePotency = 4;

    public MagmaRune(int potential) {
        super("Magma",
                false,
                potential);

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
                    this.potential / 2 + "", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET,
                    new Color(0.4F, 0.5F, 0.9F, this.c.a), this.fontScale);
            //Damage
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential + "", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    this.c, this.fontScale);
        }else if(this.showBreakValue && this.showPotentialValue){
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential / 2 + "+" + this.potential / 2, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F - NUM_Y_OFFSET,
                    new Color(0.4F, 0.5F, 0.9F, this.c.a), this.fontScale);
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,
                    this.potential + "+" + this.potential, this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
                    this.c, this.fontScale);
        }
    }

}