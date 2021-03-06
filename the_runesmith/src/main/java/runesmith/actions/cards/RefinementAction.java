package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runesmith.RunesmithMod;
import runesmith.actions.EnhanceCard;

import java.util.ArrayList;

public class RefinementAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:FortifyAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private ArrayList<AbstractCard> cannotEnhance = new ArrayList<>();
    private int enhanceNums;
    private boolean freeToPlayOnce;
    private boolean upgraded;

    public RefinementAction(int enhanceNums, boolean freeToPlayOnce, boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.freeToPlayOnce = freeToPlayOnce;
        this.enhanceNums = enhanceNums;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (upgraded)
                enhanceNums++;
            if (p.hasRelic(ChemicalX.ID)){
                enhanceNums += 2;
                p.getRelic(ChemicalX.ID).flash();
            }
            if (!this.freeToPlayOnce) {
                if (EnergyPanel.totalCount > 0) {
                    p.energy.use(EnergyPanel.totalCount);
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
                }
            }

            for (AbstractCard c : this.p.hand.group) {
                if (!EnhanceCard.canEnhance(c)) {
                    this.cannotEnhance.add(c);
                }
            }

            if (this.cannotEnhance.size() == this.p.hand.group.size()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.group.size() - this.cannotEnhance.size() == 1) {
                for (AbstractCard c : this.p.hand.group) {
                    if (EnhanceCard.canEnhance(c)) {
                        EnhanceCard.enhance(c, enhanceNums);
                        c.superFlash(RunesmithMod.BEIGE.cpy());
                        this.isDone = true;
                        return;
                    }
                }
            }

            this.p.hand.group.removeAll(this.cannotEnhance);

            if (this.p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            }
            if (this.p.hand.group.size() == 1) {
                EnhanceCard.enhance(this.p.hand.getTopCard(), enhanceNums);
                this.p.hand.getTopCard().superFlash(RunesmithMod.BEIGE.cpy());
                returnCards();
                this.isDone = true;
            }

        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard c = AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0);
            EnhanceCard.enhance(c, enhanceNums);
            c.superFlash(RunesmithMod.BEIGE.cpy());
            this.p.hand.addToTop(c);

            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            this.isDone = true;
        }

        tickDuration();
    }
	
/*	private boolean canEnhance(AbstractCard c) {
		if(c.type == CardType.CURSE || c.type == CardType.STATUS) return false;
		else return true;
	}*/

    private void returnCards() {
        for (AbstractCard c : this.cannotEnhance) {
            this.p.hand.addToTop(c);
        }
        this.p.hand.refreshHandLayout();
    }
}
