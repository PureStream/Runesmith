package runesmith.actions;
import com.megacrit.cardcrawl.core.Settings;

import runesmith.orbs.RuneOrb;

public class BreakWithoutRemovingRuneAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
{
	private int orbCount;
	private RuneOrb rune;

	public BreakWithoutRemovingRuneAction(int amount, RuneOrb rune)
	{
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

	public void update()
	{
		if (this.duration == this.startDuration) {
			for (int i = 0; i < this.orbCount; i++) {
				if(rune.useMultiBreak) {
					rune.onMultiBreak();
				}else {
					rune.onBreak();
				}
			}
		}
		tickDuration();
	}
}