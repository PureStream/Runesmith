package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.HAMMER;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import runesmith.actions.UpgradeDrawAction;
import runesmith.patches.AbstractCardEnum;

public class HammerThrow extends CustomCard {
	public static final String ID = "Runesmith:HammerThrow";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/HammerThrow.png"; //<-------------- need some img
	private static final int COST = 1;
	private static final int ATTACK_DMG = 6;
	private static final int UPGRADE_PLUS_DMG = 1;
	private static final int DRAW_AMT = 1;
	private static final int UPGRADE_DRAW_AMT = 1;

	public HammerThrow() {
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
		this.baseMagicNumber = this.magicNumber = DRAW_AMT;
		this.tags.add(HAMMER);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new DamageAction(
				m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.BLUNT_HEAVY
			)
		);
		AbstractDungeon.actionManager.addToTop(
				new UpgradeDrawAction(p, this.magicNumber)
				);
	}

	public AbstractCard makeCopy() {
		return new HammerThrow();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeDamage(UPGRADE_PLUS_DMG);
		  upgradeMagicNumber(UPGRADE_DRAW_AMT);
		}
	}
}