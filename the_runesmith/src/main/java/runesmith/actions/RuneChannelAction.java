package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import runesmith.orbs.RuneOrb;

public class RuneChannelAction extends AbstractGameAction {

    private AbstractOrb orbType;
    private boolean autoEvoke;
    private int maxRunes;
    private int runeCount = 0;
    private static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Rune Tip");
    private static final String FULL_RUNE_TEXT = tutorialStrings.TEXT[0];

    public RuneChannelAction(AbstractOrb newOrbType) {
        this(newOrbType, true);
    }

    public RuneChannelAction(AbstractOrb newOrbType, boolean autoEvoke) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        maxRunes = RuneOrb.getMaxRune(p);
        this.runeCount = RuneOrb.getRuneCount();
        if (this.runeCount >= maxRunes) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, FULL_RUNE_TEXT, true));
            this.isDone = true;
            return;
        } else {
            p.increaseMaxOrbSlots(1, false);
            CardCrawlGame.sound.playA("GUARDIAN_ROLL_UP", 1.0F);
        }
        if (this.autoEvoke) {
            if (p.hasPower("Runesmith:DuplicatePower")) {
                channelDuplicate(this.orbType, this.autoEvoke);
            }
            p.channelOrb(this.orbType);
            if (this.orbType instanceof RuneOrb) {
                ((RuneOrb) this.orbType).onCraft();
            }
        } else {
            for (AbstractOrb o : p.orbs) {
                if ((o instanceof EmptyOrbSlot)) {
                    if (p.hasPower("Runesmith:DuplicatePower")) {
                        channelDuplicate(this.orbType, this.autoEvoke);
                    }
                    p.channelOrb(this.orbType);
                    if (this.orbType instanceof RuneOrb) {
                        ((RuneOrb) this.orbType).onCraft();
                    }
                    break;
                }
            }
        }
        tickDuration();
        this.isDone = true;
    }

    private void channelDuplicate(AbstractOrb newOrbType, boolean autoEvoke) {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.runeCount < maxRunes - 1) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, "Runesmith:DuplicatePower", 1));
            p.increaseMaxOrbSlots(1, false);
            p.channelOrb(this.orbType.makeCopy());
            if (this.orbType instanceof RuneOrb) {
                ((RuneOrb) this.orbType).onCraft();
            }
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, FULL_RUNE_TEXT, true));
            this.isDone = true;
        }
    }

}
