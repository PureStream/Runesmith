package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

public class FerroRune extends RuneOrb {

    public static final int basePotency = 5;

    private AbstractPlayer p = AbstractDungeon.player;

    public FerroRune(int potency) {
        super("Ferro",
                false,
                potency);

    }

    @Override
    public void onCraft() {
        if (p.hasPower("Plated Armor")) {
            int powerDiff = this.potential - p.getPower("Plated Armor").amount;
            if (powerDiff > 0)
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, powerDiff), powerDiff));
        } else
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, potential), potential));
    }

    @Override
    public void onBreak() {
        if (p.hasPower("Plated Armor")) {
            int powerDiff = this.potential * 2 - p.getPower("Plated Armor").amount;
            if (powerDiff > 0)
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PlatedArmorPower(p, powerDiff), powerDiff));
        } else
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PlatedArmorPower(p, potential * 2), potential * 2));
        this.activateEffect();
    }

    @Override
    public AbstractOrb makeCopy() {
        return new FerroRune(this.potential);
    }

}
