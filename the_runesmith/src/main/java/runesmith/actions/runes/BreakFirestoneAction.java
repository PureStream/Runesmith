package runesmith.actions.runes;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BreakFirestoneAction extends AbstractGameAction {

    private DamageInfo info;

    public BreakFirestoneAction(DamageInfo info) {
        this.info = info;
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = AttackEffect.FIRE;
    }

    @Override
    public void update() {
        AbstractCreature m = AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToTop(new DamageAction(m, this.info, this.attackEffect));
//        AbstractDungeon.actionManager.addToTop(new DamageAction(m, this.info, this.attackEffect, true));
        this.isDone = true;
    }
}
