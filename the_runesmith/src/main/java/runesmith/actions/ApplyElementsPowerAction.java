package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import runesmith.RunesmithMod;
import runesmith.patches.ElementsGainedCountField;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;
import runesmith.relics.CoreCrystal;

public class ApplyElementsPowerAction extends AbstractGameAction {

//    private int oriIgnis, oriTerra, oriAqua;
    private int ignis;
    private int terra;
    private int aqua;
    private AbstractPlayer p;

    public ApplyElementsPowerAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua) {
        this.p = (AbstractPlayer) target;
        int maxStacks = RunesmithMod.DEFAULT_MAX_ELEMENTS;

        if (p.hasRelic(CoreCrystal.ID)) {
            maxStacks = CoreCrystal.MAX_ELEMENTS;
            double multipler = 1.5;
            ignis = (int) (ignis*multipler);
            terra = (int) (terra*multipler);
            aqua = (int) (aqua*multipler);
        }
//        oriIgnis = (int) (ignis*multipler);
//        oriTerra = (int) (terra*multipler);
//        oriAqua = (int) (aqua*multipler);
        this.ignis = ignis > maxStacks ? maxStacks : ignis;
        this.terra = terra > maxStacks ? maxStacks : terra;
        this.aqua = aqua > maxStacks ? maxStacks : aqua;
        
    }

    @Override
    public void update() {
//		if(p.hasPower("Runesmith:RunesonancePower")) {
//			this.ignis = this.ignis*2>maxStacks?maxStacks:ignis*2;
//			this.terra = this.terra*2>maxStacks?maxStacks:terra*2;
//			this.aqua = this.aqua*2>maxStacks?maxStacks:aqua*2;
//			p.getPower("Runesmith:RunesonancePower").flash();
//		}

        if (this.aqua != 0) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(
                            p,
                            p,
                            new AquaPower(p, this.aqua),
                            this.aqua
                    )
            );
        }
        if (this.terra != 0) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(
                            p,
                            p,
                            new TerraPower(p, this.terra),
                            this.terra
                    )
            );
        }
        if (this.ignis != 0) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(
                            p,
                            p,
                            new IgnisPower(p, this.ignis),
                            this.ignis
                    )
            );
        }

        ElementsGainedCountField.elementsCount.set(p, ElementsGainedCountField.elementsCount.get(p) + ignis + terra + aqua);
        this.isDone = true;
    }
}
