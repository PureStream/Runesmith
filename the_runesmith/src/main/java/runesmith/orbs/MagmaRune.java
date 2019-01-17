package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MagmaRune extends RuneOrb {
	
	public static final int basePotency = 4;
	
	public MagmaRune(int potential) {
		super( "Protectio",
				false,
				potential);
		
	}
	
	@Override
	public void onStartOfTurn() {
		this.activateEffect();
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToBottom(
				  new GainBlockAction(p, p, this.potential)
				);
	}
	
	@Override
	public void onBreak() {
		onStartOfTurn();
		onStartOfTurn();
	}

	@Override
	public AbstractOrb makeCopy() { return new MagmaRune(this.potential); }

}