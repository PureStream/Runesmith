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
import runesmith.actions.cards.FortifyAction;
import runesmith.utils.TextureLoader;

public class BrokenRuby extends CustomRelic {

    public static final String ID = "Runesmith:BrokenRuby";
    private static final String IMG = "runesmith/images/relics/BrokenRuby.png"; //<--------- Need some img
    private static final String IMG_O = "runesmith/images/relics/BrokenRuby_o.png";
    private static final int IGNIS_AMT = 2;
//    private static final int NUM_CARDS = 2;

    public BrokenRuby() {
        super(ID, TextureLoader.getTexture(IMG), TextureLoader.getTexture(IMG_O), RelicTier.STARTER, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    public void atBattleStart() {
//        setCounter(0);
        AbstractPlayer p = AbstractDungeon.player;
        flash();
        AbstractDungeon.actionManager.addToTop(new ApplyElementsPowerAction(p, p, IGNIS_AMT, 0, 0));
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(p, this));
    }

    public void atTurnStartPostDraw() {
//        if (this.counter >= NUM_CARDS) {
            AbstractDungeon.actionManager.addToBottom(new FortifyAction(false));
            flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//        }
//        setCounter(0);
    }

//    public void onUseCard(AbstractCard card, UseCardAction useCardAction) {
//        if (card.type == AbstractCard.CardType.ATTACK)
//            this.counter += 1;
//    }

//    @Override
//    public void onAfterUseCard(AbstractCard card, UseCardAction useCardAction) {
//        AbstractPlayer p = AbstractDungeon.player;
//        if (card.type == AbstractCard.CardType.ATTACK) {
//            this.counter += 1;
//            if (this.counter % NUM_CARDS == 0) {
//                this.counter = 0;
//                flash();
//                RunesmithMod.logger.info("BrokenRuby : Applying Ignis for using " + NUM_CARDS + " attack cards");
//                AbstractDungeon.actionManager.addToTop(
//                        new ApplyElementsPowerAction(p, p, IGNIS_AMT, 0, 0));
//                AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
//            }
//        }
//    }

//	public void onVictory() {
//		setCounter(-1);
//	}

    public AbstractRelic makeCopy() {
        return new BrokenRuby();
    }

}
