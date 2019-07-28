package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import runesmith.actions.ReduceElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.ui.ElementsCounter;

public class Augment extends CustomCard {

    public static final String ID = "Runesmith:Augment";
    public static final String IMG_PATH = "images/cards/Augment.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 10;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public Augment() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                AbstractCard.CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                AbstractCard.CardTarget.SELF
        );
        this.baseBlock = BLOCK_AMT;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p, p, this.block)
        );
        int aqua = ElementsCounter.getAqua();

        if(upgraded)
            aqua = (int) Math.round(aqua/2.0);
        if(aqua == 0) return;

        AbstractDungeon.actionManager.addToBottom(new ReduceElementsPowerAction(0, 0, aqua));
        if(!upgraded)
            aqua = aqua / 2;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new ArtifactPower(p, aqua),aqua));
    }

    public AbstractCard makeCopy() {
        return new Augment();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}