package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.ApplyElementsPowerAction;

import static runesmith.patches.CardTagEnum.RS_CRAFT;

public class EmergencyProvisions extends AbstractRunesmithRelic {

    public static final String ID = "Runesmith:EmergencyProvisions";
    private static final String IMG = "runesmith/images/relics/EmergencyProvisions.png"; //<--------- Need some img
//    private static final String IMG_O = "runesmith/images/relics/EmergencyProvisions_o.png";
    private static final int ELEMENT_AMT = 1;

    public EmergencyProvisions() {
        super(ID, IMG, RelicTier.COMMON, LandingSound.FLAT, true);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(RS_CRAFT)) {
            AbstractPlayer p = AbstractDungeon.player;
            flash();
            int rng = AbstractDungeon.cardRandomRng.random(2);
            if (rng == 0)
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, ELEMENT_AMT, 0, 0));
            else if (rng == 1)
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, 0, ELEMENT_AMT, 0));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, 0, 0, ELEMENT_AMT));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));
        }
    }

    public AbstractRelic makeCopy() {
        return new EmergencyProvisions();
    }
}
