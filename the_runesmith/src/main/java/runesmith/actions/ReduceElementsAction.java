package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static runesmith.ui.ElementsCounter.Elements;
import static runesmith.ui.ElementsCounter.applyElements;

public class ReduceElementsAction extends AbstractGameAction {

    private int ignis;
    private int terra;
    private int aqua;

    public ReduceElementsAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua) {
        this.ignis = ignis;
        this.terra = terra;
        this.aqua = aqua;
    }

    public ReduceElementsAction(int ignis, int terra, int aqua) {
        this(null, null, ignis, terra, aqua);
    }

    public ReduceElementsAction(Elements element, int amount) {
        if (element == Elements.IGNIS)
            ignis = amount;
        else if (element == Elements.TERRA)
            terra = amount;
        else if (element == Elements.AQUA)
            aqua = amount;
    }

    @Override
    public void update() {
        applyElements(-ignis, -terra, -aqua);
        this.isDone = true;
    }
}