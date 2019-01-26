package runesmith.orbs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.ThornsPower;

import runesmith.RunesmithMod;
import runesmith.powers.SpiculumDownPower;

public class SpiculumRune extends RuneOrb {
	public static int basePotency = 3;
	
	public static final Logger logger = LogManager.getLogger(SpiculumRune.class.getName());
	
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
		//logger.info("Applying Spiculum Down");
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiculumDownPower(p, this.potential*2),this.potential*2));
	}

	@Override
	public void onMultiBreak() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p, this.potential*2), this.potential*2));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SpiculumDownPower(p, this.potential*2),this.potential*2));
	}
	
	@Override
	public void onRemove() {
		AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p,p, "Thorns",this.potential));
	}
	
	@Override
	public AbstractOrb makeCopy() { return new SpiculumRune(this.potential); }

}