package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DowngradeEntireDeckAction extends AbstractGameAction {

    private AbstractPlayer p;

    public DowngradeEntireDeckAction(AbstractPlayer p) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = p;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        List<ArrayList<AbstractCard>> allCardsGroup = Arrays.asList(p.hand.group, p.drawPile.group, p.discardPile.group);
        allCardsGroup.forEach(cardGroup -> cardGroup
                .stream()
                .filter(DowngradeCard::canDowngrade)
                .forEach(c -> {
                    AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                    DowngradeCard.downgrade(cardGroup, c);
                }));
//        this.p.discardPile.group.stream().filter(c -> c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)).forEach(c -> {
//            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
//            DowngradeCard.downgrade(this.p.discardPile.group, c);
//        });
//        this.p.drawPile.group.stream().filter(c -> c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)).forEach(c -> {
//            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
//            DowngradeCard.downgrade(this.p.drawPile.group, c);
//        });
//        this.p.hand.group.stream().filter(c -> c.upgraded || EnhanceCountField.enhanceCount.get(c) > 0 || CardStasisStatus.isStasis.get(c)).forEach(c -> {
//            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
//            DowngradeCard.downgrade(this.p.hand.group, c);
//        });
        this.isDone = true;
    }

}
