package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import runesmith.orbs.RuneOrb;

public class RunesonancePower extends AbstractPower {

	public static final String POWER_ID = "Runesmith:RunesonancePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Runesmith:RunesonancePower");
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private AbstractPlayer p = AbstractDungeon.player;
	
	public RunesonancePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Runesonance.png"), 0, 0, 84, 84);  //<-------- NEED SOME IMG
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/RunesonanceSmall.png"), 0, 0, 32, 32); //<-------- NEED SOME IMG
	}
	
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount <= 0) {
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Runesmith:RunesonancePower"));
		}
	}
	
	@Override
	public void atStartOfTurn() {
		if (!p.orbs.isEmpty()) {
			flash();
			for(AbstractOrb o: p.orbs) {
				if(o instanceof RuneOrb) {
					o.onStartOfTurn();
				}
			}
		}
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Runesmith:RunesonancePower", 1));
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if(isPlayer) {
			if (!p.orbs.isEmpty()) {
				flash();
				for(AbstractOrb o: p.orbs) {
					if(o instanceof RuneOrb) {
						o.onEndOfTurn();
					}
				}
			}
		}
	}
	
	public void updateDescription() {
		if(this.amount == 1) {
			this.description = DESCRIPTIONS[0];
		}else {
			this.description = DESCRIPTIONS[1]+this.amount+DESCRIPTIONS[2];
		}
	}
	
}
