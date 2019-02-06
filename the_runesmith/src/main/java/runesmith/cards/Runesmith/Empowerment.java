package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class Empowerment extends CustomCard {

    public static final String ID = "Runesmith:Empowerment";
    public static final String IMG_PATH = "images/cards/Empowerment.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final int COST = 1;
    private static final int COST_UPGRADE = 0;

    public Empowerment() {
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
        //this.tags.add(BaseModCardTags.BASIC_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int tmpStr = 0;
        int tmpDex = 0;
        for (AbstractPower pow : p.powers) {
            if (pow instanceof IgnisPower) {
                tmpStr = pow.amount;
            } else if (pow instanceof TerraPower) {
                tmpDex = pow.amount;
            }
        }
        if (tmpStr > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, AbstractDungeon.player, new StrengthPower(p, tmpStr), tmpStr));

            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, AbstractDungeon.player, new LoseStrengthPower(p, tmpStr), tmpStr));
        }
        if (tmpDex > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, AbstractDungeon.player, new DexterityPower(p, tmpDex), tmpDex));

            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(p, AbstractDungeon.player, new LoseDexterityPower(p, tmpDex), tmpDex));
        }
    }

    public AbstractCard makeCopy() {
        return new Empowerment();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(COST_UPGRADE);
        }
    }
}