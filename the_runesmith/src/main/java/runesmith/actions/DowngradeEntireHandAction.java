package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class DowngradeEntireHandAction extends AbstractGameAction {

    private AbstractPlayer p;

    public DowngradeEntireHandAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        this.p.hand.group.stream()
                .filter(DowngradeCard::canDowngrade)
                .forEach(c -> {
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                    DowngradeCard.downgrade(this.p.hand.group, c);
                });
        this.isDone = true;
    }
}