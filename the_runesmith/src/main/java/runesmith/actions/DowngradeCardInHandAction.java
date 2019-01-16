package runesmith.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import runesmith.RunesmithMod;

public class DowngradeCardInHandAction extends AbstractGameAction{
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:DowngradeAction");
	public static final String[] TEXT = uiStrings.TEXT;

	private AbstractPlayer p;
	private CardGroup canDowngrade = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
	private ArrayList<AbstractCard> cannotDowngrade = new ArrayList<>();
	private boolean random = false;
	
	public DowngradeCardInHandAction(AbstractPlayer p, boolean random) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = p;
		this.duration = Settings.ACTION_DUR_FAST;
		this.random = random;
	}
	
	@Override
	public void update() {
		for(AbstractCard c : this.p.hand.group) {
			if(c.upgraded) canDowngrade.addToBottom(c);
		}
		if(canDowngrade.size()>0) {
			if(this.duration == Settings.ACTION_DUR_FAST) {
				if(random) {
					AbstractCard selectedCard = canDowngrade.getRandomCard(AbstractDungeon.cardRandomRng);
					AbstractDungeon.effectList.add(new ExhaustCardEffect(selectedCard));
					replaceCard(this.p.hand.group, selectedCard);
					this.isDone = true;
					return;
				}
				
				for(AbstractCard c : this.p.hand.group) {
					if(!c.upgraded) cannotDowngrade.add(c);
				}
				
				if(this.p.hand.group.size() - this.cannotDowngrade.size() == 1) {
					for(AbstractCard c : this.p.hand.group) {
						if(c.upgraded) {
							AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
							replaceCard(this.p.hand.group, c);
							this.isDone = true;
							return;
						}
					}
				}
				
				this.p.hand.group.removeAll(this.cannotDowngrade);
	
				if (this.p.hand.group.size() > 1) {	
					AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
					tickDuration();
					return;
				}
				if(this.p.hand.group.size() == 1) {
					AbstractDungeon.effectList.add(new ExhaustCardEffect(this.p.hand.getTopCard()));
					replaceCard(this.p.hand.group, this.p.hand.getTopCard());
					returnCards();
					this.isDone = true;
				}
			}
			
			if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
				for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
					AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
					replaceCard(this.p.hand.group, c);
					//this.p.hand.addToTop(c);
				}
				
				returnCards();
				AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
				AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
				this.isDone = true;
			}
			
			tickDuration();
		}
		this.isDone=true;
	}
	
	private void replaceCard(ArrayList<AbstractCard> group, AbstractCard select) {
		int index = group.indexOf(select);
//		group.add(index, select.makeCopy());
//		group.remove(index+1);
		group.set(index, select.makeCopy());
	}
	
	
	private void returnCards() {
		for (AbstractCard c : this.cannotDowngrade) {
			this.p.hand.addToTop(c);
		}
		this.p.hand.refreshHandLayout();
	}
}
