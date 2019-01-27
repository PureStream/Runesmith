package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TimeMachinePower extends AbstractPower {

	public static final String POWER_ID = "Runesmith:TimeMachinePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private int health, block, ignis, terra, aqua;
	
	public TimeMachinePower(AbstractCreature owner, int health, int block, int ignis, int terra, int aqua, int turns) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.amount = turns;
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/TimeMachine.png"), 0, 0, 84, 84);  
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/TimeMachineSmall.png"), 0, 0, 32, 32); 
	    this.setValues(health, block, ignis, terra, aqua, turns);
	    updateDescription();
	}

	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.POWER_ID));
		}
	}

	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.POWER_ID, 1));
		}
	}

	public void updateDescription() {
		if(this.amount == 1) {
			this.description = DESCRIPTIONS[0] + this.health + DESCRIPTIONS[1] + this.block + DESCRIPTIONS[2] + this.ignis +
					DESCRIPTIONS[3] + this.terra + DESCRIPTIONS[4] + this.aqua + DESCRIPTIONS[5] + this.amount + DESCRIPTIONS[6];
		}else{
			this.description = DESCRIPTIONS[0] + this.health + DESCRIPTIONS[1] + this.block + DESCRIPTIONS[2] + this.ignis +
					DESCRIPTIONS[3] + this.terra + DESCRIPTIONS[4] + this.aqua + DESCRIPTIONS[5] + this.amount + DESCRIPTIONS[7];
		}
	}
	
	public int[] getValues() {
		int intArr[] = {this.health,this.block,this.ignis,this.terra,this.aqua};
		return intArr;
	}
	
	public void setValues(int health, int block, int ignis, int terra, int aqua, int amount) {
	    this.health = health;
	    this.block = block;
	    this.ignis = ignis;
	    this.terra = terra;
	    this.aqua = aqua;
	    this.amount = amount;
	    updateDescription();
	    this.flash();
	}
}
