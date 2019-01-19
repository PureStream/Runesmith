package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import runesmith.patches.AbstractCardEnum;

public class ElementalConversion extends CustomCard {
    public static final String ID = "Runesmith:ElementalConversion";
    public static final String IMG_PATH = "images/cards/ElementalConversion.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;

    private ModalChoice modal;

    public ElementalConversion() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.RUNESMITH_BEIGE, CardRarity.COMMON, CardTarget.SELF);
        makeModal();
    }

    private void makeModal() {
        modal = new ModalChoiceBuilder()
                .addOption(new ConvertIgnis())
                .addOption(new ConvertTerra())
                .addOption(new ConvertAqua())
                .create();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            for (int i=0; i<3; ++i) {
                modal.getCard(i).upgrade();
            }
            this.rawDescription = UPGRADE_DESCRIPTION;
        }
        initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new ElementalConversion();
    }
}