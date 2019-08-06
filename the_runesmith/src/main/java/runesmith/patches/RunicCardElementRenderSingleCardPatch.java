package runesmith.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.utils.SingleCardViewElementRender;

@SpirePatch(
        clz = SingleCardViewPopup.class,
        method = "renderCost",
        paramtypes = {"com.badlogic.gdx.graphics.g2d.SpriteBatch"}
)
public class RunicCardElementRenderSingleCardPatch {
    public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
        AbstractCard c = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
        if(c instanceof AbstractRunicCard) {
            SingleCardViewElementRender.renderElementCost((AbstractRunicCard) c, sb);
        }
    }
}

