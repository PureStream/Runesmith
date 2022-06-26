package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.cards.MallocAction;
import runesmith.patches.AbstractCardEnum;

public class Malloc extends CustomCard {
    public static final String ID = "Runesmith:Malloc";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/Malloc.png"; //<-------------- need some img
    private static final int COST = 0;
    private static final int ENERGY_GAIN = 1;
    private static final int ENERGY_GAIN_UPGRADED = 2;
    private static final int CARD_DRAW = 1;

    public Malloc() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CardTarget.SELF
        );
        this.baseMagicNumber = this.magicNumber = CARD_DRAW;
    }

    public AbstractCard makeCopy() {
        return new Malloc();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded) {
            AbstractDungeon.actionManager.addToBottom(new MallocAction(p, ENERGY_GAIN, this.magicNumber));
            return;
        }
        AbstractDungeon.actionManager.addToBottom(new MallocAction(p, ENERGY_GAIN_UPGRADED, this.magicNumber));
    }
}
