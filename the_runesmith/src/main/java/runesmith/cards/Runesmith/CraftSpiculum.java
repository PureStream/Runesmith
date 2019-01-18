package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.SpiculumRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.CRAFT;

public class CraftSpiculum extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftSpiculum";
	public static final String IMG_PATH = "images/cards/CraftSpiculum.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int POTENCY = 3;
	private static final int UPGRADE_POTENCY = 2;
	private static final int IGNIS_AMT = 3;
	private static final int AQUA_AMT = 1;
	
	public CraftSpiculum() {
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
		
		this.potency = this.basePotency = POTENCY;
		this.tags.add(CRAFT);

	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (checkElements(IGNIS_AMT,0,AQUA_AMT)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new SpiculumRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftSpiculum();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradePotency(UPGRADE_POTENCY);
		}
	}
	
}
