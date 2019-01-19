package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.CRAFT;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import runesmith.actions.RuneChannelAction;
import runesmith.orbs.ProtectioRune;
import runesmith.patches.AbstractCardEnum;

public class CraftProtectio extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftProtectio";
	public static final String IMG_PATH = "images/cards/CraftProtectio.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int POTENCY = 4;
	private static final int UPGRADE_POTENCY = 2;
	private static final int TERRA_AMT = 2;
	
	public CraftProtectio() {
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
		
		this.basePotency = POTENCY;
		this.potency = this.basePotency;
		this.tags.add(CRAFT);

	}
	
	@Override
	public void applyPowers() {
		super.applyPowers();
		if(checkElements(0,TERRA_AMT,0,true)) {
			this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
		}else {
			this.rawDescription = (DESCRIPTION);
		}
		initializeDescription();
	}
	
	@Override
	public void onMoveToDiscard(){
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}
	
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (checkElements(0,TERRA_AMT,0)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new ProtectioRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new CraftProtectio();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradePotency(UPGRADE_POTENCY);
		}
	}
	
}