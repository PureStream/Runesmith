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

import static runesmith.ui.ElementsCounter.*;

public class LightningRodPower extends AbstractPower {

    public static final String POWER_ID = "Runesmith:LightningRodPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String[] ELEMENTS_ID = {IGNIS_ID, TERRA_ID, AQUA_ID};

    public LightningRodPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/Aqua.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/AquaSmall.png"), 0, 0, 32, 32);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0)
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    public int onLoseHp(int damageAmount) {
        for (String powerStr : ELEMENTS_ID)
            damageAmount = elementsLostCheck(damageAmount, powerStr);
        return damageAmount;
    }

    private int elementsLostCheck(int damageAmount, String elementID) {
        if (damageAmount > 0) {
            int element = getElementByID(elementID);
            if (damageAmount > element) {
                applyElementsByID(elementID, -element);
                damageAmount -= element;
            } else {
                applyElementsByID(elementID, -damageAmount);
                damageAmount = 0;
            }
        }
        return damageAmount;
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}