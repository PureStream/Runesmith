package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

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
			if(c.upgraded||EnhanceCountField.enhanceCount.get(c) > 0||CardStasisStatus.isStasis.get(c)) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
				DowngradeCard.downgrade(this.p.discardPile.group, c);
			}
		}
		for(AbstractCard c : this.p.drawPile.group) {
			if(c.upgraded||EnhanceCountField.enhanceCount.get(c) > 0||CardStasisStatus.isStasis.get(c)) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
				DowngradeCard.downgrade(this.p.drawPile.group, c);
			}
		}
		for(AbstractCard c : this.p.hand.group) {
			if(c.upgraded||EnhanceCountField.enhanceCount.get(c) > 0||CardStasisStatus.isStasis.get(c)) {
				AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
				DowngradeCard.downgrade(this.p.hand.group, c);
			}
		}
		this.isDone=true;
	}
/*
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
	}*/
	
}
