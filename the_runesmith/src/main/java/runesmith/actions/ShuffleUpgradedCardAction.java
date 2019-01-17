package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ShuffleUpgradedCardAction extends AbstractGameAction {
	private AbstractPlayer p;
	
	public ShuffleUpgradedCardAction() {
		this.p = AbstractDungeon.player;
		setValues(this.p, AbstractDungeon.player, this.amount);
		this.actionType = ActionType.CARD_MANIPULATION;
	}
	
	public void update() {
		if (this.p.discardPile.size() > 0) {
			for (AbstractCard card : this.p.discardPile.group) {
				if (card.upgraded) {
					p.drawPile.addToRandomSpot(card);
					p.discardPile.removeCard(card);
				}
			}
		}
		AbstractDungeon.actionManager.addToBottom(new ShuffleAction(p.drawPile, false));
		tickDuration();
		this.isDone = true;
	}
}