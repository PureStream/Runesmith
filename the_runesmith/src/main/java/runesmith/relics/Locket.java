package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;
import runesmith.actions.cards.MetallurgicalResearchAction;
import runesmith.cards.Runesmith.LastStand;

public class Locket extends AbstractRunesmithRelic {

    public static final String ID = "Runesmith:Locket";
    private static final String IMG = "runesmith/images/relics/Locket.png"; //<--------- Need some img
//    private static final String IMG_O = "runesmith/images/relics/Locket_o.png";
    private boolean isAvailable = false;
    private boolean isUsedThisCombat = false;

    public Locket() {
        super(ID, IMG, RelicTier.RARE, LandingSound.CLINK, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atPreBattle() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth > p.maxHealth * 0.15) {
            isAvailable = true;
            this.pulse = true;
            beginPulse();
        }
    }

    public int onPlayerHeal(int healAmount) {
        AbstractPlayer p = AbstractDungeon.player;

        // try catch to prevent exception when cannot getCurrRoom
        try {
            if (p.currentHealth + healAmount > p.maxHealth * 0.15 && !isAvailable && !isUsedThisCombat && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                isAvailable = true;
                this.pulse = true;
                beginPulse();
            }
        } catch (Exception ignored) {
        }

        return healAmount;
    }

    public void onLoseHp(int damageAmount) {
        AbstractPlayer p = AbstractDungeon.player;
        if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) && isAvailable && !isUsedThisCombat && p.currentHealth - damageAmount < p.maxHealth * 0.15) {
            flash();
            this.pulse = false;
//            AbstractCard lastStand = new LastStand();
//            p.drawPile.addToTop(lastStand);
            AbstractDungeon.actionManager.addToTop(new MetallurgicalResearchAction());
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(p, this));
            isAvailable = false;
            isUsedThisCombat = true;
        }
    }

    public void onVictory() {
        isAvailable = false;
        isUsedThisCombat = false;
        this.pulse = false;
    }

    public AbstractRelic makeCopy() {
        return new Locket();
    }

}
