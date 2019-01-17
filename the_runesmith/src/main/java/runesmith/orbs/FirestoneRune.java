package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class FirestoneRune extends RuneOrb {
	
	public static final int basePotency = 5;

	public FirestoneRune(int potential) {
		super( "Firestone",
				false,
				potential);
		
	}
	
	@Override
	public void onStartOfTurn() {
		this.activateEffect();
		//get random target
		//AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(true);
		//damage enemy
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
	public void onBreak() {
		onStartOfTurn();
		onStartOfTurn();
	}

	@Override
	public AbstractOrb makeCopy() { return new FirestoneRune(this.potential); }

}
