package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.DudRune;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.PlayerRuneField;

import java.util.List;

public class GenerateForcefieldAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("Runesmith:FortifyAction");
    public static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;
    private int breakNums;
    private boolean freeToPlayOnce;
    private boolean upgraded;

    public GenerateForcefieldAction(int breakNums, boolean freeToPlayOnce, boolean upgraded) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.freeToPlayOnce = freeToPlayOnce;
        this.breakNums = breakNums;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (upgraded)
                breakNums++;
            if (p.hasRelic(ChemicalX.ID)) {
                breakNums += 2;
                p.getRelic(ChemicalX.ID).flash();
            }
            if (!this.freeToPlayOnce)
                p.energy.use(EnergyPanel.totalCount);

            PlayerRune playerRune = PlayerRuneField.playerRune.get(p);
            if (!playerRune.hasRune()) {
                this.isDone = true;
                return;
            }

            int count = 0;
            for (RuneOrb rune : playerRune.runes) {
                if(!(rune instanceof DudRune)) {
                    AbstractDungeon.actionManager.addToBottom(new BreakRuneAction(rune));
                    if (++count == breakNums)
                        break;
                }
            }

            if (count > 0)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, count), count));
            this.isDone = true;
        }
        tickDuration();
    }
}