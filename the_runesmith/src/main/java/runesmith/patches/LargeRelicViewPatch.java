package runesmith.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import runesmith.relics.AbstractRunesmithRelic;

@SpirePatch(
        clz= SingleRelicViewPopup.class,
        method="initializeLargeImg"
)
public class LargeRelicViewPatch
{
    @SpireInsertPatch(
            rloc=0,
            localvars={"relic", "largeImg"}
    )
    public static SpireReturn<Void> Insert(SingleRelicViewPopup __instance, AbstractRelic relic, @ByRef Texture[] largeImg)
    {
        if (relic instanceof AbstractRunesmithRelic) {
            largeImg[0] = ((AbstractRunesmithRelic) relic).getLargeRelicImage();
            return SpireReturn.Return(null);
        }
        return SpireReturn.Continue();
    }
}
