package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.cards.FortifyAction;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.RS_HAMMER;

public class ShiftingHammer extends CustomCard {
    public static final String ID = "Runesmith:ShiftingHammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/ShiftingHammer.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADE_PLUS_DMG = 2;

    public ShiftingHammer() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.COMMON,
                CardTarget.ENEMY
        );
        this.baseDamage = ATTACK_DMG;
        this.tags.add(RS_HAMMER);
    }

    public AbstractCard makeCopy() {
        return new ShiftingHammer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                ));
        this.addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                ));

        AbstractDungeon.actionManager.addToBottom(
                new FortifyAction(false)
        );
    }
}
