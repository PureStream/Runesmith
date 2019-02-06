package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.PrismaticRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.CRAFT;

public class ConstructBifrost extends AbstractRunicCard {

	public static final String ID = "Runesmith:ConstructBifrost";
	public static final String IMG_PATH = "images/cards/ConstructBifrost.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int ELEMENT_AMT = 1;
	
	public ConstructBifrost() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.RARE,
			AbstractCard.CardTarget.SELF
		);
		this.tags.add(CRAFT);
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		checkElements(ELEMENT_AMT,ELEMENT_AMT,ELEMENT_AMT,true);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (checkElements(ELEMENT_AMT,ELEMENT_AMT,ELEMENT_AMT)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new PrismaticRune(this.upgraded)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new ConstructBifrost();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  initializeDescription();
		}
	}
	
}
