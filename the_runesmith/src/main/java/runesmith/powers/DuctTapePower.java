package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import runesmith.actions.StasisCardInHandAction;
import runesmith.actions.cards.FlexTapeAction;

public class DuctTapePower extends AbstractPower {

    public static final String POWER_ID = "Runesmith:DuctTapePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DuctTapePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/DuctTape.png"), 0, 0, 84, 84);  //<-------- NEED SOME IMG
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/DuctTapeSmall.png"), 0, 0, 32, 32); //<-------- NEED SOME IMG
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0)
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Runesmith:FlexTapePower"));
    }

    public void atStartOfTurnPostDraw() {
        if(owner instanceof AbstractPlayer) {
            flash();
            AbstractDungeon.actionManager.addToBottom(
                    new StasisCardInHandAction((AbstractPlayer) owner, amount)
            );
        }
    }

    public void updateDescription() {
        if (amount == 1)
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
        else
            this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2]);
    }

}
