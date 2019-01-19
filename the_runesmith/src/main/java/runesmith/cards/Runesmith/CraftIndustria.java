package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.CRAFT;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import runesmith.actions.RuneChannelAction;
import runesmith.orbs.IndustriaRune;
import runesmith.patches.AbstractCardEnum;

public class CraftIndustria extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftIndustria";
	public static final String IMG_PATH = "images/cards/defend_RS.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int AQUA_AMT = 4;
	private static final int UPG_AQUA_AMT = 1;
	
	public CraftIndustria() {
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
		this.baseMagicNumber = this.magicNumber = AQUA_AMT;
	}
	
	@Override
	public void applyPowers() {
		super.applyPowers();
		if(checkElements(0,0,this.magicNumber,true)) {
			if(!this.upgraded) {
				this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
			}else {
				this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0]);
			}
		}else {
			if(!this.upgraded) {
				this.rawDescription = (DESCRIPTION);
			}else {
				this.rawDescription = (UPGRADE_DESCRIPTION);
			}
		}
		initializeDescription();
	}
	
	@Override
	public void onMoveToDiscard(){
		if(!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		}else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		initializeDescription();
	}
	
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (checkElements(0,0,this.magicNumber)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new IndustriaRune()));
			if (this.upgraded) {
				AbstractDungeon.actionManager.addToBottom(
						new RuneChannelAction(
								new IndustriaRune()));
			}
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftIndustria();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeMagicNumber(UPG_AQUA_AMT);
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  initializeDescription();
		}
	}
	
}
