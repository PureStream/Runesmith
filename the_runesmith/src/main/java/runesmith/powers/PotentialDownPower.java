package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PotentialDownPower extends AbstractPower {

    public static final String POWER_ID = "Runesmith:PotentialDownPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final int AMOUNT_CAP = 999;

    public PotentialDownPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/PotentialDown.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/PotentialDownSmall.png"), 0, 0, 32, 32);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount > AMOUNT_CAP) {
            this.amount = AMOUNT_CAP;
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(
                            owner,
                            owner,
                            new PotentialPower(owner, -amount),
                            -amount
                    )
            );
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }

    public void updateDescription() {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

}
