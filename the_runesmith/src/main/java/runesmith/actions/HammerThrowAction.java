package runesmith.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;

public class HammerThrowAction extends AbstractGameAction {
	
	private AbstractPlayer p;
	private ArrayList<AbstractCard> hands;
	private boolean isHandFull;
	private boolean isUpgraded = false;
	private int toDraw;
	
	public HammerThrowAction(AbstractPlayer p, int toDraw) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = p;
		this.duration = Settings.ACTION_DUR_FAST;
		this.toDraw = toDraw;
	}

	@Override
	public void update() {
		int beforeDrawHandAMT = this.p.hand.size();
		int maxHand = BaseMod.MAX_HAND_SIZE;
		if (toDraw == 2) isUpgraded = true;
		if (beforeDrawHandAMT + toDraw > maxHand) isHandFull = true;
		AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.source, toDraw));
		hands = this.p.hand.group;
		
		if (isHandFull) {
			if (hands.get(hands.size()-1).canUpgrade()) hands.get(hands.size()-1).upgrade();
		}
		else {
			if (isUpgraded) {
				if (hands.get(hands.size()-1).canUpgrade()) hands.get(hands.size()-1).upgrade();
				if (hands.get(hands.size()-2).canUpgrade()) hands.get(hands.size()-2).upgrade();
			}
			if (hands.get(hands.size()-1).canUpgrade()) hands.get(hands.size()-1).upgrade();
		}
		
		//p.hand.getNCardFromTop(0);
		
		this.isDone = true;
	}

}
