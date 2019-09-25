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
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

import java.util.ArrayList;

public class MallocAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:DowngradeAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private CardGroup canDowngrade = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    private ArrayList<AbstractCard> cannotDowngrade = new ArrayList<>();
    private boolean upgraded = false;
    private int drawAmt = 1;

    public MallocAction(AbstractPlayer p, boolean upgraded, int drawAmt) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
        this.drawAmt = drawAmt;
    }

    @Override
    public void update() {
        /*
        for (AbstractCard c : this.p.hand.group) {
            if (c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c))
                canDowngrade.addToBottom(c);
        }
        if (canDowngrade.size() > 0) {
            if (this.duration == Settings.ACTION_DUR_FAST) {

                for (AbstractCard c : this.p.hand.group) {
                    if (!(c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)))
                        cannotDowngrade.add(c);
                }

                this.p.hand.group.removeAll(this.cannotDowngrade);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.p.hand.group.size(), true, true);
                tickDuration();
                return;
            }

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                int exCount = 0;
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    exCount++;
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                    this.p.hand.addToTop(c);
                    DowngradeCard.downgrade(this.p.hand.group, c);
                }
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(exCount));
                returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
            }

            tickDuration();
        }*/
        for (AbstractCard c : this.p.hand.group) {
            if (c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c))
                canDowngrade.addToBottom(c);
        }
        if (canDowngrade.size() > 0) {
            if (this.duration == Settings.ACTION_DUR_FAST) {

                for (AbstractCard c : this.p.hand.group) {
                    if (!(c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)))
                        cannotDowngrade.add(c);
                }

                this.p.hand.group.removeAll(this.cannotDowngrade);

                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false);
                tickDuration();
                return;
            }

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
                int energy_amt = c.upgraded?1:0;
                int enhance_amt = EnhanceCountField.enhanceCount.get(c) * drawAmt;
                if(upgraded)
                    energy_amt *= 2;
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                this.p.hand.addToTop(c);
                DowngradeCard.downgrade(this.p.hand.group, c);
                if(energy_amt > 0)
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energy_amt));
                if(enhance_amt > 0)
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, enhance_amt));
                returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
            }

            tickDuration();
        }
        this.isDone = true;
    }

    private void returnCards() {
        for (AbstractCard c : this.cannotDowngrade)
            this.p.hand.addToTop(c);
        this.p.hand.refreshHandLayout();
    }
}
