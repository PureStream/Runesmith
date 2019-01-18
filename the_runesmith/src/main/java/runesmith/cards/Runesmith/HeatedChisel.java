package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class HeatedChisel extends CustomCard {
	public static final String ID = "Runesmith:HeatedChisel";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/strike_RS.png"; //<-------------- need some img
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int UPGRADE_PLUS_DMG = 1;
	private static final int ELEMENT_AMT = 1;
	private static final int VULNERABLE_AMT = 1;
	private static final int UPGRADE_PLUS_VULNERABLE_AMT = 1;

	public HeatedChisel() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.COMMON,
			CardTarget.ENEMY
		);
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = this.magicNumber = VULNERABLE_AMT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new DamageAction(
				m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL
			)
		);
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, 
				new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(
				new ApplyElementsPowerAction(p,p,ELEMENT_AMT,ELEMENT_AMT,0));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
//				new IgnisPower(p, ELEMENT_AMT),ELEMENT_AMT));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
//				new TerraPower(p, ELEMENT_AMT),ELEMENT_AMT));
	}

	public AbstractCard makeCopy() {
		return new HeatedChisel();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeDamage(UPGRADE_PLUS_DMG);
		  upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE_AMT);
		}
	}
}