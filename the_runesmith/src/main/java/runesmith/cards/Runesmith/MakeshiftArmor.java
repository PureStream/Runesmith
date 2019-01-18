package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.actions.DowngradeRandomCardInDeckAction;
import runesmith.patches.AbstractCardEnum;

public class MakeshiftArmor extends CustomCard {

	public static final String ID = "Runesmith:MakeshiftArmor";
	public static final String IMG_PATH = "images/cards/defend_RS.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 11;
	private static final int UPGRADE_PLUS_BLOCK = 4;
	private static final int TERRA_AMT = 1;
	
	public MakeshiftArmor() {
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
		this.tags.add(BaseModCardTags.BASIC_DEFEND);
		this.baseBlock = BLOCK_AMT;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new GainBlockAction(p, p, this.block)
		);
		AbstractDungeon.actionManager.addToBottom(
				new ApplyElementsPowerAction(p,p,0,TERRA_AMT,0));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
//			new TerraPower(p, TERRA_AMT),TERRA_AMT));
		AbstractDungeon.actionManager.addToBottom(
			new DowngradeRandomCardInDeckAction(p)
		);
	}
	
	public AbstractCard makeCopy() {
		return new MakeshiftArmor();
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