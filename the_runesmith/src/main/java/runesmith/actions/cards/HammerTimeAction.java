package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.RunesmithMod;
import runesmith.actions.EnhanceCard;

import java.util.Arrays;
import java.util.List;

import static runesmith.patches.CardTagEnum.HAMMER;

public class HammerTimeAction extends AbstractGameAction {

    private AbstractPlayer p;
    private int enhanceNums;
    private static final int ENHANCE_NUMS = 1;
    private static final int UPG_ENHANCE_NUMS = 2;

    public HammerTimeAction(boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        enhanceNums = (upgraded) ? UPG_ENHANCE_NUMS : ENHANCE_NUMS;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            List<CardGroup> allCardsGroup = Arrays.asList(p.hand, p.drawPile, p.discardPile);
            allCardsGroup.forEach(cardGroup -> cardGroup.group.stream()
                    .filter(c -> c.hasTag(HAMMER))
                    .filter(EnhanceCard::canEnhance)
                    .forEach(c -> {
                        EnhanceCard.enhance(c, enhanceNums);
                        if (cardGroup == p.hand)
                            c.superFlash(RunesmithMod.BEIGE);
                    }));
        }
        tickDuration();
    }

}
