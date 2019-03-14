package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsAction;
import runesmith.actions.ReduceElementsAction;
import runesmith.ui.ElementsCounter;

import static runesmith.ui.ElementsCounter.getElementByID;

public class ConvertTerra extends CustomCard {
    public static final String ID = "Runesmith:ConvertTerra";
    public static final String IMG_PATH = "images/cards/ConvertTerra.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;
    private static final int BLOCK_AMT = 5;

    public ConvertTerra() {
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
        this.baseBlock = BLOCK_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );
        int convertAmt = getElementByID(ElementsCounter.Elements.TERRA);
        if (convertAmt > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReduceElementsAction(p, p, 0, convertAmt, 0));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyElementsAction(p, p, convertAmt, 0, convertAmt));

        }
    }

    public AbstractCard makeCopy() {
        return new ConvertTerra();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }
}
