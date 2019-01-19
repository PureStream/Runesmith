package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.CRAFT;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import runesmith.actions.RuneChannelAction;
import runesmith.orbs.ReservoRune;
import runesmith.patches.AbstractCardEnum;

public class CraftReservo extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftReservo";
	public static final String IMG_PATH = "images/cards/CraftReservo.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int AQUA_AMT = 2;
	
	public CraftReservo() {
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
		this.tags.add(CRAFT);
		this.exhaust = true;
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
							new ReservoRune(this.upgraded)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftReservo();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  initializeDescription();
		}
	}
	
}
