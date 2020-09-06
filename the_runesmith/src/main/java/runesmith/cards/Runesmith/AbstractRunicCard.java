package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.ExceptionHandler;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.RunesmithMod;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.actions.ReduceElementsPowerAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;
import runesmith.powers.PotentialPower;
import runesmith.powers.UnlimitedPowerPower;
import runesmith.relics.PocketReactor;
import runesmith.ui.ElementsCounter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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

    public static boolean alwaysFreeToCraft = false;

    protected int[] elementCost = new int[3];

//    int overchargePot;
//    boolean isOvercharge;

    public boolean isCraftable = false;
    boolean renderCraftable = true;

    public boolean freeElementOnce = false;

    private Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    private Color renderColor = Color.WHITE.cpy();
    private Color textColor =  Settings.CREAM_COLOR.cpy();
    private static Texture craftableTab = ImageMaster.loadImage("runesmith/images/cardui/512/craftable_tag_blank.png");
    private static String[] craftableString = CardCrawlGame.languagePack.getUIString("Runesmith:Craftable").TEXT;

    private static Texture ELEMENT_ORB_RED, ELEMENT_ORB_GREEN, ELEMENT_ORB_BLUE;

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

    @Override
    public void triggerOnGlowCheck(){
        glowColor = isCraftable ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }

    public int[] getElementCost(){ return elementCost;}

    private boolean hasEnoughElement(Elements elem){
        if(this.freeElementOnce || alwaysFreeToCraft){
            return true;
        }
        switch (elem){
            case IGNIS: return elementCost[0] <= ElementsCounter.getIgnis();
            case TERRA: return elementCost[1] <= ElementsCounter.getTerra();
            case AQUA:  return elementCost[2] <= ElementsCounter.getAqua();
            default: return false;
        }
    }

    public static boolean checkAlwaysFreeToCraft(){
        AbstractPlayer p = AbstractDungeon.player;
        alwaysFreeToCraft = p.hasRelic(PocketReactor.ID)||p.hasPower(UnlimitedPowerPower.POWER_ID);
        return alwaysFreeToCraft;
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

        if (this.freeElementOnce || alwaysFreeToCraft) {
            if (this.freeElementOnce && !checkOnly)
                freeElementOnce = false;

//            if (runeCount >= maxRunes && !checkOnly && !isPotentia)
//                AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, ignis, terra, aqua));

            this.isCraftable = true;

            return true;
        }

        int pIgnis = getIgnis(), pTerra = getTerra(), pAqua = getAqua();

        if (pIgnis >= ignis && pTerra >= terra && pAqua >= aqua) {
            //logger.info("Have enough elements.");
            if (!checkOnly) {
                if (runeCount >= maxRunes && !isPotentia)
                    AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, ignis, terra, aqua));
                else
                    AbstractDungeon.actionManager.addToBottom(new ReduceElementsPowerAction(ignis, terra, aqua));
            }
            if (checkOnly)
                this.isCraftable = true;
            return true;
        }
        //logger.info("Not enough elements.");
        if (!checkOnly)
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, ignis, terra, aqua));
        if (checkOnly)
            this.isCraftable = false;
        return false;
    }

    @Override
    public void render(SpriteBatch sb, boolean selected) {
        super.render(sb, selected);
        if (!Settings.hideCards) {
            if(!isFlipped) {
                renderCraftable(sb);
//                renderElementsCost(sb);
            }
        }
    }

    private void renderCraftable(SpriteBatch sb) {
        if (AbstractDungeon.player != null) {
            float drawX = this.current_x - 256.0F;
            float drawY = this.current_y - 256.0F;
            if (this.isCraftable && this.renderCraftable) {
                this.renderHelper(sb, this.renderColor, craftableTab, drawX, drawY);
                BitmapFont font = FontHelper.cardDescFont_L;
                font.getData().setScale(1.0F);
                GlyphLayout gl = new GlyphLayout(font, craftableString[0]);
                float scale = Math.min((82.0F)/gl.width, (15.0F)/gl.height)*this.drawScale;
                font.getData().setScale(scale*Settings.scale);
                FontHelper.renderRotatedText(sb, font, craftableString[0], this.current_x, this.current_y, 0.0F, 214.5F * Settings.scale * this.drawScale, this.angle, true, this.textColor);
                font.getData().setScale(1.0F);
            }
        }
    }

    private static Color ENERGY_COST_RESTRICTED_COLOR, ENERGY_COST_MODIFIED_COLOR;

    private static void getColorConstants(){
        Field f;
        try {
            f = AbstractCard.class.getDeclaredField("ENERGY_COST_RESTRICTED_COLOR");
            f.setAccessible(true);
            ENERGY_COST_RESTRICTED_COLOR = (Color) f.get(null);

            f = AbstractCard.class.getDeclaredField("ENERGY_COST_MODIFIED_COLOR");
            f.setAccessible(true);
            ENERGY_COST_MODIFIED_COLOR = (Color) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void renderElementsCost(AbstractRunicCard card, SpriteBatch sb){
        float drawX = card.current_x - 256.0F;
        float drawY = card.current_y - 256.0F;

        if(ELEMENT_ORB_RED == null){
            ELEMENT_ORB_RED = ImageMaster.loadImage("runesmith/images/cardui/512/card_element_orb_red.png");
            ELEMENT_ORB_GREEN = ImageMaster.loadImage("runesmith/images/cardui/512/card_element_orb_green.png");
            ELEMENT_ORB_BLUE = ImageMaster.loadImage("runesmith/images/cardui/512/card_element_orb_blue.png");
        }

        if(ENERGY_COST_MODIFIED_COLOR == null){
            getColorConstants();
        }
        if(!card.isLocked && card.isSeen) {

            float yOffset = 55.0F * Settings.scale * card.drawScale;
            int counter = 0;
            //logger.info("attempting render");
            for (int i = 0; i < 3; i++) {
                Texture tex;
                Elements elem;
                switch (i) {
                    case 0:
                        tex = ELEMENT_ORB_RED;
                        elem = Elements.IGNIS;
                        break;
                    case 1:
                        tex = ELEMENT_ORB_GREEN;
                        elem = Elements.TERRA;
                        break;
                    case 2:
                        tex = ELEMENT_ORB_BLUE;
                        elem = Elements.AQUA;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + i);
                }
                if (card.elementCost[i] != 0) {
                    Vector2 offset = new Vector2(0, -yOffset * counter);
                    offset.rotate(card.angle);
                    card.renderHelper(sb, card.renderColor, tex, drawX + offset.x, drawY + offset.y);

                    String msg = card.elementCost[i] + "";
                    Color costColor = Color.WHITE.cpy();
                    if (AbstractDungeon.player != null && AbstractDungeon.player.hand.contains(card)){
                        if (!card.hasEnoughElement(elem)) {
                            costColor = ENERGY_COST_RESTRICTED_COLOR;
                        } else if (alwaysFreeToCraft || card.freeElementOnce) {
                            msg = "0";
                            costColor = ENERGY_COST_MODIFIED_COLOR;
                        }
                    }
                    costColor.a = card.transparency;

                    FontHelper.renderRotatedText(sb, getElementFont(card), msg, card.current_x,
                            card.current_y, -132.0F * card.drawScale * Settings.scale,
                            129.0F * card.drawScale * Settings.scale - yOffset * counter, card.angle,
                            true, costColor);
                    counter++;
                }
            }
        }
    }

    private static BitmapFont getElementFont(AbstractCard card) {
        FontHelper.cardEnergyFont_L.getData().setScale(card.drawScale * 0.75f);
        return FontHelper.cardEnergyFont_L;
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


//    public boolean isOvercharge(){return this.isOvercharge;}

//    private List<TooltipInfo> tips = new ArrayList<>();
//    private static final UIStrings overchargeTip1 = CardCrawlGame.languagePack.getUIString("Runesmith:Overcharged");
//    private static final UIStrings overchargeTip2 = CardCrawlGame.languagePack.getUIString("Runesmith:OverchargeAt");

//    @Override
//    public List<TooltipInfo> getCustomTooltips() {
//        this.tips.clear();
//        if(this.isOvercharge){
//            tips.add(new TooltipInfo(overchargeTip1.TEXT[0], overchargeTip1.TEXT[1]));
//        }else{
//            tips.add(new TooltipInfo(overchargeTip2.TEXT[0], overchargeTip2.TEXT[1].replace("{pot}", this.overchargePot+"")));
//        }
//        return this.tips;
//    }
}
