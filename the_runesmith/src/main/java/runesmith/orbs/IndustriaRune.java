package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IndustriaRune extends RuneOrb {

    private AbstractPlayer p;

    public IndustriaRune() {
        super("Industria",
                false,
                0);
        this.passiveAmount = 1;
        p = AbstractDungeon.player;
    }

    @Override
    public void onStartOfTurn() {
        this.activateEndOfTurnEffect();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.passiveAmount));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.passiveAmount));
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.passiveAmount));
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.passiveAmount));
        AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.passiveAmount));
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(p, this.passiveAmount));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new IndustriaRune();
    }

}
