package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.actions.DiscardToDrawAction;
import runesmith.patches.EnhanceCountField;

public class RearmAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private AbstractPlayer p;

    public RearmAction() {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    public void update() {
        if (this.p.discardPile.size() > 0) {
            AbstractDungeon.actionManager.addToTop(new ShuffleAction(p.drawPile, false));
            p.discardPile.group.stream()
                    .filter(c -> (c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0))
                    .forEach(x -> AbstractDungeon.actionManager.addToTop(new DiscardToDrawAction(x)));
//            for (AbstractCard card : this.p.discardPile.group) {
//                if (card.upgraded || EnhanceCountField.enhanceCount.get(card) > 0) {
//                    isFound = true;
//                    AbstractDungeon.actionManager.addToBottom(new DiscardToDrawAction(card));
//                }
//            }
        }
        tickDuration();
        this.isDone = true;
    }
}