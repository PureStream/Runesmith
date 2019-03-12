package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import runesmith.orbs.RuneOrb;

import java.util.Collections;

public class RemoveRuneAndSlotAction extends AbstractGameAction {

    private RuneOrb rune;

    RemoveRuneAndSlotAction(RuneOrb runeToRemove) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.BLOCK;
        this.rune = runeToRemove;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;

            // Rotate up the remaining orbs
            int index = p.orbs.indexOf(this.rune);

            AbstractOrb orbSlot = new EmptyOrbSlot();
            for (int i = index + 1; i < p.orbs.size(); i++) {
                Collections.swap(p.orbs, i, i - 1);
            }
            p.orbs.set(p.orbs.size() - 1, orbSlot);
            for (int i = index; i < p.orbs.size(); i++) {
                p.orbs.get(i).setSlot(i, p.maxOrbs);
            }
            AbstractDungeon.player.decreaseMaxOrbSlots(1);
        }
        tickDuration();
    }
}
