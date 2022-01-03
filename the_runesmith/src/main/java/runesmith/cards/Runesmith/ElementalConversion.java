package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.patches.AbstractCardEnum;

import java.util.ArrayList;

public class ElementalConversion extends CustomCard {
    public static final String ID = "Runesmith:ElementalConversion";
    public static final String IMG_PATH = "runesmith/images/cards/ElementalConversion.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int DRAW_AMT = 1;

    private ModalChoice modal;

    public ElementalConversion() {
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
        this.baseMagicNumber = this.magicNumber = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> elementChoices = new ArrayList<>();
        elementChoices.add(new ConvertIgnis());
        elementChoices.add(new ConvertTerra());
        elementChoices.add(new ConvertAqua());
        this.addToBot(new ChooseOneAction(elementChoices));

        if (upgraded) {
            this.addToBot(new DrawCardAction(p, DRAW_AMT));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(DRAW_AMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
//            for (int i=0; i<3; ++i) {
//                modal.getCard(i).upgrade();
//            }
//            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
        //initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new ElementalConversion();
    }
}