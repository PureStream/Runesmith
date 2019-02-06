package runesmith.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class IncendiumRune extends RuneOrb {

    public static final int basePotency = 4;

    public IncendiumRune(int potential) {
        super("Incendium",
                false,
                potential);

    }

    @Override
    public void onEndOfTurn() {
        this.activateEndOfTurnEffect();
        //damage all enemies
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(this.potential, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToTop(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(this.potential, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToTop(
                new DamageAllEnemiesAction(
                        null,
                        DamageInfo.createDamageMatrix(this.potential, true),
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.FIRE));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new IncendiumRune(this.potential);
    }

}
