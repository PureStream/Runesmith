package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runesmith.actions.cards.GenerateForcefieldAction;
import runesmith.patches.AbstractCardEnum;

public class GenerateForcefield extends CustomCard implements BreakCard{
    public static final String ID = "Runesmith:GenerateForcefield";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/GenerateForcefield.png"; //need some img
    private static final int COST = -1;

    public GenerateForcefield() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.SELF
        );
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount)
            this.energyOnUse = EnergyPanel.totalCount;

        AbstractDungeon.actionManager.addToBottom(new GenerateForcefieldAction(this.energyOnUse, this.freeToPlayOnce, this.upgraded));
    }

    public AbstractCard makeCopy() {
        return new GenerateForcefield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }

    @Override
    public int showBreakValueAt() {
        return 0;
    }

    @Override
    public boolean showAllBreakValues() {
        return true;
    }
}
