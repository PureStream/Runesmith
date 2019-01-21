package runesmith.actions.cards;

import static runesmith.patches.CardTagEnum.HAMMER;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.RunesmithMod;
import runesmith.actions.EnhanceCard;

public class HammerTimeAction extends AbstractGameAction {
	
	private AbstractPlayer p;
	private int enhanceNums;
	
	public HammerTimeAction(boolean upgraded) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
		enhanceNums = (upgraded) ? 2 : 1;
	}
	
	@Override
	public void update() {
		if(this.duration == Settings.ACTION_DUR_FAST) {
			for (AbstractCard c : p.drawPile.group) {
				if (c.hasTag(HAMMER)) {
					EnhanceCard.enhance(c,enhanceNums);
				}
			}
			for (AbstractCard c : p.hand.group) {
				if (c.hasTag(HAMMER)) {
					EnhanceCard.enhance(c,enhanceNums);
					c.superFlash(RunesmithMod.BEIGE);
					
				}
			}
			for (AbstractCard c : p.discardPile.group) {
				if (c.hasTag(HAMMER)) EnhanceCard.enhance(c,enhanceNums);
			}
		}
		tickDuration();
	}

}
