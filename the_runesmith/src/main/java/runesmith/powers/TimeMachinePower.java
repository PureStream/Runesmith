package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TimeMachinePower extends AbstractPower {

	public static final String POWER_ID = "Runesmith:TimeMachinePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private int health, block, ignis, terra, aqua;
	
	public TimeMachinePower(AbstractCreature owner, int health, int block, int ignis, int terra, int aqua) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/TimeMachine.png"), 0, 0, 84, 84);  
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/TimeMachineSmall.png"), 0, 0, 32, 32); 
	    this.setValues(health, block, ignis, terra, aqua);
	    updateDescription();
	}
	
	public void updateDescription() {
		this.description = DESCRIPTIONS[0]+this.health+DESCRIPTIONS[1]+this.block+DESCRIPTIONS[2]+this.ignis+
				DESCRIPTIONS[3]+this.terra+DESCRIPTIONS[4]+this.aqua+DESCRIPTIONS[5];
	}
	
	public int[] getValues() {
		int intArr[] = {this.health,this.block,this.ignis,this.terra,this.aqua};
		return intArr;
	}
	
	public void setValues(int health, int block, int ignis, int terra, int aqua) {
	    this.health = health;
	    this.block = block;
	    this.ignis = ignis;
	    this.terra = terra;
	    this.aqua = aqua;
	    updateDescription();
	    this.flash();
	}
}
