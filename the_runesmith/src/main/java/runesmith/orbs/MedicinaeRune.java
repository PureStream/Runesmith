package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MedicinaeRune extends RuneOrb {
	
	public static final int basePotency = 3;
	
	public MedicinaeRune(int potential) {
		super( "Medicinae",
				false,
				potential);
		
	}
	
	@Override
	public void onBreak() {
		this.activateEffect();
		AbstractPlayer p = AbstractDungeon.player;
		com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.potential));
		com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.potential));
	}

	@Override
	public AbstractOrb makeCopy() { return new MedicinaeRune(this.potential); }

}
