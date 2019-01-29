package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class DowngradeCard {
    public static void downgrade(ArrayList<AbstractCard> group, AbstractCard select) {
        int index = group.indexOf(select);
        Class<? extends AbstractCard> ci = select.getClass();
        try {
            AbstractCard tmp = ci.getConstructor().newInstance();
            for(int i = 0; i < select.timesUpgraded - 1; i++){
                tmp.upgrade();
            }
            group.set(index,tmp);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
