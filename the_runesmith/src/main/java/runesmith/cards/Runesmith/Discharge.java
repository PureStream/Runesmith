package runesmith.cards.Runesmith;

import runesmith.actions.BreakRuneAction;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.DudRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.abstracts.CustomCard;

public class Discharge extends CustomCard {
	public static final String ID = "Runesmith:Discharge";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String IMG_PATH = "images/cards/Discharge.png"; //need some img
	private static final int COST = 1;
	private static final int ATTACK_DMG = 12;
	private static final int UPGRADE_PLUS_DMG = 4;
	private static final int BLOCK_AMT = 12;
	private static final int UPGRADE_BLOCK_AMT = 4;

	public Discharge() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.ATTACK,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.UNCOMMON,
			CardTarget.ENEMY
		);
		this.baseDamage = ATTACK_DMG;
		this.baseBlock = BLOCK_AMT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if(p.orbs.size() == 0) return;
		
		RuneOrb r = null;
		for(AbstractOrb o : p.orbs) {
			if(o instanceof RuneOrb) {
				if(!(o instanceof DudRune)) {
					r = (RuneOrb)o;
					break;
				}
			}
		}
		if(r == null) return;
		AbstractDungeon.actionManager.addToBottom(
				new BreakRuneAction(r)
		);
		
		AbstractDungeon.actionManager.addToBottom(
			new DamageAction(
				m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.NONE
			)
		);
		AbstractDungeon.actionManager.addToBottom(
				new GainBlockAction(p, p, this.block)
			);
		float speedTime = 0.1F;
		if (Settings.FAST_MODE) {
			speedTime = 0.0F;
		}
		AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), speedTime));
		AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
		

		
		AbstractDungeon.actionManager.addToBottom(
				new RuneChannelAction(
						new DudRune()));
		
	}

	public AbstractCard makeCopy() {
		return new Discharge();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeDamage(UPGRADE_PLUS_DMG);
		  upgradeBlock(UPGRADE_BLOCK_AMT);
		}
	}
}