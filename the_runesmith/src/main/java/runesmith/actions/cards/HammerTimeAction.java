package runesmith.actions.cards;

import static runesmith.patches.CardTagEnum.HAMMER;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.actions.EnhanceCard;

public class HammerTimeAction extends AbstractGameAction {
	
	private AbstractPlayer p;
	
	public HammerTimeAction() {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	
	@Override
	public void update() {
		if(this.duration == Settings.ACTION_DUR_FAST) {
			for (AbstractCard c : p.drawPile.group) {
				if (c.hasTag(HAMMER)) {
					EnhanceCard.enhance(c);
				}
			}
			for (AbstractCard c : p.hand.group) {
				if (c.hasTag(HAMMER)) {
					EnhanceCard.enhance(c);
					c.flash();
					c.applyPowers();
				}
			}
			for (AbstractCard c : p.discardPile.group) {
				if (c.hasTag(HAMMER)) EnhanceCard.enhance(c);
			}
		}
		tickDuration();
	}

}
