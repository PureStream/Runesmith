package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class ProtectioRune extends RuneOrb {
	
	public static final int basePotency = 4;
	
	public ProtectioRune(int potential) {
		super( "Protectio",
				false,
				potential);
		
	}
	
	@Override
	public void onEndOfTurn() {
		this.activateEndOfTurnEffect();
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToBottom(
				  new GainBlockAction(p, p, this.potential)
				);
	}
	
	@Override
	public void onBreak() {
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(
				new GainBlockAction(p, p, this.potential)
		);
		AbstractDungeon.actionManager.addToTop(
				new GainBlockAction(p, p, this.potential)
		);
		this.activateEffect();
	}

	@Override
	public AbstractOrb makeCopy() { return new ProtectioRune(this.potential); }

}