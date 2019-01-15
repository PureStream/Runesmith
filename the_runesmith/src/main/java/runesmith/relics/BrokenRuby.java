package runesmith.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import runesmith.RunesmithMod;
import runesmith.powers.IgnisPower;

public class BrokenRuby extends CustomRelic {
	
	public static final String ID = "BrokenRuby";
	private static final String IMG = ""; //<--------- Need some img
	private static final int IGNIS_AMT = 1;
	private static final int NUM_CARDS = 3;
	
	public BrokenRuby() {
		super(ID, IMG, RelicTier.STARTER, LandingSound.MAGICAL); 
	
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void atBattleStart() {
		this.counter = 0;
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
		new IgnisPower(AbstractDungeon.player, IGNIS_AMT)));
	}
   
	public void onUseCard(AbstractCard card, UseCardAction action){
		if (card.type == AbstractCard.CardType.ATTACK) {
			this.counter += 1;
			if (this.counter % NUM_CARDS == 0) {
				this.counter = 0;
				flash();
				RunesmithMod.logger.info("MiniHakkero : Applying ChargeUpPower for using card : " + card.cardID);
				AbstractDungeon.actionManager.addToTop(
				          new ApplyPowerAction(
				              AbstractDungeon.player,
				              AbstractDungeon.player,
				              new IgnisPower(AbstractDungeon.player, IGNIS_AMT),
				              1
				          )
				      );
				AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
				
			}
		}
	}

	public void onVictory() {
		this.counter = -1;
	}

	public AbstractRelic makeCopy() {
		return new BrokenRuby();
	}
	
}
