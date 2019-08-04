package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.RunesmithMod;
import runesmith.patches.AbstractCardEnum;

public class HammerAndChisel extends CustomCard {

    public static final String ID = "Runesmith:HammerAndChisel";
    public static final String IMG_PATH = "runesmith/images/cards/HammerAndChisel.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;

    public HammerAndChisel() {
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
//        this.tags.add(RS_HAMMER);
//        this.tags.add(RS_CHISEL);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c1;
        AbstractCard c2;
        c1 = RunesmithMod.getHammerCards().getRandomCard(AbstractDungeon.cardRandomRng).makeCopy();
        c2 = RunesmithMod.getChiselCards().getRandomCard(AbstractDungeon.cardRandomRng).makeCopy();

        if (this.upgraded) {
            c1.upgrade();
            c2.upgrade();
        }
        c1.modifyCostForCombat(-1);
        c2.modifyCostForCombat(-1);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c2));
    }

    public AbstractCard makeCopy() {
        return new HammerAndChisel();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
