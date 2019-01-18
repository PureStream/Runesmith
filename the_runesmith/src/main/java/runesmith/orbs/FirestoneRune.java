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
	public void onEndOfTurn() {
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
		this.activateEffect();
	}
	
	@Override
	public void onBreak() {
		onEndOfTurn();
		onEndOfTurn();
	}

	@Override
	public AbstractOrb makeCopy() { return new FirestoneRune(this.potential); }

}
