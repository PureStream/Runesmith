package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import runesmith.actions.RuneChannelAction;
import runesmith.orbs.FirestoneRune;
import runesmith.orbs.MagmaRune;
import runesmith.patches.AbstractCardEnum;

public class Firewall extends AbstractRunicCard {
	public static final String ID = "Runesmith:Firewall";
	public static final String IMG_PATH = "images/cards/Firewall.png"; //<-------- Image needed
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 2;
	private static final int BLOCK_AMT = 12;
	private static final int UPGRADE_PLUS_BLOCK = 4;
	private static final int IGNIS_AMT = 3;
	private static final int TERRA_AMT = 0;
	private static final int POTENCY = 3;
	private static final int UPG_POTENCY = 1;
	
	public Firewall() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.UNCOMMON,
			AbstractCard.CardTarget.SELF
		);
		this.baseBlock = this.block = BLOCK_AMT;
		this.basePotency = this.potency = POTENCY;
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		checkElements(IGNIS_AMT,TERRA_AMT,0, true);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
		AbstractDungeon.actionManager.addToBottom(
			new GainBlockAction(p, p, this.block)
		);
		if (checkElements(IGNIS_AMT,TERRA_AMT,0)) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new FirestoneRune(this.potency)));
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new FirestoneRune(this.potency)));
//			AbstractDungeon.actionManager.addToBottom(
//					new RuneChannelAction(
//							new MagmaRune(this.potency)));
		}
	}
	
	public AbstractCard makeCopy() {
		return new Firewall();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBlock(UPGRADE_PLUS_BLOCK);
		  upgradePotency(UPG_POTENCY);
		}
	}
}
