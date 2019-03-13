package runesmith.actions.runes;

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
    private AbstractMonster m;
    private AbstractPlayer p;
    private static final String ARTIFACT_ID = ArtifactPower.POWER_ID;

    public ObretioRuneAction(int potential, AbstractMonster m) {
        this.potential = potential;
        this.m = m;
        this.p = AbstractDungeon.player;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        if (!m.hasPower(ARTIFACT_ID))
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new GainStrengthPower(m, this.potential), this.potential, true));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, p, new StrengthPower(m, -this.potential), -this.potential, true));

        this.isDone = true;
    }
}