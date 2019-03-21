package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import runesmith.RunesmithMod;
import runesmith.actions.EnhanceCard;

import java.util.ArrayList;

public class DoubleUpAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:DoubleUpAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotUpgradeAndEnhance = new ArrayList<>();
    private int cardNums;

    public DoubleUpAction(int cardNums) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardNums = cardNums;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            for (AbstractCard c : this.p.hand.group) {
                if (!(c.canUpgrade() || EnhanceCard.canEnhance(c))) {
                    this.cannotUpgradeAndEnhance.add(c);
                }
            }

            if (this.cannotUpgradeAndEnhance.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotUpgradeAndEnhance.size() <= cardNums) {
                for (AbstractCard c : this.p.hand.group) {
                    if (c.canUpgrade() || EnhanceCard.canEnhance(c)) {
                        if (c.canUpgrade())
                            c.upgrade();
                        if (EnhanceCard.canEnhance(c))
                            EnhanceCard.enhance(c);
                        c.superFlash(RunesmithMod.BEIGE.cpy());
                    }
                }
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.cannotUpgradeAndEnhance);

            if (this.p.hand.group.size() > cardNums) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], cardNums, false, false, false, false);
                tickDuration();
                return;
            }

            this.p.hand.group.size();
            for (AbstractCard c : this.p.hand.group) {
                if (c.canUpgrade())
                    c.upgrade();
                if (EnhanceCard.canEnhance(c))
                    EnhanceCard.enhance(c);
                this.p.hand.getTopCard().superFlash(RunesmithMod.BEIGE.cpy());
            }
            returnCards();
            this.isDone = true;

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.canUpgrade())
                    c.upgrade();
                if (EnhanceCard.canEnhance(c))
                    EnhanceCard.enhance(c);
                c.superFlash(RunesmithMod.BEIGE.cpy());
                this.p.hand.addToTop(c);
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotUpgradeAndEnhance) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
