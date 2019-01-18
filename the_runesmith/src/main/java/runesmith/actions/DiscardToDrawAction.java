package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardToDrawAction extends AbstractGameAction {

	private AbstractCard card;
	
	public DiscardToDrawAction(AbstractCard card) {
		this.actionType = ActionType.CARD_MANIPULATION;
		this.card = card;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	public void update() {
		if (AbstractDungeon.player.discardPile.contains(this.card)) {
			AbstractDungeon.player.drawPile.addToRandomSpot(card);
			/*this.card.unhover();
			this.card.setAngle(0.0F, true);
			this.card.lighten(false);
			this.card.drawScale = 0.12F;
			this.card.targetDrawScale = 0.75F;
			this.card.applyPowers();*/
			AbstractDungeon.player.discardPile.removeCard(this.card);
		}
		tickDuration();
		this.isDone = true;
	}
}
