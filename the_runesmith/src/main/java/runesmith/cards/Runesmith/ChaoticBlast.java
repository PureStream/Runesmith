package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class ChaoticBlast extends AbstractRunicCard {
    public static final String ID = "Runesmith:ChaoticBlast";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/ChaoticBlast.png"; //<-------------- need some img
    private static final int COST = 2;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int ELEMENT_AMT = 1;
    private static final int ELEMENT_UPG_AMT = 1;

    public ChaoticBlast() {
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
        this.baseMagicNumber = this.magicNumber = ELEMENT_AMT;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        checkElements(this.magicNumber, this.magicNumber, this.magicNumber, true, false, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));

        int playerPot = 0;
        if (p.hasPower("Runesmith:PotentialPower"))
            playerPot = p.getPower("Runesmith:PotentialPower").amount;

        if (!this.upgraded) {
            if (checkElements(this.magicNumber, this.magicNumber, this.magicNumber, false, false, true))
                AbstractDungeon.actionManager.addToBottom(new RuneChannelAction(RuneOrb.getRandomRune(true, playerPot)));
        } else {
            if (checkElements(this.magicNumber, this.magicNumber, this.magicNumber, false, false, true)) {
                AbstractDungeon.actionManager.addToBottom(new RuneChannelAction(RuneOrb.getRandomRune(true, playerPot)));
                AbstractDungeon.actionManager.addToBottom(new RuneChannelAction(RuneOrb.getRandomRune(true, playerPot)));
            }
        }

    }

    public AbstractCard makeCopy() {
        return new ChaoticBlast();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(ELEMENT_UPG_AMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
