package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.GrindstonePower;

public class Grindstone extends CustomCard {
    public static final String ID = "Runesmith:Grindstone";
    public static final String IMG_PATH = "runesmith/images/cards/Grindstone.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 6;
    private static final int POWER_AMT = 1;
    private static final int UPGRADE_POWER_AMT = 1;

    public Grindstone() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                AbstractCard.CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                AbstractCard.CardRarity.COMMON,
                AbstractCard.CardTarget.SELF
        );
        this.baseBlock = this.block = BLOCK_AMT;
        this.baseMagicNumber = this.magicNumber = POWER_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p,
                new GrindstonePower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Grindstone();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_POWER_AMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
