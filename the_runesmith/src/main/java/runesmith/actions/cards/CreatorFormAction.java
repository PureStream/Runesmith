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
import java.util.List;

public class CreatorFormAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:FortifyAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private List<AbstractCard> cannotEnhance = new ArrayList<>();
    private int cardNums;

    public CreatorFormAction(int cardNums) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardNums = cardNums;
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


            if (this.p.hand.group.size() - this.cannotEnhance.size() <= cardNums) {
                int enhanceCount = 0;
                for (AbstractCard c : this.p.hand.group) {
                    if (EnhanceCard.canEnhance(c)) {
                        enhanceCount++;
                        EnhanceCard.enhance(c);
                        c.superFlash(RunesmithMod.BEIGE);
//                        c.applyPowers();
                    }
                }
                if ((cardNums -= enhanceCount) > 0)
                    AbstractDungeon.actionManager.addToTop(new CreatorFormAction(cardNums));
                this.isDone = true;
                return;
            }

            this.p.hand.group.removeAll(this.cannotEnhance);

            if (this.p.hand.group.size() > cardNums) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], cardNums, false, false, false, false);
                tickDuration();
                return;
            }

            //if (this.p.hand.group.size() <= cardNums)
            int enhanceCount = 0;
            for (AbstractCard c : this.p.hand.group) {
                enhanceCount++;
                EnhanceCard.enhance(c);
//                c.applyPowers();
                this.p.hand.getTopCard().superFlash(RunesmithMod.BEIGE);
            }
            returnCards();
            if ((cardNums -= enhanceCount) > 0)
                AbstractDungeon.actionManager.addToTop(new CreatorFormAction(cardNums));
            this.isDone = true;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                EnhanceCard.enhance(c);
                c.superFlash(RunesmithMod.BEIGE);
                this.p.hand.addToTop(c);
//                c.applyPowers();
            }
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        tickDuration();
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotEnhance) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
