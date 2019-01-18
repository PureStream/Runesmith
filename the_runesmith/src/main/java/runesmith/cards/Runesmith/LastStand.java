package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import runesmith.actions.DowngradeEntireDeckAction;
import runesmith.patches.AbstractCardEnum;

public class LastStand extends CustomCard {
	public static final String ID = "Runesmith:LastStand";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	public static final String IMG_PATH = "images/cards/LastStand.png"; //<-------------- need some img
	private static final int COST = 3;
	private static final int MULTIPLIER_AMT = 5;
	private static final int UPG_MULTIPLIER_AMT = 2;

	public LastStand() {
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
		this.baseDamage = this.damage = 0;
		this.isMultiDamage = true;
		this.baseMagicNumber = this.magicNumber = MULTIPLIER_AMT;
		this.exhaust = true;
	}
	
	public static int countCards() {
		int count = 0;
		for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
			if(c.upgraded) {
				count++;
			}
		}
		for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
			if(c.upgraded) {
				count++;
			}
		}
		for(AbstractCard c : AbstractDungeon.player.hand.group) {
			if(c.upgraded) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public void applyPowers() {
		this.baseDamage = ((this.upgraded) ? countCards()-1 : countCards())*this.magicNumber;
		
		super.applyPowers();
		
		String extendString = EXTENDED_DESCRIPTION[0];
		this.rawDescription = DESCRIPTION + extendString;
		initializeDescription();
	}
	
	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new DowngradeEntireDeckAction(p));
		
		if (p.hasPower("IgnisPower")) 
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, "Runesmith:IgnisPower"));
		if (p.hasPower("TerraPower")) 
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, "Runesmith:TerraPower"));
		if (p.hasPower("AquaPower")) 
			AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, "Runesmith:AquaPower"));
		
		AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
		AbstractDungeon.actionManager.addToBottom(
			new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)
		);
	}

	public AbstractCard makeCopy() {
		return new LastStand();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeMagicNumber(UPG_MULTIPLIER_AMT);
		  initializeDescription();
		}
	}
}
