package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.DowngradeEntireDeckAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.MetallurgicalResearchPower;

public class MetallurgicalResearch extends CustomCard {
	public static final String ID = "Runesmith:MetallurgicalResearch";
	public static final String IMG_PATH = "images/cards/MetallurgicalResearch.png"; //<-------- Image needed
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int WAIT_AMT = 3;
	private static final int UPG_WAIT_AMT = -1;
	
	public MetallurgicalResearch() {
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
		this.baseMagicNumber = this.magicNumber = WAIT_AMT;
		this.exhaust = true;
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
			new DowngradeEntireDeckAction(p)
		);
		if (!p.hasPower("Runesmith:MetallurgicalResearchPower"))
			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
					new MetallurgicalResearchPower(p,this.magicNumber),this.magicNumber));
	}
	
	public AbstractCard makeCopy() {
		return new MetallurgicalResearch();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeMagicNumber(UPG_WAIT_AMT);
		}
	}
}
