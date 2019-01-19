package runesmith.orbs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class PrismaticRune extends RuneOrb {
	
	public PrismaticRune(boolean upgraded) {
		super( "Prismatic",
				upgraded,
				0);
	}
	
	@Override
	public void onStartOfTurn() {
		this.activateEffect();
		AbstractCard tmp = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
		if (upgraded) tmp.upgrade();
		AbstractDungeon.actionManager.addToBottom(
				new MakeTempCardInHandAction(tmp, false));
	}
	
	@Override
	public void onBreak() {
		onStartOfTurn();
		onStartOfTurn();
	}

	@Override
	public AbstractOrb makeCopy() { return new PrismaticRune(upgraded); }
	
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
