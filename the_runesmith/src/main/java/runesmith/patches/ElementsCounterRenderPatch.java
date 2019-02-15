package runesmith.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runesmith.RunesmithMod;

@SpirePatch(clz = EnergyPanel.class, method = "renderOrb", paramtypes = { "com.badlogic.gdx.graphics.g2d.SpriteBatch"})
public class ElementsCounterRenderPatch {
    public static void Prefix(EnergyPanel __instance, SpriteBatch sb){
        if(RunesmithMod.getElementsRender()){
            RunesmithMod.renderElementsCounter(sb, __instance.current_x);
        }
    }
}
