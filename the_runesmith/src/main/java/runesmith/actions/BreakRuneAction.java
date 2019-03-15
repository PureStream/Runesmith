package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.orbs.RuneOrb;

public class BreakRuneAction extends AbstractGameAction {

    private RuneOrb orb;

    public BreakRuneAction(RuneOrb runeToBreak) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.orb = runeToBreak;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
//            RuneOrb.runeCountDown();
            AbstractDungeon.actionManager.addToTop(new RemoveRuneAndSlotAction(orb));

            orb.onBreak();
        }
        this.isDone = true;
    }
}
