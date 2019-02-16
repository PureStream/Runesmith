package runesmith.actions.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.actions.EnhanceEntireHandAction;
import runesmith.relics.Nanobots;

public class NanobotsAction extends AbstractGameAction {

    public NanobotsAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;
//            GameActionManager actionManager = AbstractDungeon.actionManager;

//            ArrayList<AbstractGameAction> actions = actionManager.actions;
//            ArrayList<AbstractGameAction> drawActions = new ArrayList<>();
//            ArrayList<AbstractGameAction> otherActions = new ArrayList<>();
//            actions.forEach(action -> {
//                if (action instanceof DrawCardAction)
//                    drawActions.add(action);
//                else
//                    otherActions.add(action);
//            });
//            actionManager.clear();
//            drawActions.forEach(actionManager::addToBottom);
//            otherActions.forEach(actionManager::addToBottom);
            AbstractDungeon.actionManager.addToBottom(new EnhanceEntireHandAction());
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, p.getRelic(Nanobots.ID)));

            this.isDone = true;
        }
    }

}
