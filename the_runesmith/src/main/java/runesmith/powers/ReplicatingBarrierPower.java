package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.cards.Runesmith.CraftProtectio;

public class ReplicatingBarrierPower extends AbstractPower {
	
	public static final String POWER_ID = "Runesmith:ReplicatingBarrierPower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
//	private static final int TERRA_AMT = 2;
//	private int POT_AMT = 4;

	public ReplicatingBarrierPower(AbstractCreature owner) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = 0;
		updateDescription();
		this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Ignis.png"), 0, 0, 84, 84);
	    this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/IgnisSmall.png"), 0, 0, 32, 32);
	}
	
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
	}
	
	public void atStartOfTurnPostDraw() {
		this.amount += 1;
		if (this.amount == 2) {
			flash();
			this.amount = 0;
			AbstractRunicCard tmp = new CraftProtectio();
//			if (tmp.checkElements(0,TERRA_AMT,0)) {
//				AbstractPlayer p = AbstractDungeon.player;
//				if (p.hasPower("Runesmith:PotentialPower"))
//					POT_AMT += p.getPower("Runesmith:PotentialPower").amount;
//				AbstractDungeon.actionManager.addToBottom(
//						new RuneChannelAction(
//								new ProtectioRune(POT_AMT)));
//				POT_AMT = 4;
//			}
			AbstractDungeon.player.limbo.addToBottom(tmp);
			tmp.freeToPlayOnce = true;
			tmp.purgeOnUse = true;
			AbstractDungeon.actionManager.cardQueue.add(new com.megacrit.cardcrawl.cards.CardQueueItem(tmp, null, tmp.energyOnUse));
		}
	}

	public void updateDescription() {
		int inTurn = (amount==0) ? 2 : 1;
		if (inTurn == 2)
			this.description = (DESCRIPTIONS[0] + inTurn + DESCRIPTIONS[1] + DESCRIPTIONS[3]);
		else
			this.description = (DESCRIPTIONS[0] + inTurn + DESCRIPTIONS[2] + DESCRIPTIONS[3]);
	}
	
}
