package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReduceEntireDeckCostAndExhaustAction extends AbstractGameAction{

    private AbstractPlayer p;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public ReduceEntireDeckCostAndExhaustAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        List<ArrayList<AbstractCard>> allCardsGroup = Arrays.asList(p.hand.group, p.drawPile.group, p.discardPile.group);
        allCardsGroup.forEach(cardGroup -> cardGroup
                .forEach(card -> {
                    if (card.cost > 0) {
                        card.cost = card.cost-1;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                    }
                    if(!card.exhaust){
                        card.rawDescription = card.rawDescription + " NL " + TEXT[0] + ".";
                        card.exhaust = true;
                        card.initializeDescription();
                    }
                }));
        this.isDone = true;
    }

}
