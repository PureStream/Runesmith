package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.relics.NanobotsAction;

public class Nanobots extends CustomRelic {

    public static final String ID = "Runesmith:Nanobots";
    private static final String IMG = "images/relics/Nanobots.png"; //<--------- Need some img
    private static final String IMG_O = "images/relics/Nanobots_o.png";

    public Nanobots() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        AbstractDungeon.actionManager.addToBottom(new NanobotsAction());
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public AbstractRelic makeCopy() {
        return new Nanobots();
    }

}
