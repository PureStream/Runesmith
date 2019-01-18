package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.ThornsPower;

import runesmith.powers.SpiculumDownPower;

public class SpiculumRune extends RuneOrb {
	public static int basePotency = 3;
	
	private AbstractPlayer p = AbstractDungeon.player;
	public SpiculumRune(int potential) {
		super( "Spiculum",
				false,
				potential);
		this.showPotentialValue = true;
		this.useMultiBreak = true;
	}
	
	@Override
	public void onCraft() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential), this.potential));
	}
	
	@Override
	public void onBreak() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential), this.potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiculumDownPower(p, this.potential*2),this.potential*2));
	}

	@Override
	public void onMultiBreak() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential*2), this.potential*2));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiculumDownPower(p, this.potential*2),this.potential*2));
	}
	
	@Override
	public AbstractOrb makeCopy() { return new SpiculumRune(this.potential); }

}