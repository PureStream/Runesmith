package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

import java.util.ArrayList;

public abstract class DowngradeCard {

    public static void downgrade(ArrayList<AbstractCard> group, AbstractCard select) {
        int index = group.indexOf(select);
        AbstractCard manualUpgrade = select.makeCopy();
        manualUpgrade.timesUpgraded = 0;
        for (int i = 0; i < select.timesUpgraded - 1; i++) {
            manualUpgrade.upgrade();
        }

        AbstractCard tmp = select.makeSameInstanceOf();

        tmp.upgraded = manualUpgrade.upgraded;
        tmp.timesUpgraded = manualUpgrade.timesUpgraded;
        tmp.name = manualUpgrade.name;
        tmp.rawDescription = manualUpgrade.rawDescription;
        tmp.target = manualUpgrade.target;
        tmp.baseDamage = manualUpgrade.baseDamage;
        tmp.baseBlock = manualUpgrade.baseBlock;
        tmp.baseMagicNumber = manualUpgrade.baseMagicNumber;
        tmp.cost = manualUpgrade.cost;
        tmp.costForTurn = manualUpgrade.costForTurn;

        EnhanceCountField.enhanceCount.set(tmp, 0);
        EnhanceCountField.lastEnhance.set(tmp, 0);
        EnhanceCountField.enhanceReset.set(tmp, false);
        CardStasisStatus.isStasis.set(tmp, false);

        if(tmp instanceof AbstractRunicCard && manualUpgrade instanceof AbstractRunicCard){
            ((AbstractRunicCard) tmp).basePotency = ((AbstractRunicCard) manualUpgrade).basePotency;
        }

        tmp.initializeDescription();

        group.set(index, tmp);
    }

    public static boolean canDowngrade(AbstractCard c) {
        return c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c);
    }

}
