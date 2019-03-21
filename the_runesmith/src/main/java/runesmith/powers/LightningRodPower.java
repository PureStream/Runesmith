package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runesmith.ui.ElementsCounter;
import runesmith.utils.TextureLoader;

import static runesmith.ui.ElementsCounter.Elements;
import static runesmith.ui.ElementsCounter.getElementByID;

public class LightningRodPower extends AbstractPower {

    public static final String POWER_ID = "Runesmith:LightningRodPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Elements[] ELEMENTS_ID = {Elements.IGNIS, Elements.TERRA, Elements.AQUA};

    public LightningRodPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = true;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture("images/powers/LightningRod.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(TextureLoader.getTexture("images/powers/LightningRodSmall.png"), 0, 0, 32, 32);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0)
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public int onLoseHp(int damageAmount) {
        for (Elements elem : ELEMENTS_ID)
            damageAmount = elementsLostCheck(damageAmount, elem);
        return damageAmount;
    }

    private int elementsLostCheck(int damageAmount, Elements elementID) {
        if (damageAmount > 0) {
            int element = getElementByID(elementID);
            if (damageAmount > element) {
                ElementsCounter.applyElements(elementID, -element);
                damageAmount -= element;
            } else {
                ElementsCounter.applyElements(elementID, -damageAmount);
                damageAmount = 0;
            }
        }
        return damageAmount;
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + ((this.amount > 1) ? DESCRIPTIONS[2] : DESCRIPTIONS[1]) + DESCRIPTIONS[3];
    }

}