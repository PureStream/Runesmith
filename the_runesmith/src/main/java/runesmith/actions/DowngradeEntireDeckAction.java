package runesmith.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.SearingBlow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import runesmith.cards.Runesmith.FieryHammer;

public class DowngradeEntireDeckAction extends AbstractGameAction{

	private AbstractPlayer p;
	
	public DowngradeEntireDeckAction(AbstractPlayer p) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = p;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	@Override
	public void update() {
		for(AbstractCard c : this.p.discardPile.group) {
			if(c.upgraded) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
				replaceCard(this.p.discardPile.group, c);
			}
		}
		for(AbstractCard c : this.p.drawPile.group) {
			if(c.upgraded) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
				replaceCard(this.p.drawPile.group, c);
			}
		}
		for(AbstractCard c : this.p.hand.group) {
			if(c.upgraded) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
				replaceCard(this.p.hand.group, c);
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