package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;

public class Preparation extends CustomCard {
    public static final String ID = "Runesmith:Preparation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Preparation.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int BASE_AMT = 2;
    private static final int DRAW_AMT = 1;

    public Preparation() {
        this(0);
    }

    public Preparation(int upgrades) {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.COMMON,
                CardTarget.SELF
        );
        this.baseMagicNumber = this.magicNumber = BASE_AMT;
        this.exhaust = true;
        this.timesUpgraded = upgrades;
    }

    public AbstractCard makeCopy() {
        return new Preparation(this.timesUpgraded);
    }

    @Override
    public void upgrade() {
        if (this.canUpgrade()) {
            int increaseCount = 0;
            switch (this.timesUpgraded) {
                case 3:
                    increaseCount = 3;
                    break;
                case 2:
                case 1:
                    increaseCount = 2;
                    break;
                case 0:
                    increaseCount = 1;
                    break;
            }
            upgradeMagicNumber(increaseCount);
            this.timesUpgraded += 1;
            this.upgraded = true;
            this.name = (NAME + "+" + this.timesUpgraded);
            initializeTitle();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, this.magicNumber, this.magicNumber, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMT));
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 4;
    }

}
