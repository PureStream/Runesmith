package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.ObretioRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class CraftObretio extends AbstractRunicCard {

    public static final String ID = "Runesmith:CraftObretio";
    public static final String IMG_PATH = "runesmith/images/cards/CraftObretio.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int POTENCY = 3;
    private static final int UPGRADE_PLUS_POT = 2;
    private static final int TERRA_AMT = 2;
    private static final int AQUA_AMT = 1;

    public CraftObretio() {
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

        this.potency = this.basePotency = POTENCY;
        this.tags.add(RS_CRAFT);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        checkElements(0, TERRA_AMT, AQUA_AMT, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (checkElements(0, TERRA_AMT, AQUA_AMT)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new ObretioRune(this.potency)));
        }
    }

    public AbstractCard makeCopy() {
        return new CraftObretio();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradePotency(UPGRADE_PLUS_POT);
        }
    }

}