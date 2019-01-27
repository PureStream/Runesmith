package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IndustriaRune extends RuneOrb {
	
	public IndustriaRune() {
		super( "Industria",
				false,
				0);
		this.passiveAmount = 1;
	}
	
	@Override
	public void onStartOfTurn() {
		this.activateEffect();
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.passiveAmount));
	}
	
	@Override
	public void onBreak() {
		this.activateEffect();
		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.passiveAmount));
		AbstractDungeon.actionManager.addToTop(new GainEnergyAction(this.passiveAmount));
	}

	@Override
	public AbstractOrb makeCopy() { return new IndustriaRune(); }

}
