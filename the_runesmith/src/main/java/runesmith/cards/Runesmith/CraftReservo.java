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
import runesmith.orbs.ReservoRune;
import runesmith.patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.List;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class CraftReservo extends AbstractRunicCard {

    public static final String ID = "Runesmith:CraftReservo";
    public static final String IMG_PATH = "runesmith/images/cards/CraftReservo.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final UIStrings runeTips = CardCrawlGame.languagePack.getUIString(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 0;
    private static final int AQUA_AMT = 2;

    private List<TooltipInfo> tips = new ArrayList<>();

    public CraftReservo() {
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
        this.tags.add(RS_CRAFT);
        this.elementCost = new int[]{0,0,AQUA_AMT};
//        this.exhaust = true;
//        this.baseMagicNumber = this.magicNumber = AQUA_AMT;
    }

    public void applyPowers() {
        super.applyPowers();
        checkElements(0, 0, AQUA_AMT, true);
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
        if (checkElements(0, 0, this.magicNumber)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new ReservoRune(this.upgraded)));
        }
    }

    public AbstractCard makeCopy() {
        return new CraftReservo();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
