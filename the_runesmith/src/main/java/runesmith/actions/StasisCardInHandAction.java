package runesmith.actions;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

import runesmith.RunesmithMod;
import runesmith.patches.CardStasisStatus;


public class StasisCardInHandAction extends AbstractGameAction{
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:StasisAction");
	public static final String[] TEXT = uiStrings.TEXT;

	private AbstractPlayer p;
	private ArrayList<AbstractCard> cannotStasis = new ArrayList<>();
	
	public StasisCardInHandAction(AbstractPlayer p, int amount) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = p;
		this.duration = Settings.ACTION_DUR_FAST;
		this.amount = amount;
	}

	private boolean canStasis(AbstractCard c) {
		return !CardStasisStatus.isStatis.get(c);
	}
	
	@Override
	public void update() {
		if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST) {
			//check if hand empty
			if (this.p.hand.isEmpty()) {
				this.isDone = true;
				return; }
			
			//get list of card that can't be stasis
			for(AbstractCard c : p.hand.group) {
				if(!canStasis(c)) {
					this.cannotStasis.add(c);
				}
			}
			
			//stasis every card if amount is at least the number of stasis-able card
			if (this.p.hand.size() - this.cannotStasis.size() <= this.amount) {
				for(AbstractCard c : p.hand.group) {
					if(canStasis(c)) CardStasisStatus.isStatis.set(c, true);
					c.superFlash(RunesmithMod.BEIGE);
					this.isDone = true;
					return;
				}
			}
		
			this.p.hand.group.removeAll(this.cannotStasis);
			
			AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
			tickDuration();
			return;
			
		}
		
			
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				CardStasisStatus.isStatis.set(c, true);
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
		for (AbstractCard c : this.cannotStasis) {
			this.p.hand.addToTop(c);
		}
		this.p.hand.refreshHandLayout();
	}
}
