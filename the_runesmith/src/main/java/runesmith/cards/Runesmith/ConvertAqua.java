package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.powers.PotentialDownPower;
import runesmith.powers.PotentialPower;

public class ConvertAqua extends CustomCard {
    public static final String ID = "Runesmith:ConvertAqua";
    public static final String IMG_PATH = "images/cards/ConvertAqua.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;
    private static final int POT_AMT = 2;

    public ConvertAqua() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                AbstractCard.CardType.SKILL,
                AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.SPECIAL,
                AbstractCard.CardTarget.SELF
        );
        this.baseMagicNumber = this.magicNumber = POT_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PotentialPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PotentialDownPower(p, this.magicNumber), this.magicNumber));

        if (p.hasPower("Runesmith:AquaPower")) {
            int convertAmount;
            convertAmount = p.getPower("Runesmith:AquaPower").amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Runesmith:AquaPower"));
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, convertAmount, convertAmount, 0));
        }

    }

    public AbstractCard makeCopy() {
        return new ConvertAqua();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }
}
