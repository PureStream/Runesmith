package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class CoreCrystal extends CustomRelic {

    public static final String ID = "Runesmith:CoreCrystal";
    private static final String IMG = "images/relics/CoreCrystal.png"; //<--------- Need some img
    private static final String IMG_O = "images/relics/CoreCrystal_o.png";

    public CoreCrystal() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(BrokenRuby.ID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(BrokenRuby.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(BrokenRuby.ID);
    }

    public AbstractRelic makeCopy() {
        return new CoreCrystal();
    }

}