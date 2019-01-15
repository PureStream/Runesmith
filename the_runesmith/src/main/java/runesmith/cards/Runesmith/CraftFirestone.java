package runesmith.cards.Runesmith;

import basemod.helpers.BaseModCardTags;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.FirestoneRune;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.IgnisPower;

public class CraftFirestone extends AbstractRunicCard {

	public static final String ID = "Runesmith:CraftFirestone";
	public static final String IMG_PATH = "images/cards/defend_RS.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int COST_UPGRADE = 0;
	private static final int POTENCY = 5;
	private static final int IGNIS_AMT = 2;
	
	public CraftFirestone() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.BASIC,
			AbstractCard.CardTarget.SELF
		);
		
		this.basePotency = POTENCY;
		this.potency = this.basePotency;

	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		if(p.hasPower("IgnisPower")) {
			if(p.getPower("IgnisPower").amount>=2) {
				AbstractDungeon.actionManager.addToBottom(
						new RuneChannelAction(
								new FirestoneRune(this.POTENCY)));
				p.getPower("IgnisPower").reducePower(2);
			}else addPower();
		}else addPower();
	}
	
	public void addPower() {
		AbstractDungeon.actionManager.addToTop(
		          new ApplyPowerAction(
		              AbstractDungeon.player,
		              AbstractDungeon.player,
		              new IgnisPower(AbstractDungeon.player, IGNIS_AMT),
		              IGNIS_AMT
		          )
		      );
	}
	
	public AbstractCard makeCopy() {
		return new CraftFirestone();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBaseCost(COST_UPGRADE);
		}
	}
	
}
