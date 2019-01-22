package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.FirestormPower;

public class Firestorm extends CustomCard {
	public static final String ID = "Runesmith:Firestorm";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/Firestorm.png"; //<-------------- need some img
	private static final int COST = 1;
	private static final int POWER_AMT = 1;
//	private static final int UPGRADE_POWER_AMT = 1;

	public Firestorm() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.POWER,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.UNCOMMON,
			CardTarget.SELF
		);
		this.baseMagicNumber = this.magicNumber = POWER_AMT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
				new FirestormPower(p, this.magicNumber),this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new Firestorm();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
//		  upgradeMagicNumber(UPGRADE_POWER_AMT);
		  this.isInnate = true;
		}
	}
}
