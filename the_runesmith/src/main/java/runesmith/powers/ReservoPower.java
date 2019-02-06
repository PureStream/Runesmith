package runesmith.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class ReservoPower extends AbstractPower {
    public static final String POWER_ID = "Runesmith:ReservoPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public int permaRetain = 0;

    public ReservoPower(int amount) {
        this.name = NAME;
        this.ID = ("Runesmith:ReservoPower");
        this.owner = AbstractDungeon.player;
        this.amount = amount;
        loadRegion("retain");
        updateDescription();
        this.priority = 4;
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.hasPower("Retain Cards")) {
            this.owner.getPower("Retain Cards").amount -= this.permaRetain;
        }
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (this.owner.hasPower("Retain Cards")) {
            this.permaRetain = this.amount;
            this.owner.getPower("Retain Cards").amount += this.amount;
            this.amount = 0;
        } else if ((isPlayer) && (!AbstractDungeon.player.hand.isEmpty()) && (!AbstractDungeon.player.hasRelic("Runic Pyramid")) &&
                (!AbstractDungeon.player.hasPower("Equilibrium"))) {
            AbstractDungeon.actionManager.addToTop(new RetainCardsAction(this.owner, this.amount));
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        } else {
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
        }
    }
}
