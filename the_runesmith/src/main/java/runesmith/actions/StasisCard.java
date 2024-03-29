package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import runesmith.cards.Runesmith.NoStasisCard;
import runesmith.patches.CardStasisStatus;

public abstract class StasisCard {

    public static void stasis(AbstractCard c) {
        CardStasisStatus.isStasis.set(c, true);
        //update card desc
//		AdditionalCardDescriptions.modifyDescription(c);
        c.initializeDescription();
    }

    public static boolean canStasis(AbstractCard c) {
        return !(CardStasisStatus.isStasis.get(c) || (c instanceof NoStasisCard)) && EnhanceCard.canEnhance(c); //&& !(c.type == CardType.CURSE) && !(c.type == CardType.STATUS);
    }
}
