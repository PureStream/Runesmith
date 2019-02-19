package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class RuneHurl extends CustomCard {
    public static final String ID = "Runesmith:RuneHurl";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/RuneHurl.png"; //need some img
    private static final int COST = 0;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int DRAW_AMT = 1;

    public RuneHurl() {
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
//        this.baseMagicNumber = this.magicNumber = DRAW_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.orbs.size() == 0) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMT));
            return;
        }

        RuneOrb r = RuneOrb.getFirstRune(p);
        if (r == null){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW_AMT));
            return;
        }

        AbstractDungeon.actionManager.addToTop(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );

        AbstractDungeon.actionManager.addToBottom(
                new BreakRuneAction(r)
        );

    }

    public AbstractCard makeCopy() {
        return new RuneHurl();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}