package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import runesmith.RunesmithMod;

import java.util.ArrayList;


public class StasisCardInHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:StasisAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotStasis = new ArrayList<>();

    private boolean isOptional;

    public StasisCardInHandAction(AbstractPlayer p, int amount) {
        this(p, amount, false);
    }

    public StasisCardInHandAction(AbstractPlayer p, int amount, boolean isOptional) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.isOptional = isOptional;
    }

    @Override
    public void update() {
        if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST) {
            //check if hand empty
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            //get list of card that can't be stasis
            p.hand.group.stream().filter(c -> !StasisCard.canStasis(c)).forEach(c -> this.cannotStasis.add(c));

            //if no card can be stasis, end the action.
            if (this.cannotStasis.size() == this.p.hand.size()) {
                this.isDone = true;
                return;
            }

            //stasis every card if amount is at least the number of stasis-able card
            if (!isOptional) {
                if (this.p.hand.size() - this.cannotStasis.size() <= this.amount) {
                    p.hand.group.stream().filter(StasisCard::canStasis).forEach(c -> {
                        StasisCard.stasis(c);
                        c.superFlash(RunesmithMod.BEIGE.cpy());
                    });
                    this.isDone = true;
                    return;
                }
            }

            //remove all can-stasis card from hand then open card select screen
            this.p.hand.group.removeAll(this.cannotStasis);

            if (!isOptional)
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
            else
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, true, false, false, true);
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.handCardSelectScreen.selectedCards.group.forEach(c -> {
                StasisCard.stasis(c);
                c.superFlash(RunesmithMod.BEIGE.cpy());
                this.p.hand.addToTop(c);
            });
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotStasis) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
