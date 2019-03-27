package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.PlayerRuneField;

public class BreakRuneAction extends AbstractGameAction {

    private RuneOrb rune;

    public BreakRuneAction(RuneOrb runeToBreak) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.rune = runeToBreak;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
//            RuneOrb.runeCountDown();
//            AbstractDungeon.actionManager.addToTop(new RemoveRuneAndSlotAction(rune));

            PlayerRune playerRune = PlayerRuneField.playerRune.get(AbstractDungeon.player);
            playerRune.breakRune(this.rune);
        }
        this.isDone = true;
    }
}
