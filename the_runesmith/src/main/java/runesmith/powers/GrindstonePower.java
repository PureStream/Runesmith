package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class GrindstonePower extends AbstractPower {

	public static final String POWER_ID = "GrindstonePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public GrindstonePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Ignis.png"), 0, 0, 84, 84);  //<-------- NEED SOME IMG
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/IgnisSmall.png"), 0, 0, 32, 32); //<-------- NEED SOME IMG
	}
	
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "GrindstonePower"));
		}
	}
	
	public void onAfterUseCard(AbstractCard card, UseCardAction action) {
		if (card.canUpgrade()) {
			flash();
			card.upgrade();
		}
	}
	
	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              owner,
			              owner,
			              new GrindstonePower(owner, -1),
			              -1
			          )
			      );
		}
	}
	
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}
	
}
