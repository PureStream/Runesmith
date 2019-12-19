package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.relics.NanobotsAction;

public class Nanobots extends AbstractRunesmithRelic {

    public static final String ID = "Runesmith:Nanobots";
    private static final String IMG = "runesmith/images/relics/Nanobots.png"; //<--------- Need some img
//    private static final String IMG_O = "runesmith/images/relics/Nanobots_o.png";

    public Nanobots() {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.CLINK, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new NanobotsAction());
    }

    public AbstractRelic makeCopy() {
        return new Nanobots();
    }

}
