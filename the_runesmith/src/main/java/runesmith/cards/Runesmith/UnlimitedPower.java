package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.UnlimitedPowerPower;

public class UnlimitedPower extends CustomCard {
    public static final String ID = "Runesmith:UnlimitedPower";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/UnlimitedPower.png"; //<-------------- need some img
    private static final int COST = 2;
    private static final int COST_UPGRADE = 1;
    private static final int BONUS_AMT = 1;

    public UnlimitedPower() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.POWER,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.SELF
        );

        this.baseMagicNumber = this.magicNumber = BONUS_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new UnlimitedPowerPower(p, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyElementsPowerAction(p, p, this.magicNumber, this.magicNumber, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new UnlimitedPower();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(COST_UPGRADE);
//            this.isInnate = true;
//            this.rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}
