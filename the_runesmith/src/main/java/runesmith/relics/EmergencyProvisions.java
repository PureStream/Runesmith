package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.ApplyElementsAction;

import static runesmith.patches.CardTagEnum.CRAFT;

public class EmergencyProvisions extends CustomRelic {

    public static final String ID = "Runesmith:EmergencyProvisions";
    private static final String IMG = "images/relics/EmergencyProvisions.png"; //<--------- Need some img
    private static final String IMG_O = "images/relics/EmergencyProvisions_o.png";
    private static final int ELEMENT_AMT = 1;

    public EmergencyProvisions() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.COMMON, LandingSound.FLAT);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.hasTag(CRAFT)) {
            AbstractPlayer p = AbstractDungeon.player;
            flash();
            int rng = AbstractDungeon.cardRandomRng.random(2);
            if (rng == 0)
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, ELEMENT_AMT, 0, 0));
            else if (rng == 1)
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, 0, ELEMENT_AMT, 0));
            else
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, 0, 0, ELEMENT_AMT));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));
        }
    }

    public AbstractRelic makeCopy() {
        return new EmergencyProvisions();
    }

}
