package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.powers.PotentialDownPower;
import runesmith.powers.PotentialPower;

public class PotentiaRune extends RuneOrb {
	
	public static final int basePotency = 2;
	
	private AbstractPlayer p = AbstractDungeon.player;
	public PotentiaRune(int potency) {
		super( "Potentia",
				false,
				potency);
		this.useMultiBreak = true;

	}
	
	@Override
	public void onCraft() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialPower(p, this.potential), this.potential));
	}
	
	@Override
	public void onBreak() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialPower(p, -potential), -potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialPower(p, potential), potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialPower(p, potential), potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialDownPower(p, potential), potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialDownPower(p, potential), potential));
	}

	@Override
	public void onMultiBreak() {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialPower(p, potential), potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialPower(p, potential), potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialDownPower(p, potential), potential));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PotentialDownPower(p, potential), potential));
	}
	
	@Override
	public AbstractOrb makeCopy() { return new PotentiaRune(potential); }

	@Override
	protected void renderText(SpriteBatch sb)
	{
		if (this.upgraded) {
			//render upgrade +
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
					"+", this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET ,
					Color.GREEN.cpy(), this.fontScale*2);
		}
	}
}
