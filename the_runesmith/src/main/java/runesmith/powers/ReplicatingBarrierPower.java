package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import runesmith.actions.RuneChannelAction;
import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.cards.Runesmith.CraftProtectio;
import runesmith.orbs.ProtectioRune;

public class ReplicatingBarrierPower extends AbstractPower {
	
	public static final String POWER_ID = "Runesmith:ReplicatingBarrierPower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final int TERRA_AMT = 2;
	private int POT_AMT = 4;
	private static int RepBarrierIdOffset;

	public ReplicatingBarrierPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = (POWER_ID+RepBarrierIdOffset);
		RepBarrierIdOffset += 1;
		this.owner = owner;
		this.amount = 2;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/ReplicatingBarrier.png"), 0, 0, 84, 84);
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/ReplicatingBarrierSmall.png"), 0, 0, 32, 32);
	}
	
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
	}
	
	public void atStartOfTurnPostDraw() {
		this.amount -= 1;
		if (this.amount == 0) {
			flash();
			this.amount = 2;
			AbstractRunicCard tmp = new CraftProtectio();
			if (tmp.checkElements(0,TERRA_AMT,0)) {
				if (owner.hasPower("Runesmith:PotentialPower"))
					POT_AMT += owner.getPower("Runesmith:PotentialPower").amount;
				AbstractDungeon.actionManager.addToBottom(
						new RuneChannelAction(
								new ProtectioRune(POT_AMT)));
				POT_AMT = 4;
			}
//			AbstractDungeon.player.limbo.addToBottom(tmp);
//			tmp.freeToPlayOnce = true;
//			tmp.purgeOnUse = true;
//			AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmp, null, tmp.energyOnUse));
		}
		updateDescription();
	}

	public void updateDescription() {
		int shownPot = POT_AMT;
		if (owner.hasPower("Runesmith:PotentialPower"))
			shownPot += owner.getPower("Runesmith:PotentialPower").amount;
		if (amount == 2)
			this.description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + DESCRIPTIONS[3] + shownPot + DESCRIPTIONS[4]);
		else
			this.description = (DESCRIPTIONS[0] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3] + shownPot + DESCRIPTIONS[4]);
	}
	
}
