package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ReduceElementsPowerAction;
import runesmith.patches.AbstractCardEnum;

import static runesmith.ui.ElementsCounter.*;

public class Prism extends CustomCard {
    public static final String ID = "Runesmith:Prism";
    public static final String IMG_PATH = "images/cards/Prism.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int TIMES_AMT = 2;
    private static final int UPGRADE_TIMES_AMT = 1;

    public Prism() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                AbstractCard.CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                AbstractCard.CardRarity.UNCOMMON,
                AbstractCard.CardTarget.SELF
        );
        this.baseBlock = this.block = 0;
        this.baseMagicNumber = this.magicNumber = TIMES_AMT;
    }

    @Override
    public void applyPowers() {
        int multiplier = this.magicNumber;
        this.baseBlock = (getIgnis() + getTerra() + getAqua()) * multiplier;
        super.applyPowers();
        if (this.block > 0) {
            String extendString = EXTENDED_DESCRIPTION[0];
            this.rawDescription = DESCRIPTION + extendString;
            initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.block > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new GainBlockAction(p, p, this.block)
            );
        }
        AbstractDungeon.actionManager.addToBottom(new ReduceElementsPowerAction((int) Math.round(getIgnis()/2.0), (int) Math.round(getTerra()/2.0), (int) Math.round(getAqua()/2.0)));
    }

    public AbstractCard makeCopy() {
        return new Prism();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_TIMES_AMT);
            initializeDescription();
        }
    }
}