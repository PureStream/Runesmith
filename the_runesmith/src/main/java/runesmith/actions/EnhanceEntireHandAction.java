package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;

public class EnhanceEntireHandAction extends AbstractGameAction {

    private Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    public EnhanceEntireHandAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = ActionType.WAIT;
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
        for (AbstractCard c : cardGroup.group) {
            if (EnhanceCard.canEnhance(c)) {
                c.superFlash();
                EnhanceCard.enhance(c);
                c.applyPowers();
            }
        }
    }

}
