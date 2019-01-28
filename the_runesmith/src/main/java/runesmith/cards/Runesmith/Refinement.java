package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import runesmith.actions.cards.RefinementAction;
import runesmith.patches.AbstractCardEnum;

public class Refinement extends CustomCard {
	public static final String ID = "Runesmith:Refinement";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String IMG_PATH = "images/cards/Refinement.png";
	private static final int COST = -1;

	public Refinement() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			CardRarity.UNCOMMON,
			CardTarget.NONE
		);
	}
	
	public void onMoveToDiscard() {
		if (this.upgraded) {
			this.rawDescription = UPGRADE_DESCRIPTION;
		} else {
			this.rawDescription = DESCRIPTION;
		}
		initializeDescription();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		int cnt = EnergyPanel.totalCount;
		if (upgraded) cnt++;
		if (p.hasRelic("Chemical X")) 
			cnt += 2;
		if (!this.freeToPlayOnce) {
			boolean ifEnergyUsed = (EnergyPanel.totalCount > 0) ? true : false;
			p.energy.use(EnergyPanel.totalCount);
			if (ifEnergyUsed)
				AbstractDungeon.actionManager.addToBottom(
						new GainEnergyAction(1));
		}
		if (cnt > 0) {
			AbstractDungeon.actionManager.addToBottom(
					new RefinementAction(cnt)
			);
		}
	}

	public AbstractCard makeCopy() {
		return new Refinement();
	}

	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  initializeDescription();
		}
	}
}
