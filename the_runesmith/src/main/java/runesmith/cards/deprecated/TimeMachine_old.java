//package runesmith.cards.deprecated;
//
//import basemod.abstracts.CustomCard;
//import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.localization.CardStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import runesmith.patches.AbstractCardEnum;
//
//public class TimeMachine_old extends CustomCard {
//
//    public static final String ID = "Runesmith:TimeMachine_old";
//    public static final String IMG_PATH = "runesmith/images/cards/TimeMachine.png";
//    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
//    public static final String NAME = cardStrings.NAME;
//    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
//    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
//
//    private static final int COST = 3;
//
//    public TimeMachine_old() {
//        super(
//                ID,
//                NAME,
//                IMG_PATH,
//                COST,
//                DESCRIPTION,
//                AbstractCard.CardType.SKILL,
//                AbstractCardEnum.RUNESMITH_BEIGE,
//                AbstractCard.CardRarity.RARE,
//                AbstractCard.CardTarget.SELF
//        );
//        this.exhaust = true;
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        AbstractCard timeTravel = new TimeTravel_old(p.currentHealth, p.currentBlock,
//                (p.hasPower("Runesmith:IgnisPower") ? p.getPower("Runesmith:IgnisPower").amount : 0), (p.hasPower("Runesmith:TerraPower") ? p.getPower("Runesmith:TerraPower").amount : 0),
//                (p.hasPower("Runesmith:AquaPower") ? p.getPower("Runesmith:AquaPower").amount : 0));
//        if (upgraded)
//            timeTravel.upgrade();
//        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(timeTravel));
//    }
//
//    public AbstractCard makeCopy() {
//        return new TimeMachine_old();
//    }
//
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            this.isInnate = true;
//            this.rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
//        }
//    }
//
//}
