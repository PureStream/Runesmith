package runesmith.actions.relics;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.actions.EnhanceEntireHandAction;

import java.util.ArrayList;

public class NanobotsAction extends AbstractGameAction {

    private boolean isCheck = false;

    public NanobotsAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            GameActionManager actionManager = AbstractDungeon.actionManager;

            actionManager.addToBottom(new EnhanceEntireHandAction());

            ArrayList<AbstractGameAction> actions = actionManager.actions;
            ArrayList<AbstractGameAction> drawActions = new ArrayList<>();
            ArrayList<AbstractGameAction> otherActions = new ArrayList<>();
            if (!isCheck) {
                for (AbstractGameAction action : actions) {
                    if (action instanceof DrawCardAction)
                        drawActions.add(action);
                    else
                        otherActions.add(action);
                }
                if (drawActions.size() == 0) {
                    isCheck = true;
                    this.isDone = true;
                    return;
                }
                actionManager.clear();
                for (AbstractGameAction action : drawActions)
                    actionManager.addToBottom(action);
                for (AbstractGameAction action : otherActions)
                    actionManager.addToBottom(action);
                isCheck = true;
            }
            this.isDone = true;
        }
    }

}
