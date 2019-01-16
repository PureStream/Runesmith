package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.cards.HammerSlamAction;
import runesmith.patches.AbstractCardEnum;

public class HammerSlam extends CustomCard{
	public static final String ID = "Runesmith:HammerSlam";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/HammerSlam.png"; //<-------------- need some img
	private static final int COST = 2;
	private static final int ATTACK_DMG = 11;
	private static final int UPGRADE_PLUS_DMG = 4;
	private static final int BASE_DRAW = 1;
	private static final int UPGRADE_PLUS_DRAW = 1;
	
	public HammerSlam() {
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
		this.baseMagicNumber = this.magicNumber = BASE_DRAW;
	}
	
	public AbstractCard makeCopy() {
		return new HammerSlam();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			  upgradeName();
			  upgradeDamage(UPGRADE_PLUS_DMG);
//			  upgradeMagicNumber(UPGRADE_PLUS_DRAW);
			}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
					m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.BLUNT_HEAVY
				)
			);
		
		AbstractDungeon.actionManager.addToBottom(
				new HammerSlamAction(p, this.magicNumber,1)
				);
	}
	
	
}
