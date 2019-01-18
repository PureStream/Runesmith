package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class ApplyElementsPowerAction extends AbstractGameAction{

	private int ignis;
	private int terra;
	private int aqua;
	private AbstractPlayer p;
	
	public ApplyElementsPowerAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua){
		this.ignis = ignis;
		this.terra = terra;
		this.aqua = aqua;
		this.p = (AbstractPlayer) target;
	}
	
	@Override
	public void update() {
		if(p.hasPower("Runesmith:RunesonancePower")) {
			this.ignis = this.ignis*2;
			this.terra = this.terra*2;
			this.aqua = this.aqua*2;
			p.getPower("Runesmith:RunesonancePower").flash();
		}
		if(this.ignis!=0) {
		AbstractDungeon.actionManager.addToTop(
		          new ApplyPowerAction(
		              p,
		              p,
		              new IgnisPower(p, this.ignis),
		              this.ignis
		          )
		      );
		}
		if(this.terra!=0) {
		AbstractDungeon.actionManager.addToTop(
		          new ApplyPowerAction(
		              p,
		              p,
		              new TerraPower(p, this.terra),
		              this.terra
		          )
		      );
		}
		if(this.aqua!=0) {
		AbstractDungeon.actionManager.addToTop(
		          new ApplyPowerAction(
		              p,
		              p,
		              new AquaPower(p, this.aqua),
		              this.aqua
		          )
		      );
		}
		
		this.isDone = true;
	}
}
