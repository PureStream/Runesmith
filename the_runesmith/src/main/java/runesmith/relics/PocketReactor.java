package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PocketReactor extends CustomRelic {

    public static final String ID = "Runesmith:PocketReactor";
    private static final String IMG = "images/relics/PocketReactor.png"; //<--------- Need some img
    private static final String IMG_O = "images/relics/PocketReactor_o.png";

    public PocketReactor() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PocketReactor();
    }

}
