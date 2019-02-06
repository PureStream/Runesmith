package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runesmith.cards.Runesmith.AbstractRunicCard;

public class PotentialPower extends AbstractPower {
	
	public static final String POWER_ID = "Runesmith:PotentialPower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	public boolean onVictory = false;
	public static final int AMOUNT_CAP = 999;

	public PotentialPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = PowerType.BUFF;
		this.canGoNegative = true;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Potential.png"), 0, 0, 84, 84);
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/PotentialSmall.png"), 0, 0, 32, 32);
	}
	
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}else if (this.amount > this.AMOUNT_CAP){
			this.amount = this.AMOUNT_CAP;
		}else if (this.amount < -this.AMOUNT_CAP){
			this.amount = -this.AMOUNT_CAP;
		}
		updatePotentialEffects();
	}
	
	public void reducePower(int reduceAmount) {
		this.fontScale = 8.0F;
		this.amount -= reduceAmount;
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
		}
		updatePotentialEffects();
	}
	
	public void onInitialApplication() {
		updatePotentialEffects();
	}

	public void onDrawOrDiscard() {
		updatePotentialEffects();
	}
	
	/*public void onAfterUseCard(AbstractCard card, UseCardAction action) {
		updatePotencialEffects();
	}*/

	private void updatePotentialEffects() {
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof AbstractRunicCard){
				((AbstractRunicCard) c).upgradePotency(0);
			}
		}
	}

	public void updateDescription() {
		if(this.amount>0) {
			this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
		}else {
			this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
		}
	}

	public void onVictory() {
		onVictory = true;
//		AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
	}
	
}
