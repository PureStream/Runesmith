package runesmith.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LastStandPower extends AbstractPower {

    public static final String POWER_ID = "Runesmith:LastStandPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int damageAmount;
    private static int LastStandIdOffset;

    public LastStandPower(AbstractCreature owner, int damageAmount, int turns) {
        this.name = NAME;
        this.ID = (POWER_ID + LastStandIdOffset);
        LastStandIdOffset++;
        this.owner = owner;
        this.damageAmount = damageAmount;
        this.amount = turns;
        updateDescription();
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/ReplicatingBarrier.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/powers/ReplicatingBarrierSmall.png"), 0, 0, 32, 32);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (amount <= 0)
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
                flash();
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                        DamageInfo.createDamageMatrix(damageAmount, true),
                        DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
            }
        }
        updateDescription();
    }

    public void updateDescription() {
        if (amount == 2)
            this.description = (DESCRIPTIONS[0] + DESCRIPTIONS[2] + damageAmount + DESCRIPTIONS[3]);
        else
            this.description = (DESCRIPTIONS[1] + DESCRIPTIONS[2] + damageAmount + DESCRIPTIONS[3]);
    }

}
