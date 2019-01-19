package runesmith.actions.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import runesmith.RunesmithMod;
import runesmith.actions.EnhanceCard;

public class DoubleUpAction extends AbstractGameAction{
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:DoubleUpAction");
	public static final String[] TEXT = uiStrings.TEXT;
	
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
	private int cardNums = 1;
	
	public DoubleUpAction(boolean upgraded) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
		if (upgraded) cardNums = 2;
	}
	
	public void update() {
		if(this.duration == Settings.ACTION_DUR_FAST) {

			for (AbstractCard c : this.p.hand.group) {
				if (!c.canUpgrade()) {
					this.cannotUpgrade.add(c);
				}
			}
	
			if(this.cannotUpgrade.size() == this.p.hand.group.size()) {
				this.isDone = true;
				return;
			}
			
			if(this.p.hand.group.size() - this.cannotUpgrade.size() <= cardNums) {
				for(AbstractCard c : this.p.hand.group) {
					if(c.canUpgrade()) {
						c.upgrade();
						EnhanceCard.enhance(c);
						c.superFlash(RunesmithMod.BEIGE);
						this.isDone = true;
						return;
					}
				}
			}
			
			this.p.hand.group.removeAll(this.cannotUpgrade);
			
			if (this.p.hand.group.size() > cardNums) {	
				AbstractDungeon.handCardSelectScreen.open(TEXT[0], 2, false, false, false, false);
				tickDuration();
				return;
			}
			
			if(this.p.hand.group.size() <= cardNums) {
				for (AbstractCard c : this.p.hand.group) {
					c.upgrade();
					EnhanceCard.enhance(c);
					this.p.hand.getTopCard().superFlash(RunesmithMod.BEIGE);
					returnCards();
					this.isDone = true;
				}			
			}
			
		}
		
		if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				c.upgrade();
				EnhanceCard.enhance(c);
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
	
	private void returnCards() {
		for (AbstractCard c : this.cannotUpgrade) {
			this.p.hand.addToTop(c);
		}
		this.p.hand.refreshHandLayout();
	}
}
