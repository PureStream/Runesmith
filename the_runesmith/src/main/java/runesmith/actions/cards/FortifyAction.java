package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import runesmith.RunesmithMod;
import runesmith.actions.EnhanceCard;

import java.util.ArrayList;

public class FortifyAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:FortifyAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotEnhance = new ArrayList<>();
    private boolean upgraded = false;

    public FortifyAction(boolean fortifyPlus) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = fortifyPlus;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            for (AbstractCard c : this.p.hand.group) {
                if (!EnhanceCard.canEnhance(c)) {
                    this.cannotEnhance.add(c);
                }
            }

            if (this.cannotEnhance.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (!this.upgraded) {
                CardGroup canEnhance = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                for (AbstractCard c : this.p.hand.group) {
                    if (EnhanceCard.canEnhance(c)) canEnhance.addToBottom(c);
                }
                if (!canEnhance.isEmpty()) {
                    AbstractCard selectedCard = canEnhance.getRandomCard(AbstractDungeon.cardRandomRng);
                    EnhanceCard.enhance(selectedCard);
                    selectedCard.superFlash(RunesmithMod.BEIGE);
                }
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotEnhance.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (EnhanceCard.canEnhance(c)) {
                        EnhanceCard.enhance(c);
//						c.upgrade();
                        c.superFlash(RunesmithMod.BEIGE);
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotEnhance);

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                EnhanceCard.enhance(this.p.hand.getTopCard());
//				this.p.hand.getTopCard().upgrade();
                this.p.hand.getTopCard().superFlash(RunesmithMod.BEIGE);
                returnCards();
                this.isDone = true;
            }

        }


        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                EnhanceCard.enhance(c);
//				c.upgrade();
                c.superFlash(RunesmithMod.BEIGE);
                this.p.hand.addToTop(c);
            }

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        tickDuration();
    }

    private boolean canEnhance(AbstractCard c) {
        if (c.type == CardType.CURSE || c.type == CardType.STATUS) return false;
        else return true;
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotEnhance) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}