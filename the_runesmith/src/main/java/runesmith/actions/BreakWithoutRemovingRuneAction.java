package runesmith.actions;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.PlayerRuneField;

public class BreakWithoutRemovingRuneAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
//    public static final Logger logger = LogManager.getLogger(BreakWithoutRemovingRuneAction.class.getName());
    private int orbCount;
    private RuneOrb rune;

    public BreakWithoutRemovingRuneAction(int amount, RuneOrb rune) {
        if (Settings.FAST_MODE) {
            this.startDuration = Settings.ACTION_DUR_FAST;
        } else {
            this.startDuration = Settings.ACTION_DUR_XFAST;
        }
        this.rune = rune;
        this.duration = this.startDuration;
        this.orbCount = amount;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            for (int i = 0; i < this.orbCount; i++) {
//                logger.info("breaking once");
                PlayerRune playerRune = PlayerRuneField.playerRune.get(AbstractDungeon.player);
                playerRune.breakWithoutLosingRune();
            }
        }
//		this.isDone = true;
        tickDuration();
    }
}