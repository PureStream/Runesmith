package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import runesmith.ui.ElementsCounter;

public class ReduceElementsAction extends AbstractGameAction {

    //    private int oriIgnis, oriTerra, oriAqua;
    private int ignis;
    private int terra;
    private int aqua;
    private AbstractPlayer p;

    public ReduceElementsAction(AbstractPlayer target, AbstractCreature source, int ignis, int terra, int aqua) {
        this.p = target;
        this.ignis = ignis;
        this.terra = terra;
        this.aqua = aqua;
    }

    @Override
    public void update() {
        ElementsCounter.applyElements(-ignis, -terra, -aqua);
        this.isDone = true;
    }
}