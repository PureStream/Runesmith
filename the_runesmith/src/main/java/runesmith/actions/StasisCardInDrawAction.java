package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class StasisCardInDrawAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:StasisCardInDeckAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public StasisCardInDrawAction(int amount) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    @Override
    public void update() {
        CardGroup tmp;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            tmp = new CardGroup(CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group) {
                if (StasisCard.canStasis(c))
                    tmp.addToRandomSpot(c);
            }

            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }

            if (tmp.size() == 1) {
                StasisCard.stasis(tmp.getTopCard());
                this.isDone = true;
                return;
            }

            if (tmp.size() <= this.amount) {
                for (int i = 0; i < tmp.size(); i++)
                    StasisCard.stasis(tmp.getNCardFromTop(i));
                this.isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                StasisCard.stasis(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }

        tickDuration();
    }

}
