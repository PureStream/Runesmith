package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;

public class ThatsALotOfDamage extends CustomCard {
	public static final String ID = "Runesmith:ThatsALotOfDamage";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPG_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "images/cards/TALD.png";
	private static final int COST = 4;

	public ThatsALotOfDamage() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.RARE,
			CardTarget.ALL_ENEMY
		);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new GainEnergyAction(this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			int halfMonHealth = mo.currentHealth/2;
			int halfMonBlock = mo.currentBlock/2;
			if (halfMonBlock > 0)
				AbstractDungeon.actionManager.addToBottom(
						new DamageAction(
							m,
							new DamageInfo(p, halfMonBlock, this.damageTypeForTurn),
							AttackEffect.NONE
						)
					);
			if (halfMonHealth > 0)
				AbstractDungeon.actionManager.addToBottom(
						new DamageAction(
							m,
							new DamageInfo(p, halfMonHealth, DamageType.HP_LOSS),
							AttackEffect.NONE
						)
					);
		}
		
	}

	public AbstractCard makeCopy() {
		return new ThatsALotOfDamage();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPG_DESCRIPTION;
		  this.isInnate = true;
		  initializeDescription();
		}
	}
}
