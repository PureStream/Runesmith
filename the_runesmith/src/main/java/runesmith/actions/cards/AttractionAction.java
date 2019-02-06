package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static runesmith.patches.CardTagEnum.CRAFT;

public class AttractionAction extends AbstractGameAction {
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
            this.duration = Settings.ACTION_DUR_XFAST / 2;
        } else {
            this.duration = Settings.ACTION_DUR_FASTER / 2;
        }
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.drawPile.size() + p.discardPile.size() == 0) {
            this.isDone = true;
            return;
        }
        if (p.drawPile.size() == 0) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
            AbstractDungeon.actionManager.addToBottom(new AttractionAction(p));
            this.isDone = true;
            return;
        }

//		ArrayList<AbstractCard> toDiscard = new ArrayList<AbstractCard>();
        for (int i = p.drawPile.size() - 1; i >= 0; i--) {
            if (p.drawPile.group.get(i).hasTag(CRAFT)) {
                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
                break;
            } else {
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(p.drawPile.group.get(i), p.drawPile));
            }
//				toDiscard.get(i).triggerOnManualDiscard();
//				p.drawPile.moveToDiscardPile(toDiscard.get(i));
        }
        this.isDone = true;
    }


}