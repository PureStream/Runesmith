package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.ReplicatingBarrierPower;

public class ReplicatingBarrier extends CustomCard {
	public static final String ID = "Runesmith:ReplicatingBarrier";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "images/cards/ReplicatingBarrier.png"; //<-------------- need some img
	private static final int COST = 1;
	private static final int UPG_COST = 0;

	public ReplicatingBarrier() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.POWER,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.RARE,
			CardTarget.SELF
		);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!p.hasPower("Runesmith:ReplicatingBarrierPower"))
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
					new ReplicatingBarrierPower(p)));
	}

	public AbstractCard makeCopy() {
		return new ReplicatingBarrier();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPG_COST);
		}
	}
}
