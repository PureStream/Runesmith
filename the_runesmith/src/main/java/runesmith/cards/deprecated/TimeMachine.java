package runesmith.cards.deprecated;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.TimeMachinePower;

public class TimeMachine extends CustomCard {

    public static final String ID = "Runesmith:TimeMachine";
    public static final String IMG_PATH = "images/cards/TimeMachine.png";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("Runesmith:TimeMachine");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 3;
    private static final int TURNS = 5;
    private static final int UPGRADE_TURNS = 1;

    private static final float CARD_TIP_PAD = 16.0F;
    private static final AbstractCard CARD_TO_PREVIEW = new TimeTravel();
    private boolean isOnPreview = false;

    public TimeMachine() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                AbstractCard.CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                AbstractCard.CardRarity.RARE,
                AbstractCard.CardTarget.SELF
        );
        this.exhaust = true;
        this.isEthereal = true;
        this.baseMagicNumber = this.magicNumber = TURNS;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int health, block, ignis, terra, aqua;
        health = p.currentHealth;
        block = p.currentBlock;
        ignis = p.hasPower("Runesmith:IgnisPower") ? p.getPower("Runesmith:IgnisPower").amount : 0;
        terra = p.hasPower("Runesmith:TerraPower") ? p.getPower("Runesmith:TerraPower").amount : 0;
        aqua = p.hasPower("Runesmith:AquaPower") ? p.getPower("Runesmith:AquaPower").amount : 0;

        if (p.hasPower("Runesmith:TimeMachinePower")) {
            AbstractPower pow = p.getPower("Runesmith:TimeMachinePower");
            if (pow instanceof TimeMachinePower) {
                ((TimeMachinePower) pow).setValues(health, block, ignis, terra, aqua);
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TimeMachinePower(p, health, block, ignis, terra, aqua)));
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new TimeTravel(), 1, false, true,true));
    }

    @Override
    public void hover() {
//        try {
//            CARD_TO_PREVIEW = TimeTravel.class.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            e.printStackTrace();
//        }
        super.hover();
        this.isOnPreview = true;
    }

    @Override
    public void unhover() {
        super.unhover();
        this.isOnPreview = false;
    }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        if ((!Settings.hideCards) && (this.isOnPreview))
        {
            if ((SingleCardViewPopup.isViewingUpgrade) && (this.isSeen) && (!this.isLocked)) {
                AbstractCard copy = makeStatEquivalentCopy();
                copy.current_x = this.current_x;
                copy.current_y = this.current_y;
                copy.drawScale = this.drawScale;
                copy.upgrade();

                TipHelper.renderTipForCard(copy, sb, copy.keywords);
            } else {
                super.renderCardTip(sb);
            }
        }

        if (!Settings.hideCards && this.isOnPreview) {
            float tmpScale = this.drawScale / 1.5F;

            if ((AbstractDungeon.player != null) && (AbstractDungeon.player.isDraggingCard)) {
                return;
            }

            //						x    = card center	  + half the card width 			 + half the preview width 					  + Padding			* Viewport scale * drawscale
            if (this.current_x > Settings.WIDTH * 0.75F) {
                CARD_TO_PREVIEW.current_x = this.current_x + (((AbstractCard.IMG_WIDTH / 2.0F) + ((AbstractCard.IMG_WIDTH / 2.0F) / 1.5F) + (CARD_TIP_PAD)) * this.drawScale);
            } else {
                CARD_TO_PREVIEW.current_x = this.current_x - (((AbstractCard.IMG_WIDTH / 2.0F) + ((AbstractCard.IMG_WIDTH / 2.0F) / 1.5F) + (CARD_TIP_PAD)) * this.drawScale);
            }

            CARD_TO_PREVIEW.current_y = this.current_y + ((AbstractCard.IMG_HEIGHT / 2.0F) - (AbstractCard.IMG_HEIGHT / 2.0F / 1.5F)) * this.drawScale;

            CARD_TO_PREVIEW.drawScale = tmpScale;
            CARD_TO_PREVIEW.render(sb);
        }
    }

    public AbstractCard makeCopy() {
        return new TimeMachine();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.isEthereal = false;
            upgradeMagicNumber(UPGRADE_TURNS);
            this.initializeDescription();
        }
    }

}
