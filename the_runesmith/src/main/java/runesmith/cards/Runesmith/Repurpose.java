package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.cards.RepurposeAction;
import runesmith.patches.AbstractCardEnum;

public class Repurpose extends CustomCard {

	public static final String ID = "Runesmith:Repurpose";
	public static final String IMG_PATH = "images/cards/Repurpose.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int BASE_ELEM_AMT = 2;
	private static final int BASE_ALL_ELEM_AMT = 1;
	private static final int EXTRA_ALL_ELEM_AMT = 1;
	private static final int UPGRADE_PLUS_ELEM = 1;
	
	public Repurpose() {
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
		this.baseMagicNumber = this.magicNumber = BASE_ELEM_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(
				new RepurposeAction(this.magicNumber, this.upgraded, BASE_ALL_ELEM_AMT, EXTRA_ALL_ELEM_AMT));
	}
	
	public AbstractCard makeCopy() {
		return new Repurpose();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  upgradeMagicNumber(UPGRADE_PLUS_ELEM);
		  initializeDescription();
		}
	}
}