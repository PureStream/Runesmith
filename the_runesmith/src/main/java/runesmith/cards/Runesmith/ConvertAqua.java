package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.AquaPower;

public class ConvertAqua extends CustomCard {
	public static final String ID = "Runesmith:ConvertAqua";
	public static final String IMG_PATH = "images/cards/defend_RS.png"; //<-------- Image needed
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;
	private static final int IGNIS_AMT = 1;
	private static final int TERRA_AMT = 1;
	private static final int AQUA_AMT = 2;
	private static final int UPG_ELEMENT_AMT = 1;
	
	public ConvertAqua() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.POWER,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.SPECIAL,
			AbstractCard.CardTarget.SELF
		);
		this.magicNumber = this.baseMagicNumber = IGNIS_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		
		if (p.hasPower("Runesmith:AquaPower")) {
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
					new AquaPower(p, -AQUA_AMT),-AQUA_AMT));
		}
		AbstractDungeon.actionManager.addToBottom(
				new ApplyElementsPowerAction(p,p,this.magicNumber,TERRA_AMT,0));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
//				new IgnisPower(p, magicNumber), magicNumber));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
//				new TerraPower(p, TERRA_AMT), TERRA_AMT));
	}
	
	public AbstractCard makeCopy() {
		return new ConvertAqua();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeMagicNumber(UPG_ELEMENT_AMT);
		}
	}
}