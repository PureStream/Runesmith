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

public class SpiculumDownPower extends AbstractPower {
	public static final String POWER_ID = "Runesmith:SpiculumDownPower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public SpiculumDownPower(AbstractCreature owner, int newAmount){
		this.name = NAME;
		this.ID = POWER_ID;
	    //this.priority = 4;
		this.owner = owner;
		this.amount = newAmount;
	    updateDescription();
	    this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/SpiculumDown.png"), 0, 0, 84, 84);
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/SpiculumDownSmall.png"), 0, 0, 32, 32);
	    this.type = AbstractPower.PowerType.DEBUFF;
	}
	
	public void atStartOfTurn(boolean isPlayer) {
//		this.flash();
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Thorns", this.amount));
	
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
	}
	
	  @Override
	  public void updateDescription()
	  {
	      this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
	  }
}
