package runesmith.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
public class CardStatisStatus {
	public static SpireField<Boolean> isStatis = new SpireField<Boolean>(( )-> false);
}
