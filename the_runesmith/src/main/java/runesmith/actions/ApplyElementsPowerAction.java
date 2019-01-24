package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.patches.ElementsGainedCountField;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class ApplyElementsPowerAction extends AbstractGameAction{
	
	private int oriIgnis, oriTerra, oriAqua;
	private int ignis;
	private int terra;
	private int aqua;
	private AbstractPlayer p;
	private static final int MAX_ELEMENTS = 10;
	
	public ApplyElementsPowerAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua){
		oriIgnis = ignis;
		oriTerra = terra;
		oriAqua = aqua;
		this.ignis = ignis>MAX_ELEMENTS?MAX_ELEMENTS:ignis;
		this.terra = terra>MAX_ELEMENTS?MAX_ELEMENTS:terra;
		this.aqua = aqua>MAX_ELEMENTS?MAX_ELEMENTS:aqua;
		this.p = (AbstractPlayer) target;
	}
	
	@Override
	public void update() {
		
//		if(p.hasPower("Runesmith:RunesonancePower")) {
//			this.ignis = this.ignis*2>MAX_ELEMENTS?MAX_ELEMENTS:ignis*2;
//			this.terra = this.terra*2>MAX_ELEMENTS?MAX_ELEMENTS:terra*2;
//			this.aqua = this.aqua*2>MAX_ELEMENTS?MAX_ELEMENTS:aqua*2;
//			p.getPower("Runesmith:RunesonancePower").flash();
//		}
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
		ElementsGainedCountField.elementsCount.set(p, ElementsGainedCountField.elementsCount.get(p)+oriIgnis+oriTerra+oriAqua);
		this.isDone = true;
	}
}
