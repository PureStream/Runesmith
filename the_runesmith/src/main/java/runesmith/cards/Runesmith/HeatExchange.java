package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.patches.AbstractCardEnum;

public class HeatExchange extends CustomCard {
    public static final String ID = "Runesmith:HeatExchange";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/HeatExchange.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int IGNIS_AMT = 1;
    private static final int MULTIPLIER_AMT = 1;
    private static final int UPG_MULTIPLIER_AMT = 1;

    public HeatExchange() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.COMMON,
                CardTarget.ALL_ENEMY
        );
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = MULTIPLIER_AMT;
        this.isMultiDamage = true;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = ATTACK_DMG;
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasRelic("Runesmith:BrokenRuby")) {
            if (p.getRelic("Runesmith:BrokenRuby").counter == 2)
                baseDamage += this.magicNumber;
        }
        if (p.hasPower("Runesmith:IgnisPower")) {
            int additionDamage = p.getPower("Runesmith:IgnisPower").amount * this.magicNumber;
            baseDamage += additionDamage;
        }
        super.applyPowers();

        if (upgraded)
            this.rawDescription = DESCRIPTION_UPG;
        else
            this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        if (upgraded)
            this.rawDescription = DESCRIPTION_UPG;
        else
            this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE)
        );
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(p, p, "Runesmith:IgnisPower", IGNIS_AMT));
    }

    public AbstractCard makeCopy() {
        return new HeatExchange();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = DESCRIPTION_UPG;
            this.upgradeMagicNumber(UPG_MULTIPLIER_AMT);
            initializeDescription();
        }
    }
}
