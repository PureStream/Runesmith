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
import runesmith.actions.ApplyElementsAction;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.PotentialDownPower;
import runesmith.powers.PotentialPower;

import static runesmith.patches.CardTagEnum.CHISEL;

public class PerfectChisel extends CustomCard {
    public static final String ID = "Runesmith:PerfectChisel";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/PerfectChisel.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int ELEMENT_AMT = 1;
    private static final int POT_AMT = 1;
    private static final int UPG_POT_AMT = 1;

    public PerfectChisel() {
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
        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = POT_AMT;
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
        AbstractDungeon.actionManager.addToBottom(
                new ApplyElementsAction(p, p, ELEMENT_AMT, ELEMENT_AMT, ELEMENT_AMT));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PotentialPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new PotentialDownPower(p, this.magicNumber), this.magicNumber));
    }

    public AbstractCard makeCopy() {
        return new PerfectChisel();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPG_POT_AMT);
        }
    }
}
