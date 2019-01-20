package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.cards.EnchantAction;
import runesmith.patches.AbstractCardEnum;

public class Enchant extends CustomCard {
	public static final String ID = "Runesmith:Enchant";
	public static final String IMG_PATH = "images/cards/Enchant.png"; //<-------- Image needed
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	
	public Enchant() {
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
		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(new EnchantAction(upgraded));
	}
	
	public AbstractCard makeCopy() {
		return new Enchant();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = DESCRIPTION_UPG;
		  initializeDescription();
		}
	}
}
