package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardTopCardAction extends AbstractGameAction{
	
	public DiscardTopCardAction() {
//		if (Settings.FAST_MODE) {
//		this.duration = Settings.ACTION_DUR_XFAST;
//		} else {
//			this.duration = Settings.ACTION_DUR_FASTER;
//		}
		this.duration = 0F;
		this.actionType = AbstractGameAction.ActionType.DISCARD;
	}

	@Override
	public void update() {
		AbstractPlayer p = AbstractDungeon.player;
		if(p.hand.getTopCard()==null) {
			this.isDone=true;
			return;
		}
		p.hand.getTopCard().triggerOnManualDiscard();
		p.hand.moveToDiscardPile(p.hand.getTopCard());
		this.isDone = true;
	}
}
