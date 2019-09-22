package runesmith.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.ui.ElementsCounter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleCardViewElementRender {
    private static Texture  ELEMENT_ORB_RED_L, ELEMENT_ORB_GREEN_L, ELEMENT_ORB_BLUE_L;

    private static float yOffset = 120.0F * Settings.scale;
    private static float centerX = (float)Settings.WIDTH / 2.0F;
    private static float centerY = (float)Settings.HEIGHT / 2.0F;

    private static void renderElementHelper(SpriteBatch sb, Texture img, float drawX, float drawY) {
        sb.draw(img, drawX, drawY,
                    0, 0, 164.0F, 164.0F,
                     Settings.scale,  Settings.scale,
                    0, 0, 0, 164, 164, false, false);

    }

    public static void renderElementCost(AbstractRunicCard card, SpriteBatch sb){
        int[] elementCost;

        if(ELEMENT_ORB_RED_L == null){
            ELEMENT_ORB_RED_L = ImageMaster.loadImage("runesmith/images/cardui/1024/card_element_orb_red.png");
            ELEMENT_ORB_GREEN_L = ImageMaster.loadImage("runesmith/images/cardui/1024/card_element_orb_green.png");
            ELEMENT_ORB_BLUE_L = ImageMaster.loadImage("runesmith/images/cardui/1024/card_element_orb_blue.png");
        }

        float fScaleX = FontHelper.SCP_cardEnergyFont.getData().scaleX;

        FontHelper.SCP_cardEnergyFont.getData().setScale(0.75F);

        if(!card.isLocked && card.isSeen) {

            elementCost = card.getElementCost();

            int counter = 0;

            //logger.info("attempting render");
            for (int i = 0; i < 3; i++) {
                Texture tex;
                ElementsCounter.Elements elem;
                switch (i) {
                    case 0:
                        tex = ELEMENT_ORB_RED_L;
                        break;
                    case 1:
                        tex = ELEMENT_ORB_GREEN_L;
                        break;
                    case 2:
                        tex = ELEMENT_ORB_BLUE_L;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                }
                if (elementCost[i] != 0) {

                    //card.renderElementHelper(sb, (float)Settings.WIDTH / 2.0F - 270.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F + 380.0F * Settings.scale);
                    renderElementHelper(sb, tex, centerX - 348.0F * Settings.scale,
                            centerY + 163.0F * Settings.scale - yOffset * counter);

                    Color c = Settings.CREAM_COLOR;
                    String msg = elementCost[i] + "";
                    FontHelper.renderFont(sb, FontHelper.SCP_cardEnergyFont, elementCost[i]+"", 682.0F * Settings.scale,
                            (float)Settings.HEIGHT / 2.0F + 268.0F * Settings.scale - yOffset * counter, c);

                    counter++;
                }
            }
        }
        FontHelper.SCP_cardEnergyFont.getData().setScale(fScaleX);
    }
}
