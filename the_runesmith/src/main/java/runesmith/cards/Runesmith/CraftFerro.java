package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.CRAFT;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import runesmith.actions.RuneChannelAction;
import runesmith.orbs.FerroRune;
import runesmith.patches.AbstractCardEnum;

public class CraftFerro extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftFerro";
	public static final String IMG_PATH = "images/cards/CraftFerro.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int POTENCY = 5;
	private static final int TERRA_AMT = 2;
	private static final int AQUA_AMT = 1;
	
	public CraftFerro() {
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
		this.exhaust = true;

	}
	
	@Override
	public void applyPowers() {
		super.applyPowers();
		if(checkElements(0,TERRA_AMT,AQUA_AMT,true)) {
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
		if (checkElements(0,TERRA_AMT,AQUA_AMT)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new FerroRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftFerro();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  this.exhaust = false;
		  initializeDescription();
		}
	}
	
}
