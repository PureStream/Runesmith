package runesmith.orbs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class FirestoneRune extends RuneOrb {

	public FirestoneRune(int potential) {
		super( "Firestone",
				false,
				potential);
		
	}
	
	@Override
	public void onEndOfTurn() {
		this.activateEffect();
		
		new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.potential, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE);
	}

	@Override
	public AbstractOrb makeCopy() { return new FirestoneRune(this.potential); }

}
