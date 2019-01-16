package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
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
import runesmith.actions.DowngradeCardInHandAction;
import runesmith.actions.cards.HammerSlamAction;
import runesmith.patches.AbstractCardEnum;

public class ShiftingStrike extends CustomCard{
	public static final String ID = "Runesmith:ShiftingStrike";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/ShiftingStrike.png"; //<-------------- need some img
	private static final int COST = 1;
	private static final int ATTACK_DMG = 9;
	private static final int UPGRADE_PLUS_DMG = 4;
	
	public ShiftingStrike() {
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
	}
	
	public AbstractCard makeCopy() {
		return new ShiftingStrike();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			  upgradeName();
			  upgradeDamage(UPGRADE_PLUS_DMG);
			}
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(
					m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.BLUNT_LIGHT
				)
			);
		
		AbstractDungeon.actionManager.addToBottom(
				new ArmamentsAction(false)
		);
		AbstractDungeon.actionManager.addToBottom(
				new DowngradeCardInHandAction(p, false)
		);
	}
	
	
}
