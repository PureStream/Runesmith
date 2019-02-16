package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.RunesmithMod;
import runesmith.ui.ElementsCounter;

import java.util.stream.IntStream;

public class CoreCrystal extends CustomRelic {

    public static final String ID = "Runesmith:CoreCrystal";
    private static final String IMG = "images/relics/CoreCrystal.png"; //<--------- Need some img
    private static final String IMG_O = "images/relics/CoreCrystal_o.png";

    public static final int MAX_ELEMENTS = 20;

    public CoreCrystal() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BrokenRuby.ID)) {
            IntStream.range(0, AbstractDungeon.player.relics.size())
                    .filter(i -> AbstractDungeon.player.relics.get(i).relicId.equals(BrokenRuby.ID))
                    .findFirst()
                    .ifPresent(i -> instantObtain(AbstractDungeon.player, i, true));
        } else
            super.obtain();
        ElementsCounter.setMaxElements(MAX_ELEMENTS);
    }

    @Override
    public void onUnequip(){
        ElementsCounter.setMaxElements(RunesmithMod.DEFAULT_MAX_ELEMENTS);
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BrokenRuby.ID);
    }

    public AbstractRelic makeCopy() {
        return new CoreCrystal();
    }

}