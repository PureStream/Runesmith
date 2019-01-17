package runesmith.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IndustriaRune extends RuneOrb {
	
	public IndustriaRune() {
		super( "Industria",
				false,
				0);
		
	}
	
	@Override
	public void onStartOfTurn() {
		this.activateEffect();
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.passiveAmount));
	}
	
	@Override
	public void onBreak() {
		onStartOfTurn();
		onStartOfTurn();
	}

	@Override
	public AbstractOrb makeCopy() { return new IndustriaRune(); }

}
