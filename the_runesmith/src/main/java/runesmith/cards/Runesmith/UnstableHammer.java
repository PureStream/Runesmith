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
import runesmith.actions.ApplyElementsAction;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.DudRune;
import runesmith.patches.AbstractCardEnum;

import static runesmith.patches.CardTagEnum.HAMMER;

public class UnstableHammer extends CustomCard {
    public static final String ID = "Runesmith:UnstableHammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/UnstableHammer.png"; //<-------------- need some img
    private static final int COST = 0;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int ELEMENT_AMT = 1;

    public UnstableHammer() {
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
                new RuneChannelAction(
                        new DudRune()));
        int selectedPower = AbstractDungeon.cardRandomRng.random(2);
        if (selectedPower == 0)
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, ELEMENT_AMT, 0, 0));
        else if (selectedPower == 1)
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, 0, ELEMENT_AMT, 0));
        else
            AbstractDungeon.actionManager.addToBottom(new ApplyElementsAction(p, p, 0, 0, ELEMENT_AMT));

//		if (random == 0) 
//			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new IgnisPower(p, ELEMENT_AMT), ELEMENT_AMT));
//		else if (random == 1)
//			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new TerraPower(p, ELEMENT_AMT), ELEMENT_AMT));
//		else
//			AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new AquaPower(p, ELEMENT_AMT), ELEMENT_AMT));
    }

    public AbstractCard makeCopy() {
        return new UnstableHammer();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}