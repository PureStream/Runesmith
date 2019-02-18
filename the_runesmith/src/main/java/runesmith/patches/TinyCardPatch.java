package runesmith.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.runHistory.TinyCard;
import runesmith.RunesmithMod;

public class TinyCardPatch {

    @SpirePatch(clz = TinyCard.class, method="getIconBackgroundColor")
    public static class getIconBackgroundColor {

        public static SpireReturn<Color> Prefix(TinyCard __instance, AbstractCard card)
        {
            if (card.color == AbstractCardEnum.RUNESMITH_BEIGE) {
                return SpireReturn.Return(RunesmithMod.BEIGE.cpy());
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = TinyCard.class, method="getIconDescriptionColor")
    public static class getIconDescriptionColor {

        public static SpireReturn<Color> Prefix(TinyCard __instance, AbstractCard card)
        {
            if (card.color == AbstractCardEnum.RUNESMITH_BEIGE) {
                return SpireReturn.Return(RunesmithMod.BEIGE.cpy());
            }
            return SpireReturn.Continue();
        }
    }

}