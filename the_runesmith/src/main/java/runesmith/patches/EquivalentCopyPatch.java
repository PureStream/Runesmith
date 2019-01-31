package runesmith.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.cards.Runesmith.AbstractRunicCard;

public class EquivalentCopyPatch {
    public static final Logger logger = LogManager.getLogger(EquivalentCopyPatch.class.getName());

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class CopyAdditionalValues{
        @SpireInsertPatch(rloc = 6, localvars = {"card"})
        public static void Insert(AbstractCard __instance, @ByRef AbstractCard[] card){
            EnhanceCountField.enhanceCount.set(card[0], EnhanceCountField.enhanceCount.get(__instance));
            CardStasisStatus.isStasis.set(card[0], CardStasisStatus.isStasis.get(__instance));
            if(__instance instanceof AbstractRunicCard){
                ((AbstractRunicCard) card[0]).basePotency = ((AbstractRunicCard) __instance).basePotency;
                ((AbstractRunicCard) card[0]).freeElementOnce = ((AbstractRunicCard) __instance).freeElementOnce;
            }
            logger.info(""+card[0].name);
            card[0].rawDescription = __instance.rawDescription;
//            card[0].description = __instance.description;
            logger.info(card[0].rawDescription);
        }
    }
}
