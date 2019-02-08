package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.EnhanceCountField;
import runesmith.powers.PotentialPower;

public abstract class AbstractRunicCard extends CustomCard {
    public AbstractRunicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                             CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);
    }

    public int potency;
    public int basePotency = -1;
    public boolean potencyUpgraded;
    public boolean isPotencyModified;

    public boolean isCraftable = false;
    public boolean renderCraftable = true;

    public boolean freeElementOnce = false;

    private Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    private Color renderColor = Color.WHITE.cpy();
    private static Texture craftableTab = ImageMaster.loadImage("images/cardui/512/craftable_tag.png");

//	public void triggerWhenDrawn() {
//		this.upgradePotency(0);
//	}

    @Override
    public void applyPowers() {
        this.applyPowersToPotency();
        super.applyPowers();

    }

    private void applyPowersToPotency() {
        this.isPotencyModified = false;
        this.potency = this.basePotency + getPotentialPowerValue();
        if (this.potency < 0) this.potency = 0;
        this.potency = this.potency + MathUtils.floor(this.potency * (0.5F * EnhanceCountField.enhanceCount.get(this)));
        if (this.potency != this.basePotency) this.isPotencyModified = true;
    }

    public void upgradePotency(int amount) {
        this.basePotency += amount;
        this.potencyUpgraded = true;
    }

    @Override
    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.potencyUpgraded) {
            this.potency = this.basePotency;
            this.isPotencyModified = true;
        }
    }

    private int getPotentialPowerValue() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            if (p.hasPower("Runesmith:PotentialPower")) {
                PotentialPower pPower = (PotentialPower) p.getPower("Runesmith:PotentialPower");
                return (pPower.onVictory) ? 0 : pPower.amount;
            }
        }
        return 0;
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.potency = this.basePotency;
        this.isPotencyModified = false;
        this.freeElementOnce = false;
    }

    public boolean checkElements(int ignis, int terra, int aqua) {
        return checkElements(ignis, terra, aqua, false, false, false);
    }

    public boolean checkElements(int ignis, int terra, int aqua, boolean checkOnly) {
        return checkElements(ignis, terra, aqua, checkOnly, false, false);
    }

    public boolean checkElements(int ignis, int terra, int aqua, boolean checkOnly, boolean isPotentia) {
        return checkElements(ignis, terra, aqua, checkOnly, isPotentia, false);
    }

    public boolean checkElements(int ignis, int terra, int aqua, boolean checkOnly, boolean isPotentia, boolean isAnAttackCard) {

        //logger.info("Start checking elements.");
        AbstractPlayer p = AbstractDungeon.player;
        int runeCount = RuneOrb.getRuneCount(p);
        int maxRunes = RuneOrb.getMaxRune(p);

        if (this.freeElementOnce || p.hasPower("Runesmith:UnlimitedPowerPower")) {
            if (this.freeElementOnce && !checkOnly)
                freeElementOnce = false;

            if (runeCount >= maxRunes && !checkOnly && !isPotentia)
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, ignis, terra, aqua));

            this.isCraftable = true;

            return true;
        }

        int pIgnis = 0, pTerra = 0, pAqua = 0;

        if (isAnAttackCard) {
            if (p.hasRelic("Runesmith:BrokenRuby"))
                if (p.getRelic("Runesmith:BrokenRuby").counter == 2)
                    pIgnis++;
        }

        if (p.hasPower("Runesmith:IgnisPower")) {
            pIgnis += p.getPower("Runesmith:IgnisPower").amount;
        }
        if (p.hasPower("Runesmith:TerraPower")) {
            pTerra += p.getPower("Runesmith:TerraPower").amount;
        }
        if (p.hasPower("Runesmith:AquaPower")) {
            pAqua += p.getPower("Runesmith:AquaPower").amount;
        }
        if (pIgnis >= ignis && pTerra >= terra && pAqua >= aqua) {
            //logger.info("Have enough elements.");
            if (!checkOnly) {
                if (runeCount >= maxRunes && !isPotentia)
                    AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, ignis, terra, aqua));
                else {
                    if (pIgnis > 0 && ignis > 0) {
                        p.getPower("Runesmith:IgnisPower").reducePower(ignis);
                        if (p.getPower("Runesmith:IgnisPower").amount == 0)
                            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, "Runesmith:IgnisPower"));
                    }
                    if (pTerra > 0 && terra > 0) {
                        p.getPower("Runesmith:TerraPower").reducePower(terra);
                        if (p.getPower("Runesmith:TerraPower").amount == 0)
                            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, "Runesmith:TerraPower"));
                    }
                    if (pAqua > 0 && aqua > 0) {
                        p.getPower("Runesmith:AquaPower").reducePower(aqua);
                        if (p.getPower("Runesmith:AquaPower").amount == 0)
                            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, "Runesmith:AquaPower"));
                    }
                }

            }
            if (checkOnly) this.isCraftable = true;
            return true;
        }
        //logger.info("Not enough elements.");
        if (!checkOnly) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyElementsPowerAction(p, p, ignis, terra, aqua));
        }
        if (checkOnly) {
            this.isCraftable = false;
        }
        return false;
    }

    @Override
    public void render(SpriteBatch sb, boolean selected) {
        super.render(sb, selected);
        if (!Settings.hideCards) {
            renderCraftable(sb, selected);
        }
    }

    private void renderCraftable(SpriteBatch sb, boolean selected) {
        float drawX = this.current_x - 256.0F;
        float drawY = this.current_y - 256.0F;

        if (AbstractDungeon.player != null) {
            if (this.isCraftable && this.renderCraftable) {
                this.renderHelper(sb, this.renderColor, craftableTab, drawX, drawY);
            }
        }
    }

    @Override
    public void onMoveToDiscard() {
        this.isCraftable = false;
    }

    private void renderHelper(SpriteBatch sb, Color color, Texture img, float drawX, float drawY) {
        sb.setColor(color);
        try {
            sb.draw(img, drawX, drawY,
                    256.0F, 256.0F, 512.0F, 512.0F,
                    this.drawScale * Settings.scale, this.drawScale * Settings.scale,
                    this.angle, 0, 0, 512, 512, false, false);

        } catch (Exception e) {
            ExceptionHandler.handleException(e, logger);
        }
    }

//	public void addPower(String element, int value) {
//		AbstractPlayer p = AbstractDungeon.player;
//		if (element.equals("IgnisPower")) {
//			AbstractDungeon.actionManager.addToTop(
//			          new ApplyPowerAction(
//			              p,
//			              p,
//			              new IgnisPower(p, value),
//			              value
//			          )
//			      );
//		}
//		else if (element.equals("TerraPower")) {
//			AbstractDungeon.actionManager.addToTop(
//			          new ApplyPowerAction(
//			              p,
//			              p,
//			              new TerraPower(p, value),
//			              value
//			          )
//			      );
//		}
//		else {
//			AbstractDungeon.actionManager.addToTop(
//			          new ApplyPowerAction(
//			              p,
//			              p,
//			              new AquaPower(p, value),
//			              value
//			          )
//			      );
//		}
//	}
}
