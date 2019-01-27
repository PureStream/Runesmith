package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.BobEffect;

public class FirestoneRune extends RuneOrb {
	
	public static final Logger logger = LogManager.getLogger(FirestoneRune.class.getName());
	
	public static final int basePotency = 4;
	
	public static Texture img1;
	public static Texture img2;
	public static Texture img3;
	
	private BobEffect extraBobEffect1 = new BobEffect(6.0F * Settings.scale, 3.0F);
	private BobEffect extraBobEffect2 = new BobEffect(5.0F * Settings.scale, 2.2F);
	private float slope = (float) Math.tan(Math.toRadians(105));

	public FirestoneRune(int potential) {
		super( "Firestone",
				false,
				potential);
		
		if(img1 == null) {
			img1 = ImageMaster.loadImage("images/orbs/Firestone/FireR.png");
			img2 = ImageMaster.loadImage("images/orbs/Firestone/FireU.png");
			img3 = ImageMaster.loadImage("images/orbs/Firestone/FireD.png");
		}
		
//		logger.info("slope is: " + this.slope);
	}
	
	@Override
	public void onEndOfTurn() {
		//get random target
		//AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(true);
		//damage enemy
		this.activateEffect();
//		logger.info("damaging...");
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
		this.activateEffect();
		AbstractDungeon.actionManager.addToTop(
				new DamageRandomEnemyAction(
						new DamageInfo(AbstractDungeon.player,
								this.potential,
								DamageInfo.DamageType.THORNS),
						AbstractGameAction.AttackEffect.FIRE
				)
		);
		AbstractDungeon.actionManager.addToTop(
				new DamageRandomEnemyAction(
						new DamageInfo(AbstractDungeon.player,
								this.potential,
								DamageInfo.DamageType.THORNS),
						AbstractGameAction.AttackEffect.FIRE
				)
		);
	}

	@Override
	public AbstractOrb makeCopy() { return new FirestoneRune(this.potential); }
	
	@Override
	public void render(SpriteBatch sb)
	{
		sb.setColor(this.c);
		float ft = this.extraBobEffect1.y + this.extraBobEffect2.y / 2.0F;
		float dX =  (float) Math.sqrt(Math.abs(ft) / (1 + this.slope * this.slope));
		float dY =  (float) -Math.sqrt(Math.abs(ft) / (1 + 1 / (this.slope * this.slope)));
		if(ft<0.0F) {
			dX = -dX;
			dY = -dY;
		}
		
		sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 4.0F + dX + this.extraBobEffect2.y / 1.5F
				,this.cY - 48.0F + this.bobEffect.y / 4.0F + dY 
				, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
		sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + this.extraBobEffect2.y / 1.5F ,
				this.cY - 48.0F + this.bobEffect.y / 4.0F
				, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
		sb.draw(img3, this.cX - 48.0F + this.bobEffect.y/ 2.0F , this.cY - 48.0F + this.bobEffect.y / 4.0F
				, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false); 
		
		
//		sb.draw(img1, this.cX - 48.0F + this.bobEffect.y / 4.0F + this.extraBobEffect1.y / slope + this.extraBobEffect2.y,
//				this.cY - 48.0F + this.bobEffect.y / 4.0F + this.extraBobEffect1.y * slope
//				, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
//		sb.draw(img2, this.cX - 48.0F + this.bobEffect.y / 4.0F + this.extraBobEffect2.y, this.cY - 48.0F + this.bobEffect.y / 4.0F
//				, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
//		sb.draw(img3, this.cX - 48.0F + this.bobEffect.y / 4.0F , this.cY - 48.0F + this.bobEffect.y / 2.0F
//				, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false); 
		renderText(sb);
	    this.hb.render(sb);
	}	

	@Override
	public void updateAnimation(){
		this.extraBobEffect1.update();
		this.extraBobEffect2.update();
		super.updateAnimation();
	}
}
