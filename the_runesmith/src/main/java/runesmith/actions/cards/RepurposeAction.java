package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class RepurposeAction extends AbstractGameAction{
	private static final com.megacrit.cardcrawl.localization.UIStrings uiStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getUIString("RecycleAction");
	public static final String[] TEXT = uiStrings.TEXT;
	private AbstractPlayer p;
	private boolean upgraded = false;
	private int allAmt;
	private int allAmtPlus;
	
	public RepurposeAction(int amount, boolean RepurposePlus, int allAmt, int allAmtPlus) {
		setValues(this.target, source, amount);
		this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST;
		this.upgraded = RepurposePlus;
		this.allAmt = allAmt;
		this.allAmtPlus = allAmtPlus;
	}
	
	@Override
	public void update() {
		if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FAST) {
			if (this.p.hand.isEmpty()) {
				this.isDone = true;
				return; }
			if (this.p.hand.size() == 1) {
				gainElement(this.p.hand.getBottomCard());
				this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());
				tickDuration();
				return;
			}
			AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
			tickDuration();
			return;
		}
		
			
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				gainElement(c);
				this.p.hand.moveToExhaustPile(c);
			}
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
			AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
		}
			
		tickDuration();
	}
	
	private void gainElement(AbstractCard c) {
		switch(c.type) {
			case ATTACK :	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
								new IgnisPower(p, this.amount),this.amount));
							return;
			case SKILL :	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
								new TerraPower(p, this.amount),this.amount));
							return;
			case POWER : 	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
								new AquaPower(p, this.amount),this.amount));
							return;
			case STATUS : 	AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
								new IgnisPower(p, this.allAmt),this.allAmt));
							AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
									new TerraPower(p, this.allAmt),this.allAmt));
							AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
									new AquaPower(p, this.allAmt),this.allAmt));
							return;
			case CURSE : 	if(this.upgraded) {
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
										new IgnisPower(p, this.allAmt+this.allAmtPlus),this.allAmt+this.allAmtPlus));
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
										new TerraPower(p, this.allAmt+this.allAmtPlus),this.allAmt+this.allAmtPlus));
								AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
										new AquaPower(p, this.allAmt+this.allAmtPlus),this.allAmt+this.allAmtPlus));
							}
			default : return;
		}
	}
}
