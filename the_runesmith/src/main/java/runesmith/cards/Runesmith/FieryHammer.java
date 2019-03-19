package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.RS_HAMMER;

public class FieryHammer extends CustomCard {
    public static final String ID = "Runesmith:FieryHammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/FieryHammer.png"; //<-------------- need some img
    private static final int COST = 2;
    private static final int ATTACK_DMG = 10;

    public FieryHammer() {
        this(0);
    }

    public FieryHammer(int upgrades) {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CardTarget.ALL_ENEMY
        );
        this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.tags.add(RS_HAMMER);
    }

    public AbstractCard makeCopy() {
        return new FieryHammer(this.timesUpgraded);
    }

    @Override
    public void upgrade() {
        upgradeDamage(3 + this.timesUpgraded);
        this.timesUpgraded += 1;
        this.upgraded = true;
        this.name = (NAME + "+" + this.timesUpgraded);
        initializeTitle();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        p,
                        this.multiDamage, this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
    }

    @Override
    public boolean canUpgrade() {
        return true;
    }

}
