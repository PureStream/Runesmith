package runesmith.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class RenderEnhancedBannerPatch {

    private static Texture ENHANCED_BANNER = ImageMaster.loadImage("runesmith/images/cardui/512/enhance_banner.png");
    private static Color textColor = new Color(1245392127);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:CardEnhanced");
    public static final String[] ENHANCE_TEXT = uiStrings.TEXT;

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

        BitmapFont font = FontHelper.cardDescFont_L;
        font.getData().setScale(1.0F);
        GlyphLayout gl = new GlyphLayout(font, EnhanceCountField.enhanceString.get(c));
        float scale = Math.min((170.0F)/gl.width, (16.0F)/gl.height)*c.drawScale;
        font.getData().setScale(scale*Settings.scale);
        FontHelper.renderRotatedText(sb, font, EnhanceCountField.enhanceString.get(c), c.current_x, c.current_y, 0.0F, 139.0F * Settings.scale * c.drawScale, c.angle, true, textColor);
        font.getData().setScale(1.0F);
    }

    private static void renderHelper(SpriteBatch sb, AbstractCard c, Color color, Texture img, float drawX, float drawY) {
        sb.setColor(color);
        sb.draw(img, drawX - 256f, drawY - 256f,
                256.0F, 256.0F, 512.0F, 512.0F,
                c.drawScale * Settings.scale, c.drawScale * Settings.scale,
                c.angle, 0, 0, 512, 512, false, false);
    }

}
