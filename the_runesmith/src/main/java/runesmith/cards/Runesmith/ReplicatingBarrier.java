package runesmith.cards.Runesmith;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.ProtectioRune;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.ReplicatingBarrierPower;

public class ReplicatingBarrier extends AbstractRunicCard {
    public static final String ID = "Runesmith:ReplicatingBarrier";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/ReplicatingBarrier.png"; //<-------------- need some img
    private static final int COST = 1;
    private static final int POTENCY_AMT = 2;
    private static final int UPG_POTENCY_AMT = 1;
    private static final int TERRA_AMT = 2;

    public ReplicatingBarrier() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.POWER,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.SELF
        );
//        this.overchargePot = ProtectioRune.getOverchargeAmt();
        this.elementCost = new int[]{0,TERRA_AMT,0};
        this.renderCraftable = false;
        this.basePotency = this.potency = POTENCY_AMT;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
//        boolean prevCharge = this.isOvercharge;
//        this.isOvercharge = this.potency > this.overchargePot;
//        if(prevCharge!=this.isOvercharge){
//            initializeDescription();
//        }
        checkElements(0, TERRA_AMT, 0, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (checkElements(0, TERRA_AMT, 0)) {
            AbstractDungeon.actionManager.addToBottom(
                    new RuneChannelAction(
                            new ProtectioRune(this.potency)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new ReplicatingBarrierPower(p, this.potency)));
    }

    public AbstractCard makeCopy() {
        return new ReplicatingBarrier();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradePotency(UPG_POTENCY_AMT);
        }
    }
}
