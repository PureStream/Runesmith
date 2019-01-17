package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MagmaRune extends RuneOrb {
	
	public static final int basePotency = 4;
	
	public MagmaRune(int potential) {
		super( "Magma",
				false,
				potential);
		
	}
	
	@Override
	public void onStartOfTurn() {
		this.activateEffect();
		AbstractDungeon.actionManager.addToBottom(
		new DamageRandomEnemyAction(
					new DamageInfo(AbstractDungeon.player, 
							this.potential, 
							DamageInfo.DamageType.THORNS),
					AbstractGameAction.AttackEffect.FIRE
				)
		);
	}
	
	@Override
	public void onEndOfTurn() {
		this.activateEffect();
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToBottom(
				  new GainBlockAction(p, p, this.potential/2)
				);
	}
	
	@Override
	public void onBreak() {
		onStartOfTurn();
		onEndOfTurn();
		onStartOfTurn();
		onEndOfTurn();
	}

	@Override
	public AbstractOrb makeCopy() { return new MagmaRune(this.potential); }
	
	@Override
	protected void renderText(SpriteBatch sb)
	{
		if (!this.showEvokeValue && this.showPotentialValue) {
			//Block
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
					Integer.toString(this.evokeAmount), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET,
					new Color(0.2F, 0.4F, 0.9F, this.c.a), this.fontScale);
			//Damage
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, 
					Integer.toString(this.potential), this.cX + NUM_X_OFFSET, this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET + NUM_Y_OFFSET,
					Color.WHITE.cpy(), this.fontScale);
		}
	}

}