package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.DowngradeCard;
import runesmith.actions.DowngradeEntireDeckAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.LastStandPower;
import runesmith.ui.ElementsCounter;

public class LastStand extends CustomCard {
    public static final String ID = "Runesmith:LastStand";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/LastStand.png"; //<-------------- need some img
    private static final int COST = 3;
    private static final int MULTIPLIER_AMT = 5;
    private static final int UPG_MULTIPLIER_AMT = 2;
    private static final int TURNS = 2;

    public LastStand() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.NONE
        );
        this.baseDamage = this.damage = 0;
//        this.isMultiDamage = true;
        this.baseMagicNumber = this.magicNumber = MULTIPLIER_AMT;
        this.exhaust = true;
    }

    private static int countCards() {
        return (int) (AbstractDungeon.player.discardPile.group.stream().filter(DowngradeCard::canDowngrade).count()
                + AbstractDungeon.player.drawPile.group.stream().filter(DowngradeCard::canDowngrade).count()
                + AbstractDungeon.player.hand.group.stream().filter(DowngradeCard::canDowngrade).count());
    }


    @Override
    public void applyPowers() {
//        this.baseDamage = ((this.upgraded) ? countCards() - 1 : countCards()) * this.magicNumber;
        this.baseDamage = countCards() * this.magicNumber;
        super.applyPowers();

        String extendString = EXTENDED_DESCRIPTION[0];
        this.rawDescription = DESCRIPTION + extendString;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DowngradeEntireDeckAction(p));

        ElementsCounter.resetElements();

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new LastStandPower(p, this.damage, TURNS)));

//        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
//        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
//        AbstractDungeon.actionManager.addToBottom(
//                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE)
//        );

    }

    public AbstractCard makeCopy() {
        return new LastStand();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MULTIPLIER_AMT);
        }
    }
}
