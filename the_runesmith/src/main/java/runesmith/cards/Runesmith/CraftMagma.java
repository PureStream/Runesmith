package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.MagmaRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.CRAFT;

public class CraftMagma extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftMagma";
	public static final String IMG_PATH = "images/cards/CraftMagma.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 0;
	private static final int POTENCY = 4;
	private static final int UPGRADE_PLUS_POT = 2;
	private static final int IGNIS_AMT = 2;
	private static final int TERRA_AMT = 1;
	
	public CraftMagma() {
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
		
		this.potency = this.basePotency = POTENCY;
		this.tags.add(CRAFT);
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (checkElements(IGNIS_AMT,TERRA_AMT,0)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new MagmaRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftMagma();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradePotency(UPGRADE_PLUS_POT);
		}
	}
	
}
