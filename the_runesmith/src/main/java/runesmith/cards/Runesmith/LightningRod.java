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
import runesmith.powers.LightningRodPower;

public class LightningRod extends CustomCard {
    public static final String ID = "Runesmith:LightningRod";
    public static final String IMG_PATH = "images/cards/LightningRod.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_MAGIC_AMT = 1;

    public LightningRod() {
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
        this.baseMagicNumber = this.magicNumber = MAGIC_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new LightningRodPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyElementsPowerAction(p, p, this.magicNumber, this.magicNumber, this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new LightningRod();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_AMT);
        }
    }
}