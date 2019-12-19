package runesmith.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardGlowBorder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;
import runesmith.cards.Runesmith.AbstractRunicCard;

public class RunicCardGlow {
    public static final Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    private static TextureAtlas CARD_RUNIC_BG_SILHOUETTE = new TextureAtlas(Gdx.files.internal("runesmith/images/cardui/512/cardui.atlas"));

    private static TextureAtlas.AtlasRegion CARD_CRAFT_RUNIC_ATTACK_BG_SILHOUETTE = CARD_RUNIC_BG_SILHOUETTE.findRegion("craftable_attack_silhouette");
    private static TextureAtlas.AtlasRegion CARD_CRAFT_RUNIC_SKILL_BG_SILHOUETTE = CARD_RUNIC_BG_SILHOUETTE.findRegion("craftable_skill_silhouette");
    private static TextureAtlas.AtlasRegion CARD_CRAFT_RUNIC_POWER_BG_SILHOUETTE = CARD_RUNIC_BG_SILHOUETTE.findRegion("craftable_attack_silhouette");
    private static TextureAtlas.AtlasRegion CARD_RUNIC_ATTACK_BG_SILHOUETTE = CARD_RUNIC_BG_SILHOUETTE.findRegion("attack_silhouette");
    private static TextureAtlas.AtlasRegion CARD_RUNIC_SKILL_BG_SILHOUETTE = CARD_RUNIC_BG_SILHOUETTE.findRegion("skill_silhouette");
    private static TextureAtlas.AtlasRegion CARD_RUNIC_POWER_BG_SILHOUETTE = CARD_RUNIC_BG_SILHOUETTE.findRegion("attack_silhouette");


    @SpirePatch(clz = CardGlowBorder.class, method = SpirePatch.CONSTRUCTOR, paramtypez = {
            AbstractCard.class,
            Color.class
    })
    public static class CardGlowPatch {
        @SpirePostfixPatch
        public static void runicCardGlowColor(CardGlowBorder __instance, AbstractCard inputCard, Color gColor) {
            if(inputCard.color == AbstractCardEnum.RUNESMITH_BEIGE) {
                switch (inputCard.type) {
                    case ATTACK:
                        ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", CARD_RUNIC_ATTACK_BG_SILHOUETTE);
                        break;
                    case SKILL:
                        ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", CARD_RUNIC_SKILL_BG_SILHOUETTE);
                        break;
                    case POWER:
                        ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", CARD_RUNIC_POWER_BG_SILHOUETTE);
                        break;
                }
                if (inputCard instanceof AbstractRunicCard) {
                    if (((AbstractRunicCard) inputCard).isCraftable) {
                        switch (inputCard.type) {
                            case ATTACK:
                                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", CARD_CRAFT_RUNIC_ATTACK_BG_SILHOUETTE);
                                break;
                            case SKILL:
                                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", CARD_CRAFT_RUNIC_SKILL_BG_SILHOUETTE);
                                break;
                            case POWER:
                                ReflectionHacks.setPrivate(__instance, CardGlowBorder.class, "img", CARD_CRAFT_RUNIC_POWER_BG_SILHOUETTE);
                                break;
                        }
                    }
                }
            }
        }
    }
}
