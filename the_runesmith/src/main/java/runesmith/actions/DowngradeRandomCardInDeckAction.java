package runesmith.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import runesmith.cards.Runesmith.FieryHammer;

public class DowngradeRandomCardInDeckAction extends AbstractGameAction{

	private AbstractPlayer p;
	private CardGroup canDowngrade = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
	private boolean upgraded = false;
	
	public DowngradeRandomCardInDeckAction(AbstractPlayer p) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = p;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	@Override
	public void update() {
		for(AbstractCard c : this.p.discardPile.group) {
			if(c.upgraded) canDowngrade.addToBottom(c);
		}
		for(AbstractCard c : this.p.drawPile.group) {
			if(c.upgraded) canDowngrade.addToBottom(c);
		}
		for(AbstractCard c : this.p.hand.group) {
			if(c.upgraded) canDowngrade.addToBottom(c);
		}
		if(canDowngrade.size()>0) {
			AbstractCard selectedCard = canDowngrade.getRandomCard(AbstractDungeon.cardRandomRng);
			if(this.p.discardPile.group.indexOf(selectedCard)>=0) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(selectedCard));
				replaceCard(this.p.discardPile.group,selectedCard);
			}else if(this.p.drawPile.group.indexOf(selectedCard)>=0) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(selectedCard));
				replaceCard(this.p.drawPile.group,selectedCard);
			}else if(this.p.hand.group.indexOf(selectedCard)>=0) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(selectedCard));
				replaceCard(this.p.hand.group,selectedCard);
			}
		}
		this.isDone=true;
	}
	
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
	
}
