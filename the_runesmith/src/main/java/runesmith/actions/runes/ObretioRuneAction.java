package runesmith.actions.runes;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ObretioRuneAction extends AbstractGameAction {

    private int potential;
    private AbstractPlayer p;
    private static final String ARTIFACT_ID = ArtifactPower.POWER_ID;

    public ObretioRuneAction(int potential) {
        this.potential = potential;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        AbstractMonster m = AbstractDungeon.getMonsters().monsters
                .stream()
                .reduce(null, this::getHighestAttackMonster);
        if (m != null) {
            if (!m.hasPower(ARTIFACT_ID))
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.potential), this.potential, true));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new StrengthPower(m, -this.potential), -this.potential, true));
        } else {
            m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRng);
            if (m != null) {
                if (!m.hasPower(ARTIFACT_ID))
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.potential), this.potential, true));
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new StrengthPower(m, -this.potential), -this.potential, true));
            }
        }
        this.isDone = true;
    }

    private AbstractMonster getHighestAttackMonster(AbstractMonster m1, AbstractMonster m2){
        int m1Attack = 0;
        if (m1 != null && !m1.isDeadOrEscaped()) {
            if (m1.intent == AbstractMonster.Intent.ATTACK || m1.intent == AbstractMonster.Intent.ATTACK_BUFF || m1.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m1.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                boolean m1IsMulti = (boolean) ReflectionHacks.getPrivate(m1, AbstractMonster.class, "isMultiDmg");
                if (m1IsMulti)
                    m1Attack = (int) ReflectionHacks.getPrivate(m1, AbstractMonster.class, "intentDmg") * (int) ReflectionHacks.getPrivate(m1, AbstractMonster.class, "intentMultiAmt");
                else
                    m1Attack = (int) ReflectionHacks.getPrivate(m1, AbstractMonster.class, "intentDmg");
            }
        }
        int m2Attack = 0;
        if (m2 != null && !m2.isDeadOrEscaped()) {
            if (m2.intent == AbstractMonster.Intent.ATTACK || m2.intent == AbstractMonster.Intent.ATTACK_BUFF || m2.intent == AbstractMonster.Intent.ATTACK_DEBUFF || m2.intent == AbstractMonster.Intent.ATTACK_DEFEND) {
                boolean m2IsMulti = (boolean) ReflectionHacks.getPrivate(m2, AbstractMonster.class, "isMultiDmg");
                if (m2IsMulti)
                    m2Attack = (int) ReflectionHacks.getPrivate(m2, AbstractMonster.class, "intentDmg") * (int) ReflectionHacks.getPrivate(m2, AbstractMonster.class, "intentMultiAmt");
                else
                    m2Attack = (int) ReflectionHacks.getPrivate(m2, AbstractMonster.class, "intentDmg");
            }
        }
        return (m1Attack >= m2Attack) ? m1 : m2;
    }
}