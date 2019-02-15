package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.cards.DoubleUpAction;
import runesmith.patches.AbstractCardEnum;

public class DoubleUp extends CustomCard {

    public static final String ID = "Runesmith:DoubleUp";
    public static final String IMG_PATH = "images/cards/DoubleUp.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int BLOCK_AMT = 12;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int CARD_AMT = 1;
    private static final int UPG_CARD_AMT = 1;

    public DoubleUp() {
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
        this.baseBlock = this.block = BLOCK_AMT;
        this.baseMagicNumber = this.magicNumber = CARD_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );
        AbstractDungeon.actionManager.addToBottom(
                new DoubleUpAction(magicNumber)
        );
    }

    public AbstractCard makeCopy() {
        return new DoubleUp();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPG_CARD_AMT);
            initializeDescription();
        }
    }

}
