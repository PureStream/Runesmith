package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class Superposition extends CustomCard implements BreakCard {
    public static final String ID = "Runesmith:Superposition";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Superposition.png"; //<-------------- need some img
    private static final int COST = 3;
    private static final int DMG_AMT = 26;
    private static final int UPGRADE_DMG_AMT = 6;
    private static final int BLOCK_AMT = 13;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public Superposition() {
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
        this.baseDamage = this.damage = DMG_AMT;
        this.baseBlock = this.block = BLOCK_AMT;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );

        RuneOrb r = RuneOrb.getFirstRune(p);
        if (r != null) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
            AbstractDungeon.actionManager.addToBottom(new BreakRuneAction(r));
        }
    }

    public AbstractCard makeCopy() {
        return new Superposition();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DMG_AMT);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }

    @Override
    public int showBreakValueAt() {
        return 0;
    }

    @Override
    public boolean showAllBreakValues() {
        return false;
    }
}