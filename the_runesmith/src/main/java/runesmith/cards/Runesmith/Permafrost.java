package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.PermafrostPower;

public class Permafrost extends CustomCard {
	public static final String ID = "Runesmith:Permafrost";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/Permafrost.png"; //<-------------- need some img
	private static final int COST = 2;
	private static final int UPG_COST = 1;
	private static final int ELEMENT_AMT = 1;

	public Permafrost() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.POWER,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.UNCOMMON,
			CardTarget.SELF
		);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!p.hasPower("Runesmith:PermafrostPower"))
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
					new PermafrostPower(p)));
		AbstractDungeon.actionManager.addToBottom(
				new ApplyElementsPowerAction(p,p,0,ELEMENT_AMT,ELEMENT_AMT));
	}

	public AbstractCard makeCopy() {
		return new Permafrost();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBaseCost(UPG_COST);
		}
	}
}