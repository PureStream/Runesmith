package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.PlayerRuneField;

public class RemoveRuneAction extends AbstractGameAction {

    private RuneOrb orb;

    public RemoveRuneAction(RuneOrb runeToRemove) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.BLOCK;
        this.orb = runeToRemove;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            PlayerRune playerRune = PlayerRuneField.playerRune.get(AbstractDungeon.player);
            playerRune.removeRune();
//            AbstractDungeon.actionManager.addToTop(new RemoveRuneAndSlotAction(orb));

        }
//		    tickDuration();
        this.isDone = true;
    }
}
