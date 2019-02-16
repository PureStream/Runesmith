package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.actions.EnhanceCard;

import java.util.Arrays;
import java.util.List;

public class MetallurgicalResearchAction extends AbstractGameAction {

    public MetallurgicalResearchAction() {
        this.duration = Settings.ACTION_DUR_MED;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractPlayer p = AbstractDungeon.player;
            List<CardGroup> allCardsGroup = Arrays.asList(p.hand, p.drawPile, p.discardPile);
            allCardsGroup.forEach(this::upgradeAndEnhanceAllCardsInGroup);
//            upgradeAndEnhanceAllCardsInGroup(p.hand);
//            upgradeAndEnhanceAllCardsInGroup(p.drawPile);
//            upgradeAndEnhanceAllCardsInGroup(p.discardPile);
            this.isDone = true;
        }
    }

    private void upgradeAndEnhanceAllCardsInGroup(CardGroup cardGroup) {
        for (AbstractCard c : cardGroup.group) {
            if (c.canUpgrade()) {
                if (cardGroup.type == CardGroup.CardGroupType.HAND) {
                    c.superFlash();
                }
                c.upgrade();
                c.applyPowers();
            }
            if (EnhanceCard.canEnhance(c)) {
                EnhanceCard.enhance(c);
                c.applyPowers();
            }

        }
    }

}
