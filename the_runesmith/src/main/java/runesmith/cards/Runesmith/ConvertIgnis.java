package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.ApplyElementsAction;

public class ConvertIgnis extends CustomCard {
    public static final String ID = "Runesmith:ConvertIgnis";
    public static final String IMG_PATH = "images/cards/ConvertIgnis.png"; //<-------- Image needed
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -2;
    private static final int ATTACK_DMG = 6;

    public ConvertIgnis() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCard.CardColor.COLORLESS,
                AbstractCard.CardRarity.SPECIAL,
                CardTarget.ENEMY
        );
        this.baseDamage = ATTACK_DMG;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );
        if (p.hasPower("Runesmith:IgnisPower")) {
            int convertAmount;
            convertAmount = p.getPower("Runesmith:IgnisPower").amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Runesmith:IgnisPower"));
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, 0, convertAmount, convertAmount));
        }

    }

    public AbstractCard makeCopy() {
        return new ConvertIgnis();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
        }
    }
}
