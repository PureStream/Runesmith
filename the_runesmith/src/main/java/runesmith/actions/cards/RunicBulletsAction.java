package runesmith.actions.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class RunicBulletsAction extends AbstractGameAction {
    private DamageInfo info = null;
    private AbstractCreature target;

    public RunicBulletsAction(AbstractCreature m, DamageInfo info) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.info = info;
        this.target = m;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            for (AbstractOrb r : AbstractDungeon.player.orbs) {
                if (!(r instanceof EmptyOrbSlot)) {
                    AbstractDungeon.actionManager.addToTop(new DamageAction(this.target, this.info, AttackEffect.BLUNT_LIGHT, true));
                }
            }
        }
        tickDuration();
    }
}
