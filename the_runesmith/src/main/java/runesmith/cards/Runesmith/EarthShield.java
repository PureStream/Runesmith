package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.IgnisPower;

public class EarthShield extends CustomCard {
	public static final String ID = "Runesmith:EarthShield";
	public static final String IMG_PATH = "images/cards/defend_RS.png"; //<-------- Image needed
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 8;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	private static final int TERRA_AMT = 2;
	
	public EarthShield() {
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
		this.baseMagicNumber = this.magicNumber = TERRA_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new GainBlockAction(p, p, this.block)
		);
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, 
				new IgnisPower(AbstractDungeon.player, TERRA_AMT)));
	}
	
	public AbstractCard makeCopy() {
		return new EarthShield();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}
