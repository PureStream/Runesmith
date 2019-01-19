package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.cards.DoubleUpAction;
import runesmith.patches.AbstractCardEnum;

public class DoubleUp extends CustomCard {

	public static final String ID = "Runesmith:DoubleUp";
	public static final String IMG_PATH = "images/cards/DoubleUp.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;	
	
	private static final int COST = 2;
	private static final int BLOCK_AMT = 10;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	
	public DoubleUp() {
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
		this.baseBlock = this.block = BLOCK_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new GainBlockAction(p, p, this.block)
		);
		AbstractDungeon.actionManager.addToBottom(
			new DoubleUpAction(this.upgraded)
		);
	}
	
	public AbstractCard makeCopy() {
		return new DoubleUp();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBlock(UPGRADE_PLUS_BLOCK);
		  this.rawDescription = UPGRADE_DESCRIPTION;
 		   	initializeDescription();
		}
	}
	
}
