package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.PlayerRuneField;

public class RuneChannelAction extends AbstractGameAction {

    private AbstractOrb orbType;
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
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        PlayerRune playerRune = PlayerRuneField.playerRune.get(p);
        maxRunes = playerRune.getMaxRunes();
        this.runeCount = playerRune.runeCount();
        if (!playerRune.hasRuneSpace()) {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, FULL_RUNE_TEXT, true));
            this.isDone = true;
            return;
        }
        if(this.orbType instanceof RuneOrb) {
            playerRune.craftRune((RuneOrb) this.orbType);
            if (p.hasPower("Runesmith:DuplicatePower")) {
                craftDuplicate(this.orbType);
            }
        }
        tickDuration();
        this.isDone = true;
    }

    private void craftDuplicate(AbstractOrb newOrbType) {
        AbstractPlayer p = AbstractDungeon.player;
        PlayerRune playerRune = PlayerRuneField.playerRune.get(p);
        if (playerRune.hasRuneSpace()) {
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, "Runesmith:DuplicatePower", 1));
            if (this.orbType instanceof RuneOrb) {
                playerRune.craftRune((RuneOrb) this.orbType.makeCopy());
            }
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, FULL_RUNE_TEXT, true));
            this.isDone = true;
        }
    }

}
