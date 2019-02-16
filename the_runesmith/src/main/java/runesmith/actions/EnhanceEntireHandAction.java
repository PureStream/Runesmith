package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;

public class EnhanceEntireHandAction extends AbstractGameAction {

    private Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    private int enhanceNums;

    public EnhanceEntireHandAction() {
        this(1);
    }

    public EnhanceEntireHandAction(int enhanceNums) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
        this.enhanceNums = enhanceNums;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            AbstractPlayer p = AbstractDungeon.player;

            logger.info("Enhance entire hand.");

            EnhanceAllCardsInGroup(p.hand);
            this.isDone = true;
        }
    }

    private void EnhanceAllCardsInGroup(CardGroup cardGroup) {
        cardGroup.group.stream().filter(EnhanceCard::canEnhance)
                .forEach(c -> {
                    c.superFlash();
                    EnhanceCard.enhance(c, enhanceNums);
                });
    }

}
