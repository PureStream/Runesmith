package runesmith.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.TipHelper;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.stream.Collectors;

@SpirePatch(clz = TipHelper.class, method = "renderTipForCard")
public class CardTooltipPatch {
    private static final String ENHANCE_PROPER_NAME = "runesmith:enhance";
    private static final String STASIS_PROPER_NAME = "runesmith:stasis";

    @SpireInsertPatch(locator = Locator.class)
    public static void addCustomTip(AbstractCard c, SpriteBatch sb, @ByRef ArrayList<String>[] keywords) {
        keywords[0] = addKeywords(c, keywords[0]);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctBehavior) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(TipHelper.class, "KEYWORDS");
            return LineFinder.findInOrder(ctBehavior, finalMatcher);
        }
    }

    private static ArrayList<String> addKeywords(AbstractCard c, ArrayList<String> keywords) {
        if (EnhanceCountField.enhanceCount.get(c) > 0) {
            if (!keywords.contains(ENHANCE_PROPER_NAME))
                keywords.add(ENHANCE_PROPER_NAME);
        }
        if (CardStasisStatus.isStasis.get(c)) {
            if (!keywords.contains(STASIS_PROPER_NAME))
                keywords.add(STASIS_PROPER_NAME);
        }
        return keywords.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }
}
