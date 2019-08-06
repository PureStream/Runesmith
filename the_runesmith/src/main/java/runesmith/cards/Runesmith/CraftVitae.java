package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.VitaeRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class CraftVitae extends AbstractRunicCard{
    public static final String ID = "Runesmith:CraftVitae";
    public static final String IMG_PATH = "runesmith/images/cards/CraftVitae.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int POTENCY = 5;
    private static final int UPGRADE_PLUS_POT = 2;
    private static final int IGNIS_AMT = 1;
    private static final int TERRA_AMT = 2;
//    private static final int AQUA_AMT = 1;

    public CraftVitae(){
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
        this.elementCost = new int[]{IGNIS_AMT,TERRA_AMT,0};
        this.potency = this.basePotency = POTENCY;
        this.tags.add(RS_CRAFT);
}

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradePotency(UPGRADE_PLUS_POT);
        }
    }

    public void applyPowers() {
        super.applyPowers();
        checkElements(IGNIS_AMT, TERRA_AMT, 0, true);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (checkElements(IGNIS_AMT, TERRA_AMT, 0)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new VitaeRune(this.potency)));
        }
    }
}
