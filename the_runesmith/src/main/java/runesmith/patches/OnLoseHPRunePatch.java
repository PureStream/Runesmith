package runesmith.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import javassist.CtBehavior;
import runesmith.effects.VitaeBlockedNumberEffect;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.orbs.VitaeRune;

import java.util.ArrayList;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="damage"
)
public class OnLoseHPRunePatch {
    @SpireInsertPatch(
            locator=Locator.class,
            localvars={"damageAmount", "hadBlock"}
    )
    public static void Insert(AbstractCreature __instance, DamageInfo info, @ByRef int[] damageAmount, @ByRef boolean[] hadBlock){
        int prevDamage = damageAmount[0];
        damageAmount[0] = reduceHpLoss(damageAmount[0]);
        if(prevDamage > damageAmount[0]){
            hadBlock[0] = true;
            CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
        }
    }

//    private static final float OFFSET_Y = -150.0F * Settings.scale;

    private static int reduceHpLoss(int i){
        AbstractPlayer p = AbstractDungeon.player;
        PlayerRune playerRune = PlayerRuneField.playerRune.get(p);
        for(RuneOrb r: playerRune.runes){
            if(r instanceof VitaeRune){
                int pre = i;
                i -= ((VitaeRune) r).losePotency(i);
                if(pre > i){
                    AbstractDungeon.effectList.add(new VitaeBlockedNumberEffect(r.hb.cX, r.hb.cY, pre - i + ""));
                }
            }
        }
        return i;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "decrementBlock");
            return offset(LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher), 1);
        }

        private static int[] offset(int[] originalArr, int offset) {
            for (int i = 0; i < originalArr.length; i++) {
                originalArr[i] += offset;
            }
            return originalArr;
        }
    }
}
