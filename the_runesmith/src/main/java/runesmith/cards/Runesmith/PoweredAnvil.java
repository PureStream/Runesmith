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
import runesmith.powers.PoweredAnvilPower;

public class PoweredAnvil extends CustomCard {
    public static final String ID = "Runesmith:PoweredAnvil";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/PoweredAnvil.png"; //<-------------- need some img
    private static final int COST = 2;

    public PoweredAnvil() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.POWER,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!p.hasPower("PoweredAnvilPower"))
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                    new PoweredAnvilPower(p, 1), 1));
    }

    public AbstractCard makeCopy() {
        return new PoweredAnvil();
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
