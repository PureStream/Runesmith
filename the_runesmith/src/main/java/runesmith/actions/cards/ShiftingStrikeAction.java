package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import runesmith.actions.DowngradeCard;
import runesmith.patches.EnhanceCountField;

import java.util.ArrayList;

public class ShiftingStrikeAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:ShiftingStrike");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private CardGroup canRemoveEnhance = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    private ArrayList<AbstractCard> cannotRemoveEnhance = new ArrayList<>();

    public ShiftingStrikeAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        for (AbstractCard c : this.p.hand.group) {
            if (EnhanceCountField.enhanceCount.get(c) > 0)
                canRemoveEnhance.addToBottom(c);
        }
        if (canRemoveEnhance.size() > 0) {
            if (this.duration == Settings.ACTION_DUR_FAST) {

                for (AbstractCard c : this.p.hand.group) {
                    if (!(EnhanceCountField.enhanceCount.get(c) > 0))
                        cannotRemoveEnhance.add(c);
                }

                this.p.hand.group.removeAll(this.cannotRemoveEnhance);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                tickDuration();
                return;
            }

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);

                int enhance_amt = EnhanceCountField.enhanceCount.get(c);
                EnhanceCountField.enhanceCount.set(c, 0);
                EnhanceCountField.lastEnhance.set(c, 0);
                EnhanceCountField.enhanceReset.set(c, true);
                c.initializeDescription();
                this.p.hand.addToTop(c);
                returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                AbstractDungeon.actionManager.addToBottom(new RefinementAction(enhance_amt, true, false));
                this.isDone = true;
            }

            tickDuration();
        }
        this.isDone = true;
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotRemoveEnhance)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}

