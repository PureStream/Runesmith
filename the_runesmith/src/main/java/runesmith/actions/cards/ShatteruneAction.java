package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import runesmith.actions.BreakRuneAction;
import runesmith.actions.BreakWithoutRemovingRuneAction;
import runesmith.orbs.RuneOrb;

public class ShatteruneAction extends AbstractGameAction{
	private boolean freeToPlayOnce = false;
	private AbstractPlayer p;
	private int energyOnUse = -1;
	private boolean upgraded;
	private RuneOrb rune;
	
	public ShatteruneAction(AbstractPlayer p, int energyOnUse, boolean upgraded, boolean freeToPlayOnce, RuneOrb runeToBreak) {
		this.p = p;
		this.duration = Settings.ACTION_DUR_XFAST;
		this.actionType = ActionType.SPECIAL;
		this.energyOnUse = energyOnUse;
		this.upgraded = upgraded;
		this.freeToPlayOnce = freeToPlayOnce;
		this.rune = runeToBreak;
	}

	public void update() {
		if(p.hasOrb()) {
			int effect = EnergyPanel.totalCount;
			if(this.energyOnUse != -1) {
				effect = this.energyOnUse;
			}
			
			if(this.p.hasRelic("Chemical X")) {
				effect += 2;
				this.p.getRelic("Chemical X").flash();
			}
			
			if(this.upgraded) {
				effect++;
			}
			
			if(effect > 0) {
				for(int i = 0; i < effect - 1; i++) {
					AbstractDungeon.actionManager.addToBottom(new BreakWithoutRemovingRuneAction(1, this.rune));					
				}
				AbstractDungeon.actionManager.addToBottom(new BreakRuneAction(this.rune));
				
			
				if(!this.freeToPlayOnce) {
					this.p.energy.use(EnergyPanel.totalCount);
				}
			}
		}
		this.isDone = true;
	}

}
