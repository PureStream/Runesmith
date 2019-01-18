package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static runesmith.patches.CardTagEnum.CRAFT;

public class DuplicatePower extends AbstractPower {

	public static final String POWER_ID = "Runesmith:DuplicatePower";
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public DuplicatePower(AbstractCreature owner, int amount) {
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
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Runesmith:DuplicatePower"));
		}
	}
	
	public void onUseCard(AbstractCard card, UseCardAction action) {
		if ((!card.purgeOnUse) && (card.hasTag(CRAFT)) && (this.amount > 0)) {
			flash();
			
			AbstractMonster m = null;
			if (action.target != null) {
				m = (AbstractMonster)action.target;
			}
				
			AbstractCard tmp = card.makeSameInstanceOf();
			AbstractDungeon.player.limbo.addToBottom(tmp);
			tmp.current_x = card.current_x;
			tmp.current_y = card.current_y;
			tmp.target_x = (Settings.WIDTH / 2.0F - 300.0F * Settings.scale);
			tmp.target_y = (Settings.HEIGHT / 2.0F);
			tmp.freeToPlayOnce = true;
			
			tmp.purgeOnUse = true;
			AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse));
			
			this.amount -= 1;
			if (this.amount == 0) {
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Runesmith:DuplicatePower"));
			}
		}
	}
	
	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			if (this.owner.hasPower("DuplicatePower"))
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "DuplicatePower"));
		}
	}
	
	public void updateDescription() {
		if (this.amount == 1) 
			this.description = (DESCRIPTIONS[0] + DESCRIPTIONS[1]);
		else
			this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
	}
	
}