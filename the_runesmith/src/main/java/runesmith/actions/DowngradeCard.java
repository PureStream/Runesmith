package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class DowngradeCard {
    public static void downgrade(ArrayList<AbstractCard> group, AbstractCard select) {
        int index = group.indexOf(select);
        AbstractCard manualUpgrade = select.makeCopy();
        for (int i = 0; i < select.timesUpgraded - 1; i++) {
            manualUpgrade.upgrade();
        }

//        if(select.timesUpgraded > 0){
//            select.timesUpgraded--;
//            if(select.timesUpgraded == 0){
//                select.upgraded = false;
//            }
//        }
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

        EnhanceCountField.enhanceCount.set(tmp, 0);
        EnhanceCountField.lastEnhance.set(tmp, 0);
        EnhanceCountField.enhanceReset.set(tmp, false);
        CardStasisStatus.isStasis.set(tmp, false);

        if(tmp instanceof AbstractRunicCard && manualUpgrade instanceof AbstractRunicCard){
            ((AbstractRunicCard) tmp).basePotency = ((AbstractRunicCard) manualUpgrade).basePotency;
        }

        tmp.initializeDescription();

        group.set(index, tmp);

//        Class<? extends AbstractCard> ci = select.getClass();
//        try {
//            AbstractCard tmp = ci.getConstructor().newInstance();
//            for (int i = 0; i < select.timesUpgraded - 1; i++) {
//                tmp.upgrade();
//            }
//            group.set(index, tmp);
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
    }
}
