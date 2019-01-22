package runesmith.actions;

import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import basemod.BaseMod;

import java.util.ArrayList;

import org.apache.logging.log4j.Logger;

public class UpgradeDrawAction extends AbstractGameAction {
	private boolean shuffleCheck = false;
	private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(UpgradeDrawAction.class.getName());
  
	public UpgradeDrawAction(AbstractCreature source, int amount, boolean endTurnDraw) {
		if (endTurnDraw) {
			AbstractDungeon.topLevelEffects.add(new PlayerTurnEffect());
		} else if (AbstractDungeon.player.hasPower("No Draw")) {
			AbstractDungeon.player.getPower("No Draw").flash();
			setValues(AbstractDungeon.player, source, amount);
			this.isDone = true;
			this.duration = 0.0F;
			this.actionType = AbstractGameAction.ActionType.WAIT;
			return;
    }
    
		setValues(AbstractDungeon.player, source, amount);
		this.actionType = AbstractGameAction.ActionType.DRAW;
		if (Settings.FAST_MODE) {
			this.duration = Settings.ACTION_DUR_XFAST;
		} else {
			this.duration = Settings.ACTION_DUR_FASTER;
		}
	}
  
	public UpgradeDrawAction(AbstractCreature source, int amount) {
		this(source, amount, false);
	}
  
	public void update() {
		if (this.amount <= 0) {
			this.isDone = true;
			return;
		}

		int deckSize = AbstractDungeon.player.drawPile.size();
		int discardSize = AbstractDungeon.player.discardPile.size();


		if (SoulGroup.isActive()) {
			return;
		}


		if (deckSize + discardSize == 0) {
			this.isDone = true;
			return;
		}

		if (AbstractDungeon.player.hand.size() == BaseMod.MAX_HAND_SIZE) {
			AbstractDungeon.player.createHandIsFullDialog();
			this.isDone = true;
			return;
		}




		if (!this.shuffleCheck) {
			if (this.amount + AbstractDungeon.player.hand.size() > 10) {
				int handSizeAndDraw = 10 - (this.amount + AbstractDungeon.player.hand.size());
				this.amount += handSizeAndDraw;
				AbstractDungeon.player.createHandIsFullDialog();
			}
			if (this.amount > deckSize) {
				int tmp = this.amount - deckSize;
				AbstractDungeon.actionManager.addToTop(new UpgradeDrawAction(AbstractDungeon.player, tmp));
				AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
				if (deckSize != 0) {
					AbstractDungeon.actionManager.addToTop(new UpgradeDrawAction(AbstractDungeon.player, deckSize));
				}
				this.amount = 0;
				this.isDone = true;
			}
			this.shuffleCheck = true;
		}

		this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

		if ((this.amount != 0) && (this.duration < 0.0F)) {
			if (Settings.FAST_MODE) {
			this.duration = Settings.ACTION_DUR_XFAST;
			} else {
				this.duration = Settings.ACTION_DUR_FASTER;
			}
			this.amount -= 1;
		  
			if (!AbstractDungeon.player.drawPile.isEmpty()) {
				AbstractDungeon.player.draw();
				AbstractDungeon.player.hand.refreshHandLayout();
				ArrayList<AbstractCard> hands = AbstractDungeon.player.hand.group ;
				if (hands.get(hands.size()-1).canUpgrade()) hands.get(hands.size()-1).upgrade();
			} else {
				logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + AbstractDungeon.player.masterDeck
					.getCardNames());
				this.isDone = true;
			}
		  
			if (this.amount == 0) {
				this.isDone = true;
			}
		}
	}
}
