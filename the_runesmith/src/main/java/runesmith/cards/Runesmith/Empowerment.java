package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import runesmith.patches.AbstractCardEnum;
import runesmith.ui.ElementsCounter;

public class Empowerment extends CustomCard {

    public static final String ID = "Runesmith:Empowerment";
    public static final String IMG_PATH = "runesmith/images/cards/Empowerment.png";
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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int tmpStr = ElementsCounter.getIgnis();
        int tmpDex = ElementsCounter.getTerra();
        if (tmpStr > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, AbstractDungeon.player, new StrengthPower(p, tmpStr), tmpStr));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, AbstractDungeon.player, new LoseStrengthPower(p, tmpStr), tmpStr));
        }
        if (tmpDex > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, AbstractDungeon.player, new DexterityPower(p, tmpDex), tmpDex));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, AbstractDungeon.player, new LoseDexterityPower(p, tmpDex), tmpDex));
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