package runesmith.actions.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import runesmith.RunesmithMod;
import runesmith.actions.StasisCard;

public class FlexTapeAction extends AbstractGameAction{
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:FlexTapeAction");
	public static final String[] TEXT = uiStrings.TEXT;
	
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cannotStasis = new ArrayList<>();
	private int amount;
	
	public FlexTapeAction(int amount) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
		this.amount = amount;
	}
	
	public void update() {
		if(this.duration == Settings.ACTION_DUR_FAST) {

			for (AbstractCard c : this.p.hand.group) {
				if (!canStasis(c)) {
					this.cannotStasis.add(c);
				}
			}
			
			if(this.cannotStasis.size() == this.p.hand.group.size()) {
				this.isDone = true;
				return;
			}
			
			this.p.hand.group.removeAll(this.cannotStasis);
			
			if (this.p.hand.group.size() >= 1) {	
				AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false, false, false);
				tickDuration();
				return;
			}
		}
		
		
		if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				StasisCard.stasis(c);
				if (!c.isEthereal)
					c.retain = true;
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
	
	private boolean canStasis(AbstractCard c) {
		if(c.type == CardType.CURSE || c.type == CardType.STATUS) return false;
		else return true;
	}
	
	private void returnCards() {
		for (AbstractCard c : this.cannotStasis) {
			this.p.hand.addToTop(c);
		}
		this.p.hand.refreshHandLayout();
	}
}
