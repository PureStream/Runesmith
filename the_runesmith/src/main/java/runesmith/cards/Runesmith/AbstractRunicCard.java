package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;
import runesmith.actions.ApplyElementsAction;
import runesmith.actions.ReduceElementsAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.EnhanceCountField;
import runesmith.powers.PotentialPower;
import runesmith.powers.UnlimitedPowerPower;
import runesmith.relics.PocketReactor;

import static runesmith.ui.ElementsCounter.*;

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
    boolean renderCraftable = true;

    public boolean freeElementOnce = false;

    private Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    private Color renderColor = Color.WHITE.cpy();
    private Color textColor =  Settings.CREAM_COLOR.cpy();
    private static Texture craftableTab = ImageMaster.loadImage("images/cardui/512/craftable_tag_blank.png");
    private static String[] craftableString = CardCrawlGame.languagePack.getUIString("Runesmith:Craftable").TEXT;

    @Override
    public void applyPowers() {
        this.applyPowersToPotency();
        super.applyPowers();

    }

    private void applyPowersToPotency() {
        this.isPotencyModified = false;
        this.potency = this.basePotency + getPotentialPowerValue();
        if (AbstractDungeon.player.hasRelic(PocketReactor.ID))
            this.potency -= 2;
        if (this.potency < 0)
            this.potency = 0;
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
            if (p.hasPower(PotentialPower.POWER_ID)) {
                PotentialPower pPower = (PotentialPower) p.getPower(PotentialPower.POWER_ID);
                return pPower.amount;
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
        int runeCount = RuneOrb.getRuneCount();
        int maxRunes = RuneOrb.getMaxRune(p);

        if (this.freeElementOnce || p.hasPower(UnlimitedPowerPower.POWER_ID) || p.hasRelic(PocketReactor.ID)) {
            if (this.freeElementOnce && !checkOnly)
                freeElementOnce = false;

            if (runeCount >= maxRunes && !checkOnly && !isPotentia)
                AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, ignis, terra, aqua));

            this.isCraftable = true;

            return true;
        }

        int pIgnis = getIgnis(), pTerra = getTerra(), pAqua = getAqua();

        if (pIgnis >= ignis && pTerra >= terra && pAqua >= aqua) {
            //logger.info("Have enough elements.");
            if (!checkOnly) {
                if (runeCount >= maxRunes && !isPotentia)
                    AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, ignis, terra, aqua));
                else
                    AbstractDungeon.actionManager.addToBottom(new ReduceElementsAction(p, p, ignis, terra, aqua));
            }
            if (checkOnly)
                this.isCraftable = true;
            return true;
        }
        //logger.info("Not enough elements.");
        if (!checkOnly)
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, ignis, terra, aqua));
        if (checkOnly)
            this.isCraftable = false;
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
                BitmapFont font = FontHelper.menuBannerFont;
                font.getData().setScale(1.0F);
                GlyphLayout gl = new GlyphLayout(font, craftableString[0]);
                float scale = Math.min((82.0F*this.drawScale)/gl.width, (15.0F*this.drawScale)/gl.height);
                FontHelper.menuBannerFont.getData().setScale(scale*Settings.scale);
                FontHelper.renderRotatedText(sb, FontHelper.menuBannerFont, craftableString[0], this.current_x, this.current_y, 0.0F, 429.0F * Settings.scale * this.drawScale / 2.0F, this.angle, true, this.textColor);
                FontHelper.menuBannerFont.getData().setScale(1.0F);
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

}
