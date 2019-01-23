package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.TimeMachinePower;

public class TimeMachineAlt extends CustomCard {

	public static final String ID = "Runesmith:TimeMachineAlt";
	public static final String IMG_PATH = "images/cards/TimeMachine.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Runesmith:TimeMachine");
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;	
	
	private static final int COST = 3;
	
	public TimeMachineAlt() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.RARE,
			AbstractCard.CardTarget.SELF
		);
		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		int health, block, ignis, terra, aqua;
		health = p.currentHealth;
		block = p.currentBlock;
		ignis = p.hasPower("Runesmith:IgnisPower") ? p.getPower("Runesmith:IgnisPower").amount : 0;
		terra = p.hasPower("Runesmith:TerraPower") ? p.getPower("Runesmith:TerraPower").amount : 0;
		aqua = p.hasPower("Runesmith:AquaPower") ? p.getPower("Runesmith:AquaPower").amount : 0;
		
		if(p.hasPower("Runesmith:TimeMachinePower")) {
			AbstractPower pow = p.getPower("Runesmith:TimeMachinePower");
			if(pow instanceof TimeMachinePower) {
				((TimeMachinePower) pow).setValues(health, block, ignis, terra, aqua);
			}
		}else {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TimeMachinePower(p, health, block, ignis, terra, aqua)));
		}
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new TimeTravelAlt()));
	}
	
	public AbstractCard makeCopy() {
		return new TimeMachineAlt();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.isInnate = true;
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  initializeDescription();
		}
	}
	
}
