package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.IceColdPower;

import static runesmith.patches.CardTagEnum.CHISEL;

public class FrozenChisel extends CustomCard {
    public static final String ID = "Runesmith:FrozenChisel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/FrozenChisel.png"; //<-------------- need some img
    private static final int COST = 2;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 2;
//    private static final int STR_DOWN_AMT = 1;
    private static final int SLOW_RECOVER = 3;
    private static final int UPGRADE_SLOW_RECOVER = 1;
    private static final int ELEMENT_AMT = 1;

    public FrozenChisel() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = SLOW_RECOVER;
        this.tags.add(CHISEL);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                )
        );

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new SlowPower(m, 0), 0));
//        if (!m.hasPower("Artifact"))
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new IceColdPower(m, this.magicNumber), this.magicNumber));

//        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, -STR_DOWN_AMT), -STR_DOWN_AMT));

        AbstractDungeon.actionManager.addToBottom(new ApplyElementsPowerAction(p, p, 0, ELEMENT_AMT, ELEMENT_AMT));
    }

    public AbstractCard makeCopy() {
        return new FrozenChisel();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_SLOW_RECOVER);
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
