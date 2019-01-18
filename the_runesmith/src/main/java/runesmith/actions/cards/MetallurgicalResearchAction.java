package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.actions.EnhanceCard;

public class MetallurgicalResearchAction extends AbstractGameAction {
	
	public MetallurgicalResearchAction() {
		this.duration = Settings.ACTION_DUR_MED;
		this.actionType = ActionType.WAIT;
	}
	
	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			AbstractPlayer p = AbstractDungeon.player;
	
			upgradeAndEnhanceAllCardsInGroup(p.hand);
			upgradeAndEnhanceAllCardsInGroup(p.drawPile);
			upgradeAndEnhanceAllCardsInGroup(p.discardPile);
			
			this.isDone = true;
		}
	}
	
	private void upgradeAndEnhanceAllCardsInGroup(CardGroup cardGroup) {
		for (AbstractCard c : cardGroup.group) {
			if (c.canUpgrade()) {
				if (cardGroup.type == CardGroup.CardGroupType.HAND) {
					c.superFlash();
				}
				c.upgrade();
				c.applyPowers();
			}
			EnhanceCard.enhance(c);
		}
	}
}
