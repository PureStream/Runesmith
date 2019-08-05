package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.IncendiumRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class CraftIncendium extends AbstractRunicCard {

    public static final String ID = "Runesmith:CraftIncendium";
    public static final String IMG_PATH = "runesmith/images/cards/CraftIncendium.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int POTENCY = 4;
    private static final int UPG_POTENCY = 2;
    private static final int IGNIS_AMT = 3;

    public CraftIncendium() {
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
//        this.overchargePot = IncendiumRune.getOverchargeAmt();
        this.basePotency = POTENCY;
        this.potency = this.basePotency;
        this.tags.add(RS_CRAFT);

    }

    public void applyPowers() {
        super.applyPowers();
//        boolean prevCharge = this.isOvercharge;
//        this.isOvercharge = this.potency > this.overchargePot;
//        if(prevCharge!=this.isOvercharge){
//            initializeDescription();
//        }
        checkElements(IGNIS_AMT, 0, 0, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (checkElements(IGNIS_AMT, 0, 0)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new IncendiumRune(this.potency)));
        }
    }

    public AbstractCard makeCopy() {
        return new CraftIncendium();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradePotency(UPG_POTENCY);
        }
    }

}
