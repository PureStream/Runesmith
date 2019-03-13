package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import runesmith.RunesmithMod;
import runesmith.patches.ElementsGainedCountField;
import runesmith.relics.CoreCrystal;
import runesmith.ui.ElementsCounter;

public class ApplyElementsAction extends AbstractGameAction {

//    private int oriIgnis, oriTerra, oriAqua;
    private int ignis;
    private int terra;
    private int aqua;
    private AbstractPlayer p;

    public ApplyElementsAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua) {
        this.p = (AbstractPlayer) target;
        int maxStacks = RunesmithMod.DEFAULT_MAX_ELEMENTS;

        if (p.hasRelic(CoreCrystal.ID)) {
            maxStacks = CoreCrystal.MAX_ELEMENTS;
            double multipler = 1.5;
            ignis = (int) (ignis*multipler);
            terra = (int) (terra*multipler);
            aqua = (int) (aqua*multipler);
        }
        this.ignis = ignis > maxStacks ? maxStacks : ignis;
        this.terra = terra > maxStacks ? maxStacks : terra;
        this.aqua = aqua > maxStacks ? maxStacks : aqua;
    }

    @Override
    public void update() {
        ElementsCounter.applyElements(ignis, terra, aqua);

        ElementsGainedCountField.elementsCount.set(p, ElementsGainedCountField.elementsCount.get(p) + ignis + terra + aqua);
        this.isDone = true;
    }
}
