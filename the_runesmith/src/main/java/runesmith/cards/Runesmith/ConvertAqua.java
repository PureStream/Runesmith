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
import runesmith.actions.ReduceElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.PotentialDownPower;
import runesmith.powers.PotentialPower;
import runesmith.ui.ElementsCounter;

import static runesmith.ui.ElementsCounter.getElementByID;

public class ConvertAqua extends CustomCard {
    public static final String ID = "Runesmith:ConvertAqua";
    public static final String IMG_PATH = "runesmith/images/cards/ConvertAqua.png"; //<-------- Image needed
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
                CardType.POWER,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.SPECIAL,
                CardTarget.NONE
        );
//        this.baseMagicNumber = this.magicNumber = POT_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        int convertAmt = getElementByID(ElementsCounter.Elements.AQUA);
        if (convertAmt > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReduceElementsPowerAction(p, p, 0, 0, convertAmt));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyElementsPowerAction(p, p, convertAmt, convertAmt, 0));
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
