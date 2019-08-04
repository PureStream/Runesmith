package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runesmith.actions.cards.RefinementAction;
import runesmith.patches.AbstractCardEnum;

public class Refine extends CustomCard {
    public static final String ID = "Runesmith:Refine";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/Refine.png";
    private static final int COST = -1;

    public Refine() {
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
        if (this.energyOnUse < EnergyPanel.totalCount)
            this.energyOnUse = EnergyPanel.totalCount;

        AbstractDungeon.actionManager.addToBottom(new RefinementAction(this.energyOnUse, this.freeToPlayOnce, this.upgraded));
    }

    public AbstractCard makeCopy() {
        return new Refine();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
