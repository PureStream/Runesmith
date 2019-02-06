package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PoweredAnvilPower extends AbstractPower {

	public static final String POWER_ID = "Runesmith:PoweredAnvilPower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public PoweredAnvilPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.amount = amount;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Anvil.png"), 0, 0, 84, 84);  //<-------- NEED SOME IMG
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/AnvilSmall.png"), 0, 0, 32, 32); //<-------- NEED SOME IMG
	}

	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
	}
	
	public void updateDescription() {
		if(this.amount == 1) {
			this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
		}else{
			this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[2];
		}
	}
	
}
