package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.actions.DiscardToDrawAction;

public class RearmAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
	private AbstractPlayer p;
	
	public RearmAction() {
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, this.amount);
		this.actionType = ActionType.CARD_MANIPULATION;
	}
	
	public void update() {
		if (this.p.discardPile.size() > 0) {
			int count = 0;
			for (AbstractCard card : this.p.discardPile.group) {
				if (card.upgraded) {
					count++;
					AbstractDungeon.actionManager.addToBottom(new DiscardToDrawAction(card));
				}
			}
			if (count > 0) {
				AbstractDungeon.actionManager.addToBottom(new ShuffleAction(p.drawPile, false));
			}			
		}
		tickDuration();
		this.isDone = true;
	}
}