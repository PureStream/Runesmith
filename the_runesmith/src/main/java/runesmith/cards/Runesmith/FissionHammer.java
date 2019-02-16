package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.BreakRuneAction;
import runesmith.actions.BreakWithoutRemovingRuneAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.HAMMER;

public class FissionHammer extends CustomCard {
    public static final String ID = "Runesmith:FissionHammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/FissionHammer.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int ATTACK_DMG = 5;
    private static final int UPGRADE_PLUS_DMG = 2;

    public FissionHammer() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.ENEMY
        );
        this.baseDamage = this.damage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.tags.add(HAMMER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );
        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(
                        p,
                        this.multiDamage, this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.FIRE
                )
        );

        if (p.orbs.size() == 0) return;

        RuneOrb r = (RuneOrb) p.orbs.stream()
                .filter(o -> o instanceof RuneOrb)
                .findFirst().orElse(null);
        if (r == null) return;

        AbstractDungeon.actionManager.addToBottom(new BreakWithoutRemovingRuneAction(1, r));
        AbstractDungeon.actionManager.addToBottom(new BreakRuneAction(r));
    }

    public AbstractCard makeCopy() {
        return new FissionHammer();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
