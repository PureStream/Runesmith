package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PlayerTurnEffect;

import basemod.BaseMod;
import static runesmith.patches.CardTagEnum.CRAFT;

public class AttractionAction extends AbstractGameAction{
	public AttractionAction(AbstractPlayer p) {
		if (AbstractDungeon.player.hasPower("No Draw")) {
			AbstractDungeon.player.getPower("No Draw").flash();
			this.isDone = true;
			this.duration = 0.0F;
			this.actionType = AbstractGameAction.ActionType.WAIT;
			return;
		}
		this.actionType = AbstractGameAction.ActionType.DRAW;
		if (Settings.FAST_MODE) {
		this.duration = Settings.ACTION_DUR_XFAST;
		} else {
			this.duration = Settings.ACTION_DUR_FASTER;
		}
	}

	@Override
	public void update() {
		AbstractPlayer p = AbstractDungeon.player;
		if (p.drawPile.size() + p.discardPile.size() == 0) {
			this.isDone = true;
			return;
		}
		if(p.drawPile.size()==0) {
			AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
		}
		while(p.drawPile.size()>0) {
			if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
				p.createHandIsFullDialog();
				this.isDone = true;
				return;
			}
			p.draw();
			AbstractCard c = p.hand.getTopCard();
			if(c.hasTag(CRAFT)) {
				break;
			}else {
				c.moveToDiscardPile();
				c.triggerOnManualDiscard();
			}
		}
		this.isDone = true;
	}
	
	
}