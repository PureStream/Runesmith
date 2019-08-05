package runesmith.utils;

import basemod.ReflectionHacks;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MultiTipRender {
    private static float BODY_TEXT_WIDTH = 0;
    private static float TIP_DESC_LINE_SPACING = 0;
    public static final Logger logger = LogManager.getLogger(RunesmithMod.class.getName());
    private static float BOX_EDGE_H = 0;

    public static void renderMultiTip(SpriteBatch sb, ArrayList<TooltipInfo> tips, float x, float y){
        if(BOX_EDGE_H == 0){
//            logger.info("getting constant");
            getConstants();
        }
        float ypos = y;

//        logger.info("attempting render");

        for(TooltipInfo tip:tips){
//            logger.info(tip.title+tip.description);
            float height = FontHelper.getSmartHeight(FontHelper.tipBodyFont, tip.description, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING)
                    + 7.0F * Settings.scale;
            ReflectionHacks.setPrivateStatic(TipHelper.class, "renderedTipThisFrame", false);
            TipHelper.renderGenericTip(x,ypos,tip.title,tip.description);
            TipHelper.render(sb);
            ypos -= height + BOX_EDGE_H * 3.15F;
        }
        ReflectionHacks.setPrivateStatic(TipHelper.class, "renderedTipThisFrame", false);
    }

    private static void getConstants()
    {
        try {
            Field f = TipHelper.class.getDeclaredField("BODY_TEXT_WIDTH");
            f.setAccessible(true);
            BODY_TEXT_WIDTH = f.getFloat(null);

            f = TipHelper.class.getDeclaredField("TIP_DESC_LINE_SPACING");
            f.setAccessible(true);
            TIP_DESC_LINE_SPACING = f.getFloat(null);

            f = TipHelper.class.getDeclaredField("BOX_EDGE_H");
            f.setAccessible(true);
            BOX_EDGE_H = f.getFloat(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
