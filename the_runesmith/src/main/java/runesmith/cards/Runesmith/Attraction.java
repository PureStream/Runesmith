package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.cards.AttractionAction;
import runesmith.patches.AbstractCardEnum;

public class Attraction extends CustomCard {

    public static final String ID = "Runesmith:Attraction";
    public static final String IMG_PATH = "runesmith/images/cards/Attraction.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 0;
//    private static final int COST_UPGRADE = 0;
    private static final int SELECT_AMT = 1;
    private static final int UPG_SELECT_AMT = 1;

    public Attraction() {
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
		this.exhaust = true;
		this.magicNumber = this.baseMagicNumber = SELECT_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AttractionAction(magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Attraction();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_SELECT_AMT);
        }
    }

}
