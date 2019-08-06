package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RemoveRuneAction;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.deprecated.PotentiaRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class CraftPotentia extends AbstractRunicCard {

    public static final String ID = "Runesmith:CraftPotentia";
    public static final String IMG_PATH = "runesmith/images/cards/CraftPotentia.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int POTENCY = 2;
    private static final int UPG_POTENCY = 1;
    private static final int ELEMENT_AMT = 2;

    public CraftPotentia() {
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
        this.elementCost = new int[]{2,2,2};
//        this.overchargePot = PotentiaRune.getOverchargeAmt();
        this.potency = this.basePotency = POTENCY;
        this.tags.add(RS_CRAFT);

    }

    public void applyPowers() {
        super.applyPowers();
//        boolean prevCharge = this.isOvercharge;
//        this.isOvercharge = this.potency > this.overchargePot;
//        if(prevCharge!=this.isOvercharge){
//            initializeDescription();
//        }
        checkElements(ELEMENT_AMT, ELEMENT_AMT, ELEMENT_AMT, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        if (checkElements(ELEMENT_AMT, ELEMENT_AMT, ELEMENT_AMT, false, true)) {
            RuneOrb r = RuneOrb.getFirstRune(p);
            if (r != null) {
                AbstractDungeon.actionManager.addToBottom(new RemoveRuneAction(r));
            }
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new PotentiaRune(this.potency)));
        }
    }

    public AbstractCard makeCopy() {
        return new CraftPotentia();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradePotency(UPG_POTENCY);
        }
    }

}
