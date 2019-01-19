package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;
import runesmith.patches.ElementsGainedCountField;

public class ForcesOfNature extends CustomCard{

	public static final String ID = "Runesmith:ForcesOfNature";
	public static final String IMG_PATH = "images/cards/ForcesOfNature.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int BASE_MULTIPLY = 1;
	private static final int UPGRADE_MULTIPLY = 1;
	
	public ForcesOfNature() {
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
		this.baseDamage = 0;
		this.baseMagicNumber = this.magicNumber = BASE_MULTIPLY;
		this.isMultiDamage = true;
	}
	
	@Override
	public void use(AbstractPlayer arg0, AbstractMonster arg1) {
		AbstractPlayer p = AbstractDungeon.player;
//		int elem = ElementsGainedCountField.elementsCount.get(p);
		
//		this.baseDamage = (elem * this.magicNumber);
		calculateCardDamage(null);
		

		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
		AbstractDungeon.actionManager.addToBottom(
				new DamageAllEnemiesAction(
					p,
					this.multiDamage, this.damageTypeForTurn,
					AbstractGameAction.AttackEffect.FIRE
				)
			);
	}
	
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		
		if(!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		}else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		this.rawDescription += EXTENDED_DESCRIPTION[0];
		initializeDescription();
	}
		
	public AbstractCard makeCopy() {
		return new ForcesOfNature();
	}
	
	@Override
	public void applyPowers() {
		AbstractPlayer p = AbstractDungeon.player;
		int elem = ElementsGainedCountField.elementsCount.get(p);
		
//		if(elem > 0) {
			this.baseDamage = elem * this.magicNumber;
			super.applyPowers();
			if(!this.upgraded) {
				this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0]);
			}else {
				this.rawDescription = (UPGRADE_DESCRIPTION + EXTENDED_DESCRIPTION[0]);
			}
			initializeDescription();
//		}
	}
	
	@Override
	public void onMoveToDiscard(){
		if(!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		}else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		initializeDescription();
	}

	@Override
	public void upgrade() {
		if(!this.upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_MULTIPLY);
			if(!this.upgraded) {
				this.rawDescription = DESCRIPTION;
			}else {
				this.rawDescription = UPGRADE_DESCRIPTION;
			}
			initializeDescription();
		}
		
	}
}
