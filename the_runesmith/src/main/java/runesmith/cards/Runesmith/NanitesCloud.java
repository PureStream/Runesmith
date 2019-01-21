package runesmith.cards.Runesmith;

import static runesmith.patches.CardTagEnum.CHISEL;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import basemod.abstracts.CustomCard;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.IceColdPower;

public class NanitesCloud extends CustomCard {
	public static final String ID = "Runesmith:NanitesCloud";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/NanitesCloud.png"; //<-------------- need some img
	private static final int COST = 2;
	private static final int STRENGTH_DOWN = 12;
	private static final int UPGRADE_STRENGTH_DOWN = 4;

	public NanitesCloud() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.UNCOMMON,
			CardTarget.ALL_ENEMY
		);
		this.exhaust = true;
		this.baseMagicNumber = this.magicNumber = STRENGTH_DOWN;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {

		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Color.GRAY, ShockWaveEffect.ShockWaveType.NORMAL), 1.5F));

		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
		}

		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			if (!mo.hasPower("Artifact")) {
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
			}
		}
	}

	public AbstractCard makeCopy() {
		return new NanitesCloud();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeMagicNumber(UPGRADE_STRENGTH_DOWN);
		}
	}
}
