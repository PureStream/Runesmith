package runesmith.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import runesmith.RunesmithMod;
import runesmith.actions.ApplyElementsPowerAction;

public class BrokenRuby extends CustomRelic {
	
	public static final String ID = "Runesmith:BrokenRuby";
	private static final String IMG = "images/relics/BrokenRuby.png"; //<--------- Need some img
	private static final int IGNIS_AMT = 1;
	private static final int NUM_CARDS = 2;
	
	public BrokenRuby() {
		super(ID, ImageMaster.loadImage(IMG), RelicTier.STARTER, LandingSound.MAGICAL); 
	
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void atBattleStart() {
		this.counter = 0;
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(
				new ApplyElementsPowerAction(p,p,IGNIS_AMT,0,0));
	}
   
	public void onUseCard(AbstractCard card, UseCardAction action){
		AbstractPlayer p = AbstractDungeon.player;
		if (card.type == AbstractCard.CardType.ATTACK) {
			this.counter += 1;
			if (this.counter % NUM_CARDS == 0) {
				this.counter = 0;
				flash();
				RunesmithMod.logger.info("BrokenRuby : Applying Ignis for using "+ NUM_CARDS +" attack cards");
				AbstractDungeon.actionManager.addToTop(
						new ApplyElementsPowerAction(p,p,IGNIS_AMT,0,0));
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
