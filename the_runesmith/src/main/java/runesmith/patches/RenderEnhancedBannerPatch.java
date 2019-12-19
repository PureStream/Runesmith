package runesmith.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import runesmith.cards.Runesmith.AbstractRunicCard;

import java.util.ArrayList;

public class RenderEnhancedBannerPatch {

    private static Texture ENHANCED_BANNER = ImageMaster.loadImage("runesmith/images/cardui/512/enhance_banner.png");

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderImage"
    )
    public static class RenderEnhanceBanner {
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractCard __instance, SpriteBatch sb, boolean hovered, boolean selected) {
            if (EnhanceCountField.enhanceCount.get(__instance)>0) {
                renderBanner(sb,__instance);
            }
        }

        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
            {
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageFinalGive");
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "renderBannerImage");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, preMatchers, finalMatcher)[0]};
            }
        }
    }

    static void renderBanner(SpriteBatch sb, AbstractCard c){
        Color color = (Color) ReflectionHacks.getPrivate(c, AbstractCard.class, "renderColor");
        renderHelper(sb, c, color, ENHANCED_BANNER, c.current_x, c.current_y);
    }

    private static void renderHelper(SpriteBatch sb, AbstractCard c, Color color, Texture img, float drawX, float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX - 256f, drawY - 256f,
                256.0F, 256.0F, 512.0F, 512.0F,
                c.drawScale * Settings.scale, c.drawScale * Settings.scale,
                c.angle, 0, 0, 512, 512, false, false);
    }

}