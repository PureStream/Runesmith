package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.cards.FortifyAction;

public class AutoHammer extends CustomRelic {

    public static final String ID = "Runesmith:AutoHammer";
    private static final String IMG = "runesmith/images/relics/AutoHammer.png"; //<--------- Need some img
    private static final String IMG_O = "runesmith/images/relics/AutoHammer_o.png";
    private static final int NUM_CARDS = 3;

    public AutoHammer() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.COMMON, LandingSound.SOLID);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atTurnStart() {
        this.counter = 0;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.SKILL) {
            this.counter += 1;
            if (this.counter % NUM_CARDS == 0) {
                this.counter = 0;
                flash();
                AbstractDungeon.actionManager.addToTop(new FortifyAction(false));
                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
        }
    }

    public void onVictory() {
        this.counter = -1;
    }

    public AbstractRelic makeCopy() {
        return new AutoHammer();
    }

}
