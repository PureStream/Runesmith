package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.RunesmithMod;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.relics.CoreCrystal;
import runesmith.ui.ElementsCounter;

public class Terraform extends CustomCard {
    public static final String ID = "Runesmith:Terraform";
    public static final String IMG_PATH = "runesmith/images/cards/Terraform.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int TERRA_AMT = 1;
    private static final int TIMES_AMT = 2;
    private static final int UPGRADE_TIMES_AMT = 1;


    public Terraform() {
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
        this.baseBlock = this.block = 0;
        this.baseMagicNumber = this.magicNumber = TIMES_AMT;
    }

    @Override
    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        int multiplier = this.magicNumber;

        int playerTerra = 1 + ElementsCounter.getTerra();
        int maxStacks = p.hasRelic(CoreCrystal.ID) ? CoreCrystal.MAX_ELEMENTS : RunesmithMod.DEFAULT_MAX_ELEMENTS;
        if (playerTerra > maxStacks)
            playerTerra = maxStacks;

        this.baseBlock = playerTerra*multiplier;
        super.applyPowers();

        String extendString = EXTENDED_DESCRIPTION[0];

        this.rawDescription = DESCRIPTION + extendString;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyElementsPowerAction(p, p, 0, TERRA_AMT, 0));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, 
//				new TerraPower(p, TERRA_AMT),TERRA_AMT));\
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );
    }

    public AbstractCard makeCopy() {
        return new Terraform();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_TIMES_AMT);
        }
    }
}