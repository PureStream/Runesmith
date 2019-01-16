package runesmith.cards.Runesmith;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import runesmith.actions.cards.FortifyAction;
import runesmith.patches.AbstractCardEnum;

public class Fortify extends CustomCard {

	public static final String ID = "Runesmith:Fortify";
	public static final String IMG_PATH = "images/cards/Fortify.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;	
	
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 2;
	
	public Fortify() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.COMMON,
			AbstractCard.CardTarget.SELF
		);
		//this.tags.add(BaseModCardTags.BASIC_DEFEND);
		this.baseBlock = BLOCK_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new GainBlockAction(p, p, this.block)
		);
		AbstractDungeon.actionManager.addToBottom(
			new FortifyAction(this.upgraded)
		);
	}
	
	public AbstractCard makeCopy() {
		return new Fortify();
	}
	
	@Override
	public boolean isDefend() {
		return true;
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