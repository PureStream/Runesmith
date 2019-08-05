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
import runesmith.powers.PotentialPower;

public class ChaoticBlast extends AbstractRunicCard {
    public static final String ID = "Runesmith:ChaoticBlast";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/ChaoticBlast.png"; //<-------------- need some img
    private static final int COST = 2;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int ELEMENT_AND_RUNE_AMT = 1;
    private static final int ELEMENT_AND_RUNE_UPG_AMT = 1;

    private static final String POTENTIAL_ID = PotentialPower.POWER_ID;

    public ChaoticBlast() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION+EXTENDED_DESCRIPTION[0],
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CardTarget.ALL_ENEMY
        );
        this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.baseMagicNumber = this.magicNumber = ELEMENT_AND_RUNE_AMT;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        checkElements(this.magicNumber, this.magicNumber, this.magicNumber, true, false);
        this.rawDescription = DESCRIPTION;
        if (this.magicNumber > 1)
            this.rawDescription += EXTENDED_DESCRIPTION[1];
        else
            this.rawDescription += EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new com.megacrit.cardcrawl.vfx.combat.MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.NONE));

        int playerPot = (p.hasPower(POTENTIAL_ID)) ? p.getPower(POTENTIAL_ID).amount : 0;
        if (checkElements(this.magicNumber, this.magicNumber, this.magicNumber, false, false))
            for (int i=0; i<this.magicNumber; i++)
                AbstractDungeon.actionManager.addToBottom(new RuneChannelAction(RuneOrb.getRandomRune(AbstractDungeon.cardRandomRng, playerPot)));
    }

    public AbstractCard makeCopy() {
        return new ChaoticBlast();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(ELEMENT_AND_RUNE_UPG_AMT);
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }
}
