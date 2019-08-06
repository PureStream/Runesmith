package runesmith.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import runesmith.cards.Runesmith.AbstractRunicCard;

@SpirePatch(
        clz = AbstractCard.class,
        method = "renderEnergy",
        paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"}
)
public class RunicCardElementRenderInLibraryPatch {
    public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
        if (__instance instanceof AbstractRunicCard) {
            AbstractRunicCard.renderElementsCost((AbstractRunicCard) __instance, sb);
        }
    }
}
