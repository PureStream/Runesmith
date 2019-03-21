package runesmith.actions.cards;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.CorruptionPower;

import java.util.Arrays;
import java.util.List;

import static com.megacrit.cardcrawl.cards.CardGroup.*;
import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class AttractionAction extends AbstractGameAction {

    AbstractPlayer p;
    private CardGroup drawPile, discardPile, exhaustPile;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public AttractionAction(int amount) {
        AbstractPlayer p = this.p = AbstractDungeon.player;
        setValues(p, p, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            drawPile = p.drawPile;
            discardPile = p.discardPile;
            exhaustPile = p.exhaustPile;
            List<List<AbstractCard>> allCardsGroup = Arrays.asList(drawPile.group, discardPile.group, exhaustPile.group);
            allCardsGroup.forEach(cardGroup -> cardGroup
                    .stream()
                    .filter(c -> c.hasTag(RS_CRAFT))
                    .forEach(temp::addToRandomSpot));

            if (temp.size() <= amount) {
                for (int i=0; i<temp.size(); i++) {
                    if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        AbstractDungeon.player.createHandIsFullDialog();
                        this.isDone = true;
                        return;
                    }
                    moveToHand(temp.getNCardFromTop(i));
                }
                this.isDone = true;
                return;
            }

            if (this.amount == 1)
                AbstractDungeon.gridSelectScreen.open(temp, this.amount, TEXT[0], false);
            else
                AbstractDungeon.gridSelectScreen.open(temp, this.amount, TEXT[1], false);
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            AbstractDungeon.gridSelectScreen.selectedCards
                    .forEach(c -> {
                        if (p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                            AbstractDungeon.player.createHandIsFullDialog();
                            this.isDone = true;
                            return;
                        }
                        moveToHand(c);
                    });

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            p.hand.refreshHandLayout();
            this.isDone = true;
        }
        tickDuration();
    }

    private void moveToHand(AbstractCard c) {
        if (drawPile.contains(c))
            moveToHandFromDraw(c);
        else if (discardPile.contains(c))
            moveToHandFromDiscard(c);
        else
            moveToHandFromExhaust(c);
        p.hand.refreshHandLayout();
        p.hand.applyPowers();
    }

    private void moveToHandFromDraw(AbstractCard c) {
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0F);
        c.drawScale = 0.12F;
        c.targetDrawScale = 0.75F;
        c.current_x = DRAW_PILE_X;
        c.current_y = DRAW_PILE_Y;
        drawPile.removeCard(c);
        p.hand.addToTop(c);
    }

    private void moveToHandFromDiscard(AbstractCard c) {
        c.unhover();
        c.lighten(true);
        c.setAngle(0.0F);
        c.drawScale = 0.12F;
        c.targetDrawScale = 0.75F;
        c.current_x = DISCARD_PILE_X;
        c.current_y = DISCARD_PILE_Y;
        discardPile.removeCard(c);
        p.hand.addToTop(c);
    }

    private void moveToHandFromExhaust(AbstractCard c) {
        c.unfadeOut();
        this.p.hand.addToHand(c);
        if (AbstractDungeon.player.hasPower(CorruptionPower.POWER_ID) && c.type == AbstractCard.CardType.SKILL)
            c.setCostForTurn(-9);
        exhaustPile.removeCard(c);
        c.unhover();
        c.fadingOut = false;
    }

//    @Override
//    public void update() {
//        AbstractPlayer p = AbstractDungeon.player;
//        if (p.drawPile.size() + p.discardPile.size() == 0) {
//            this.isDone = true;
//            return;
//        }
//        if (p.drawPile.size() == 0) {
//            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
//            AbstractDungeon.actionManager.addToBottom(new AttractionAction(p));
//            this.isDone = true;
//            return;
//        }
//
////		ArrayList<AbstractCard> toDiscard = new ArrayList<AbstractCard>();
//        for (int i = p.drawPile.size() - 1; i >= 0; i--) {
//            if (p.drawPile.group.get(i).hasTag(RS_CRAFT)) {
//                AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
//                break;
//            } else {
//                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(p.drawPile.group.get(i), p.drawPile));
//            }
////				toDiscard.get(i).triggerOnManualDiscard();
////				p.drawPile.moveToDiscardPile(toDiscard.get(i));
//        }
//        this.isDone = true;
//    }


}