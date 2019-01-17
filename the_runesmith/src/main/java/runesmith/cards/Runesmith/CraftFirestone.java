package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.FirestoneRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.CRAFT;

public class CraftFirestone extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftFirestone";
	public static final String IMG_PATH = "images/cards/CraftFirestone.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int COST_UPGRADE = 0;
	private static final int POTENCY = 5;
	private static final int IGNIS_AMT = 2;
	
	public CraftFirestone() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.BASIC,
			AbstractCard.CardTarget.SELF
		);
		
		this.potency = this.basePotency = POTENCY;
		this.tags.add(CRAFT);

	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (checkElements(IGNIS_AMT,0,0)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new FirestoneRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftFirestone();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBaseCost(COST_UPGRADE);
		}
	}
	
}
