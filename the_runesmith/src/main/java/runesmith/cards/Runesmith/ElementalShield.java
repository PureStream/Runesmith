package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;

public class ElementalShield extends CustomCard {
	public static final String ID = "Runesmith:ElementalShield";
	public static final String IMG_PATH = "images/cards/defend_RS.png"; //<-------- Image needed
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 0;
	private static final int TIMES_AMT = 2;
	private static final int UPGRADE_TIMES_AMT = 1;
	
	public ElementalShield() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.UNCOMMON,
			AbstractCard.CardTarget.SELF
		);
		this.baseBlock = this.block = 0;
		this.baseMagicNumber = this.magicNumber = TIMES_AMT;
	}
	
	@Override
	public void applyPowers() {
		AbstractPlayer p = AbstractDungeon.player;
		int multiplier;
		int totalElements = 0;
		this.baseBlock = 0;
		if (this.upgraded) {
			multiplier = 3;
		} else {
			multiplier = 2;
		}
		if (p.hasPower("Runesmith:IgnisPower")) {
			totalElements += p.getPower("Runesmith:IgnisPower").amount;
		}
		if (p.hasPower("Runesmith:TerraPower")) {
			totalElements += p.getPower("Runesmith:TerraPower").amount;
		}
		if (p.hasPower("Runesmith:AquaPower")) {
			totalElements += p.getPower("Runesmith:AquaPower").amount;
		}
		if (totalElements > 0) {
			this.baseBlock += (totalElements * multiplier);
			super.applyPowers();
		}
		if (this.block > 0) {
			String extendString = EXTENDED_DESCRIPTION[0] + this.block + EXTENDED_DESCRIPTION[1];
			if (!this.upgraded) {
				this.rawDescription = DESCRIPTION + extendString;
			} else {
				this.rawDescription = DESCRIPTION_UPG + extendString;
			}
			initializeDescription();
		}
	}
	
	public void onMoveToDiscard() {
		if (this.upgraded) {
			this.rawDescription = DESCRIPTION_UPG;
		} else {
			this.rawDescription = DESCRIPTION;
		}
		initializeDescription();
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.block > 0) {
			AbstractDungeon.actionManager.addToBottom(
			  new GainBlockAction(p, p, this.block)
			);
		}
		if (p.hasPower("IgnisPower")) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "IgnisPower"));
		}
		if (p.hasPower("TerraPower")) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "TerraPower"));
		}
		if (p.hasPower("AquaPower")) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "AquaPower"));
		}
	}
	
	public AbstractCard makeCopy() {
		return new ElementalShield();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_TIMES_AMT);
			initializeDescription();
		}
	}
}