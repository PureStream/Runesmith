package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.actions.ReduceElementsPowerAction;
import runesmith.patches.AbstractCardEnum;

import static runesmith.ui.ElementsCounter.Elements;
import static runesmith.ui.ElementsCounter.getElementByID;

public class ConvertIgnis extends CustomCard {
    public static final String ID = "Runesmith:ConvertIgnis";
    public static final String IMG_PATH = "runesmith/images/cards/ConvertIgnis.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;
    private static final int ATTACK_DMG = 6;

    public ConvertIgnis() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.SPECIAL,
                CardTarget.NONE
        );
//        this.baseDamage = ATTACK_DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.onChoseThisOption();
    }

    public void onChoseThisOption() {
        AbstractPlayer p = AbstractDungeon.player;
        int convertAmt = getElementByID(Elements.IGNIS);
        if (convertAmt > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ReduceElementsPowerAction(p, p, convertAmt, 0, 0));
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyElementsPowerAction(p, p, 0, convertAmt, convertAmt));

        }
    }

    public AbstractCard makeCopy() {
        return new ConvertIgnis();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }
}
