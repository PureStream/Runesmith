package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

import java.util.ArrayList;

public class DowngradeCardInHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:DowngradeAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private CardGroup canDowngrade = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    private ArrayList<AbstractCard> cannotDowngrade = new ArrayList<>();
    private boolean random = false;

    public DowngradeCardInHandAction(AbstractPlayer p, boolean random, int amount) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
        this.random = random;
        this.amount = amount;
    }

    public DowngradeCardInHandAction(AbstractPlayer p, boolean random) {
        this(p, random, 1);
    }

    @Override
    public void update() {
        for (AbstractCard c : this.p.hand.group) {
            if (c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c))
                canDowngrade.addToBottom(c);
        }
        if (canDowngrade.size() > 0) {
            if (this.duration == Settings.ACTION_DUR_FAST) {
                if (random) {
                    for (int i = 0; i < this.amount; i++) {
                        if (canDowngrade.size() > 0) {
                            AbstractCard selectedCard = canDowngrade.getRandomCard(AbstractDungeon.cardRandomRng);
                            canDowngrade.removeCard(selectedCard);
                            AbstractDungeon.effectList.add(new ExhaustCardEffect(selectedCard));
                            DowngradeCard.downgrade(this.p.hand.group, selectedCard);
                        }
                    }
                    this.isDone = true;
                    return;
                }

                for (AbstractCard c : this.p.hand.group) {
                    if (!(c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)))
                        cannotDowngrade.add(c);
                }

                if (this.p.hand.group.size() - this.cannotDowngrade.size() <= this.amount) {
                    for (AbstractCard c : this.p.hand.group) {
                        if (c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)) {
                            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                            DowngradeCard.downgrade(this.p.hand.group, c);
                        }
                    }
                    this.isDone = true;
                    return;
                }

                this.p.hand.group.removeAll(this.cannotDowngrade);

                if (this.p.hand.group.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false, false, false);
                    tickDuration();
                    return;
                }
                if (this.p.hand.group.size() <= this.amount) {
                    for (AbstractCard c : this.p.hand.group) {
                        AbstractDungeon.effectList.add(new ExhaustCardEffect(this.p.hand.getTopCard()));
                        DowngradeCard.downgrade(this.p.hand.group, this.p.hand.getTopCard());
                    }
                    returnCards();
                    this.isDone = true;
                }
            }

            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                    //DowngradeCard.downgrade(this.p.hand.group, c);
                    this.p.hand.addToTop(c.makeCopy());
                }

                returnCards();
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                this.isDone = true;
            }

            tickDuration();
        }
        this.isDone = true;
    }

	/*
	private void replaceCard(ArrayList<AbstractCard> group, AbstractCard select) {
		if(!((select instanceof SearingBlow)||(select instanceof FieryHammer))) {
			int index = group.indexOf(select);
			group.set(index, select.makeCopy());
		}else if(select instanceof SearingBlow){
			int index = group.indexOf(select);
			AbstractCard tmp = new SearingBlow();
			for(int i = 0; i < select.timesUpgraded - 1; i++) {
				tmp.upgrade();
			}
			group.set(index, tmp);
		}else if(select instanceof FieryHammer) {
			int index = group.indexOf(select);
			AbstractCard tmp = new FieryHammer();
			for(int i = 0; i < select.timesUpgraded - 1; i++) {
				tmp.upgrade();
			}
			group.set(index, tmp);
		}
	}
	*/

    private void returnCards() {
        for (AbstractCard c : this.cannotDowngrade) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
