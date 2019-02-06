package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.MedicinaeRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.CRAFT;

public class CraftMedicinae extends AbstractRunicCard {

    public static final String ID = "Runesmith:CraftMedicinae";
    public static final String IMG_PATH = "images/cards/CraftMedicinae.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int POTENCY = 3;
    private static final int UPG_POTENCY = 1;
    private static final int TERRA_AMT = 5;

    public CraftMedicinae() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                AbstractCard.CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                AbstractCard.CardRarity.RARE,
                AbstractCard.CardTarget.SELF
        );
        this.tags.add(CRAFT);
        this.potency = this.basePotency = POTENCY;
        this.exhaust = true;
        this.tags.add(AbstractCard.CardTags.HEALING);
    }

    public void applyPowers() {
        super.applyPowers();
        checkElements(0, TERRA_AMT, 0, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (checkElements(0, TERRA_AMT, 0)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new MedicinaeRune(this.potency)));
        }
    }

    public AbstractCard makeCopy() {
        return new CraftMedicinae();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradePotency(UPG_POTENCY);
        }
    }

}
