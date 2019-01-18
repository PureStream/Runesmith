package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.CRAFT;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import runesmith.actions.RuneChannelAction;
import runesmith.orbs.PotentiaRune;
import runesmith.patches.AbstractCardEnum;

public class CraftPotentia extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftPotentia";
	public static final String IMG_PATH = "images/cards/CraftPotentia.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int POTENCY = 2;
	private static final int UPG_POTENCY = 1;
	private static final int ELEMENT_AMT = 2;
	
	public CraftPotentia() {
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
		if (checkElements(ELEMENT_AMT,ELEMENT_AMT,ELEMENT_AMT)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new PotentiaRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftPotentia();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradePotency(UPG_POTENCY);
		}
	}
	
}
