package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.actions.EnhanceCard;

public class GrandSlamAction extends AbstractGameAction {
	
	public GrandSlamAction() {
		this.duration = Settings.ACTION_DUR_MED;
		this.actionType = ActionType.WAIT;
	}
	
	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			AbstractPlayer p = AbstractDungeon.player;
			EnhanceAllCardsInGroup(p.hand);
			this.isDone = true;
		}
	}
	
	private void EnhanceAllCardsInGroup(CardGroup cardGroup) {
		for (AbstractCard c : cardGroup.group) {
			if (EnhanceCard.canEnhance(c)) {
				c.superFlash();
				EnhanceCard.enhance(c);
				c.applyPowers();
			}
		}
	}
	
}
