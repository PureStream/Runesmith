package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import java.util.ArrayList;

public class HammerSlamAction extends AbstractGameAction{
	private int add;
	
	public HammerSlamAction(AbstractPlayer p, int amount, int addi) {
		setValues(this.target, source, amount);
		this.add = addi;
		this.actionType = AbstractGameAction.ActionType.WAIT;
	}
	
	@Override
	public void update() {
		ArrayList<String> runeList = new ArrayList<>();
		for(AbstractOrb o : AbstractDungeon.player.orbs) {
			if ((o.ID != null) && (!o.ID.equals("Empty")) && (!runeList.contains(o.ID))) {
				runeList.add(o.ID);
			}
		}
		int toDraw = runeList.size() * this.amount + this.add;
		if(toDraw > 0) {
			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.DrawCardAction(this.source, toDraw));
		}
		this.isDone = true;
	}
}
