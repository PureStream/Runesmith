package runesmith.orbs;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import runesmith.actions.runes.ObretioRuneAction;

public class ObretioRune extends RuneOrb {

    public static final int basePotency = 4;
    private static final String ARTIFACT_ID = ArtifactPower.POWER_ID;

    public ObretioRune(int potential) {
        super("Obretio",
                false,
                potential);
    }

    @Override
    public void onEndOfTurn() {
        this.activateEndOfTurnEffect();
        AbstractDungeon.actionManager.addToBottom(new ObretioRuneAction(potential));
    }


    private AbstractMonster getHighestHealthMonsterWithAttackIntent(AbstractMonster m1, AbstractMonster m2) {
        int m1Health = 0;
        if (m1 != null && (m1.intent == AbstractMonster.Intent.ATTACK || m1.intent == AbstractMonster.Intent.ATTACK_BUFF || m1.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m1.intent == AbstractMonster.Intent.ATTACK_DEFEND))
            m1Health = m1.currentHealth;
        int m2Health = 0;
        if (m2 != null && (m2.intent == AbstractMonster.Intent.ATTACK || m2.intent == AbstractMonster.Intent.ATTACK_BUFF || m2.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m2.intent == AbstractMonster.Intent.ATTACK_DEFEND))
            m2Health = m2.currentHealth;
        return (m1Health >= m2Health) ? m1 : m2;
    }

    @Override
    public void onBreak() {
        AbstractDungeon.actionManager.addToBottom(new ObretioRuneAction(potential));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new ObretioRune(this.potential);
    }

}