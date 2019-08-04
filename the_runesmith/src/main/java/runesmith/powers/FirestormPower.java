package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runesmith.actions.ApplyElementsPowerAction;

public class FirestormPower extends AbstractPower {

    public static final String POWER_ID = "Runesmith:FirestormPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FirestormPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("runesmith/images/powers/Firestorm.png"), 0, 0, 84, 84);  //<-------- NEED SOME IMG
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("runesmith/images/powers/FirestormSmall.png"), 0, 0, 32, 32); //<-------- NEED SOME IMG
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0)
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Runesmith:FirestormPower"));
    }

    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom(
                new ApplyElementsPowerAction(owner, owner, amount, amount, 0));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new IgnisPower(owner, amount), amount));
//		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new TerraPower(owner, amount), amount));
    }

    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
    }

}
