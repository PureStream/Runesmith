package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class Accelerate extends CustomCard {
    public static final String ID = "Runesmith:Accelerate";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Accelerate.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int BLOCK_AMT = 7;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int BASE_DRAW_AMT = 1;
    private static final int DRAW_AMT = 2;
    private static final int UPGRADE_DRAW_AMT = 1;

    public Accelerate() {
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
        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = this.magicNumber = DRAW_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int drawAmt = BASE_DRAW_AMT;
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );

        if (p.orbs.size() == 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new DrawCardAction(p, drawAmt)
            );
            return;
        }

        RuneOrb r = null;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof RuneOrb) {
                r = (RuneOrb) o;
                break;
            }
        }

        if (r != null) {
            drawAmt += this.magicNumber;

            AbstractDungeon.actionManager.addToBottom(
                    new BreakRuneAction(r)
            );
        }

        AbstractDungeon.actionManager.addToBottom(
                new DrawCardAction(p, drawAmt)
        );
    }

    public AbstractCard makeCopy() {
        return new Accelerate();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_DRAW_AMT);
        }
    }
}