package runesmith.orbs;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class MagmaRune extends RuneOrb {
	
	public static final int basePotency = 4;
	
	public MagmaRune(int potential) {
		super( "Protectio",
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

}