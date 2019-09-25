package runesmith.cards.Runesmith;

import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.PrismaticaRune;
import runesmith.patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class CraftPrismatica extends AbstractRunicCard {

    public static final String ID = "Runesmith:CraftPrismatica";
    public static final String IMG_PATH = "runesmith/images/cards/CraftPrismatica.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings runeTips = CardCrawlGame.languagePack.getUIString(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int ELEMENT_AMT = 2;

    private List<TooltipInfo> tips = new ArrayList<>();

    public CraftPrismatica() {
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
        this.elementCost = new int[]{ELEMENT_AMT,ELEMENT_AMT,ELEMENT_AMT};
        this.tags.add(RS_CRAFT);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        checkElements(ELEMENT_AMT, ELEMENT_AMT, ELEMENT_AMT, true);
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        this.tips.clear();
        if(!this.upgraded){
            this.tips.add(new TooltipInfo(runeTips.TEXT[0], runeTips.TEXT[2]));
        }else{
            this.tips.add(new TooltipInfo(runeTips.TEXT[1], runeTips.TEXT[3]));
        }
        return this.tips;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (checkElements(ELEMENT_AMT, ELEMENT_AMT, ELEMENT_AMT)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new PrismaticaRune(this.upgraded)));
        }
    }

    public AbstractCard makeCopy() {
        return new CraftPrismatica();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
