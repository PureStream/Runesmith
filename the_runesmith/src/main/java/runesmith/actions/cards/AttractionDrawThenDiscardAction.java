package runesmith.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.actions.DiscardTopCardAction;

import static runesmith.patches.CardTagEnum.CRAFT;

public class AttractionDrawThenDiscardAction extends AbstractGameAction {
    private boolean shuffled;

    public AttractionDrawThenDiscardAction(boolean shuffled) {
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
        this.shuffled = shuffled;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.drawPile.size() + p.discardPile.size() == 0) {
            this.isDone = true;
            return;
        }
        if (p.drawPile.size() == 0 && !this.shuffled) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
            AbstractDungeon.actionManager.addToBottom(new AttractionDrawThenDiscardAction(true));
            this.isDone = true;
            return;
        } else if (p.drawPile.size() == 0) {
            this.isDone = true;
            return;
        }
        if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
            p.createHandIsFullDialog();
            this.isDone = true;
            return;
        }
        AbstractCard c = p.drawPile.getTopCard();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));

        if (c.hasTag(CRAFT)) {
            this.isDone = true;
        } else {
            AbstractDungeon.actionManager.addToBottom(new DiscardTopCardAction());
            tickDuration();
            AbstractDungeon.actionManager.addToBottom(new AttractionDrawThenDiscardAction(true));
            this.isDone = true;
        }
    }
}
