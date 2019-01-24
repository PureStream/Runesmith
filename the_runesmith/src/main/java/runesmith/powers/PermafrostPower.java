package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PermafrostPower extends AbstractPower {
	
	public static final String POWER_ID = "Runesmith:PermafrostPower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
//	private int atTurnEndBlock;

	public PermafrostPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.type = PowerType.BUFF;
//		this.atTurnEndBlock = 0;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Permafrost.png"), 0, 0, 84, 84);
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/PermafrostSmall.png"), 0, 0, 32, 32);
	}
	
//	public void atStartOfTurnPostDraw() {
//		int blockGainAmt = atTurnEndBlock/2;
//		if (owner.currentBlock < blockGainAmt) {
//			flash();
//			AbstractDungeon.actionManager.addToBottom(
//					new GainBlockAction(owner, owner, blockGainAmt-owner.currentBlock)
//			);
//		}
//		if (blockGainAmt > 0 && !owner.hasPower("Barricade") && !owner.hasPower("Blur")) {
//			if (owner.isPlayer && AbstractDungeon.player.hasRelic("Calipers")) {
//				if (owner.currentBlock < blockGainAmt) {
//					flash();
//					AbstractDungeon.actionManager.addToBottom(
//							new GainBlockAction(owner, owner, blockGainAmt-owner.currentBlock)
//					);
//				}
//			}
//			else {
//				flash();
//				AbstractDungeon.actionManager.addToBottom(
//						new GainBlockAction(owner, owner, blockGainAmt)
//				);
//			}
//		}
//	}
	
//	public void atEndOfRound() {
//		atTurnEndBlock = owner.currentBlock;
//	}

	public void updateDescription() {
		if (owner.isPlayer)
			this.description = DESCRIPTIONS[0];
		else
			this.description = DESCRIPTIONS[1];
	}
	
}
