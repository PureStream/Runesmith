package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class PocketReactor extends AbstractRunesmithRelic {

    public static final String ID = "Runesmith:PocketReactor";
    private static final String IMG = "runesmith/images/relics/PocketReactor.png"; //<--------- Need some img
//    private static final String IMG_O = "runesmith/images/relics/PocketReactor_o.png";

    public PocketReactor() {
        super(ID, IMG, RelicTier.BOSS, LandingSound.HEAVY, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public AbstractRelic makeCopy() {
        return new PocketReactor();
    }

}
