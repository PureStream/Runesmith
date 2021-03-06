package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.powers.PotentialPower;

public class MiniCore extends AbstractRunesmithRelic {

    public static final String ID = "Runesmith:MiniCore";
    private static final String IMG = "runesmith/images/relics/MiniCore.png"; //<--------- Need some img
//    private static final String IMG_O = "runesmith/images/relics/MiniCore_o.png";
    private static final int POWER_AMT = 1;

    public MiniCore() {
        super(ID, IMG, RelicTier.COMMON, LandingSound.MAGICAL, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(
                new ApplyPowerAction(p, p,
                        new PotentialPower(p, POWER_AMT), POWER_AMT));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));
    }

    public AbstractRelic makeCopy() {
        return new MiniCore();
    }

}
