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
import runesmith.relics.CoreCrystal;

public class ApplyElementsPowerAction extends AbstractGameAction {

    private int oriIgnis, oriTerra, oriAqua;
    private int ignis;
    private int terra;
    private int aqua;
    private AbstractPlayer p;

    public ApplyElementsPowerAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua) {
        this.p = (AbstractPlayer) target;
        int maxStacks = 10;
        int multipler = 1;
        if (p.hasRelic(CoreCrystal.ID)) {
            maxStacks = 20;
            multipler = 2;
        }
        oriIgnis = ignis*multipler;
        oriTerra = terra*multipler;
        oriAqua = aqua*multipler;
        this.ignis = ignis*multipler > maxStacks ? maxStacks : ignis*multipler;
        this.terra = terra*multipler > maxStacks ? maxStacks : terra*multipler;
        this.aqua = aqua*multipler > maxStacks ? maxStacks : aqua*multipler;
        
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

        ElementsGainedCountField.elementsCount.set(p, ElementsGainedCountField.elementsCount.get(p) + oriIgnis + oriTerra + oriAqua);
        this.isDone = true;
    }
}
