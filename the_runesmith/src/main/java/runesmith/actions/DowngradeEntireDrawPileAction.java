package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

public class DowngradeEntireDrawPileAction extends AbstractGameAction {

    private AbstractPlayer p;

    public DowngradeEntireDrawPileAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        for (AbstractCard c : this.p.drawPile.group) {
            if (DowngradeCard.canDowngrade(c)) {
                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                DowngradeCard.downgrade(this.p.drawPile.group, c);
            }
        }
        this.isDone = true;
    }
}
