package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static runesmith.ui.ElementsCounter.Elements;
import static runesmith.ui.ElementsCounter.applyElements;

public class ReduceElementsPowerAction extends AbstractGameAction {

    private int ignis;
    private int terra;
    private int aqua;

    public ReduceElementsPowerAction(AbstractCreature target, AbstractCreature source, int ignis, int terra, int aqua) {
        this.ignis = ignis;
        this.terra = terra;
        this.aqua = aqua;
    }

    public ReduceElementsPowerAction(int ignis, int terra, int aqua) {
        this(null, null, ignis, terra, aqua);
    }

    public ReduceElementsPowerAction(Elements element, int amount) {
        if (element == Elements.IGNIS)
            this.ignis = amount;
        else if (element == Elements.TERRA)
            this.terra = amount;
        else if (element == Elements.AQUA)
            this.aqua = amount;
    }

    @Override
    public void update() {
        applyElements(-ignis, -terra, -aqua);
        this.isDone = true;
    }
}