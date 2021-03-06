package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.DuctTapePower;

public class DuctTape extends CustomCard {
    public static final String ID = "Runesmith:DuctTape";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/DuctTape.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int POWER_AMT = 1;
    private static final int UPGRADE_POWER_AMT = 1;

    public DuctTape() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.POWER,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.NONE
        );
        this.baseMagicNumber = this.magicNumber = POWER_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                new DuctTapePower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new DuctTape();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_POWER_AMT);
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }
}
