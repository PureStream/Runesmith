package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;

import basemod.abstracts.CustomCard;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class OneForEveryone extends CustomCard {
	public static final String ID = "Runesmith:OneForEveryone";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/OneForEveryone.png"; //<-------------- need some img
	private static final int COST = 1;
	private static final int COST_UPGRADE = 0;
	private static final int ATTACK_DMG = 1;
	private static final int BLOCK_AMT = 1;
	private static final int ELEM_AMT = 1;

	public OneForEveryone() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.COMMON,
			CardTarget.ALL_ENEMY
		);
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = BLOCK_AMT;
		this.baseMagicNumber = ELEM_AMT;
		this.isMultiDamage = true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		RuneOrb r = null;
		for(AbstractOrb o : p.orbs) {
			if(o instanceof RuneOrb) {
				r = (RuneOrb)o;
				break;
			}
		}
		if(r != null) {
			AbstractDungeon.actionManager.addToBottom(
					new BreakRuneAction(r)
			);
			AbstractDungeon.actionManager.addToBottom(
					new GainEnergyAction(this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
			AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
			AbstractDungeon.actionManager.addToBottom(
				new DamageAllEnemiesAction(
					p,
					this.multiDamage, this.damageTypeForTurn,
					AttackEffect.NONE
				)
			);
			AbstractDungeon.actionManager.addToBottom(
					new GainBlockAction(p, p, this.block)
			);
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IgnisPower(p, this.magicNumber), this.magicNumber));
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new TerraPower(p, this.magicNumber), this.magicNumber));
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new AquaPower(p, this.magicNumber), this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, 
					new VulnerablePower(m, this.magicNumber, false), this.magicNumber));
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, 
					new WeakPower(m, this.magicNumber, false), this.magicNumber));
		}
	}

	public AbstractCard makeCopy() {
		return new OneForEveryone();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBaseCost(COST_UPGRADE);
		}
	}
}
