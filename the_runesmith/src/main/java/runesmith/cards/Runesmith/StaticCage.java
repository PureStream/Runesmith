package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.abstracts.CustomCard;
import runesmith.actions.BreakRuneAction;
import runesmith.actions.StasisCardInHandAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class StaticCage extends CustomCard {

	public static final String ID = "Runesmith:StaticCage";
	public static final String IMG_PATH = "images/cards/StaticCage.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 7;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	
	public StaticCage() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.COMMON,
			AbstractCard.CardTarget.SELF
		);
		this.baseBlock = BLOCK_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		RuneOrb r = null;
		for(AbstractOrb o : p.orbs) {
			if(o instanceof RuneOrb) {
				r = (RuneOrb)o;
				break;
			}
		}
		if(r!=null) {
			AbstractDungeon.actionManager.addToBottom(
					new BreakRuneAction(r)
			);
		}
		AbstractDungeon.actionManager.addToBottom(
				new StasisCardInHandAction(p, 1)
			);
		AbstractDungeon.actionManager.addToBottom(
				new GainBlockAction(p, p, this.block)
			);
		float speedTime = 0.1F;
		if (Settings.FAST_MODE) {
			speedTime = 0.0F;
		}
		AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(p.drawX, p.drawY), speedTime));
		AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
	}
	
	public AbstractCard makeCopy() {
		return new StaticCage();
	}
	
	@Override
	public boolean isDefend() {
		return true;
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
	
}