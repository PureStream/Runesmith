//package runesmith.cards.deprecated;
//
//import basemod.abstracts.CustomCard;
//import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
//import com.megacrit.cardcrawl.actions.common.DamageAction;
//import com.megacrit.cardcrawl.actions.common.GainBlockAction;
//import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.localization.CardStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import runesmith.actions.ApplyElementsPowerAction;
//
//public class TimeTravel_old extends CustomCard {
//
//    public static final String ID = "Runesmith:TimeTravel_old";
//    public static final String IMG_PATH = "images/cards/TimeTravel.png";
//    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
//    public static final String NAME = cardStrings.NAME;
//    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
//    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
//
//    private static final int COST = 2;
//    private static final int UPG_COST = 1;
//
//    private int savedHealth, savedBlock, savedIgnis, savedTerra, savedAqua;
//
//    public TimeTravel_old() {
//        this(0, 0, 0, 0, 0);
//    }
//
//    public TimeTravel_old(int health, int block, int ignis, int terra, int aqua) {
//        super(
//                ID,
//                NAME,
//                IMG_PATH,
//                COST,
//                DESCRIPTION,
//                AbstractCard.CardType.SKILL,
//                AbstractCard.CardColor.COLORLESS,
//                AbstractCard.CardRarity.SPECIAL,
//                AbstractCard.CardTarget.SELF
//        );
//        savedHealth = health;
//        savedBlock = block;
//        savedIgnis = ignis;
//        savedTerra = terra;
//        savedAqua = aqua;
//        this.purgeOnUse = true;
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        int curHealth = p.currentHealth;
//        int curBlock = p.currentBlock;
//        int curIgnis = (p.hasPower("Runesmith:IgnisPower") ? p.getPower("Runesmith:IgnisPower").amount : 0);
//        int curTerra = (p.hasPower("Runesmith:TerraPower") ? p.getPower("Runesmith:TerraPower").amount : 0);
//        int curAqua = (p.hasPower("Runesmith:AquaPower") ? p.getPower("Runesmith:AquaPower").amount : 0);
//
//        if (curHealth < savedHealth)
//            p.heal(savedHealth - curHealth);
//        else if (savedHealth < curHealth)
//            AbstractDungeon.actionManager.addToBottom(
//                    new DamageAction(
//                            p,
//                            new DamageInfo(p, curHealth - savedHealth, DamageType.HP_LOSS),
//                            AttackEffect.NONE
//                    )
//            );
//
//
//        if (curBlock < savedBlock)
//            AbstractDungeon.actionManager.addToBottom(
//                    new GainBlockAction(p, p, savedBlock - curBlock)
//            );
//        else if (savedBlock < curBlock)
//            p.loseBlock(curBlock - savedBlock);
//
//        if (curIgnis < savedIgnis)
//            AbstractDungeon.actionManager.addToBottom(
//                    new ApplyElementsPowerAction(p, p, savedIgnis - curIgnis, 0, 0));
//        else if (savedIgnis < curIgnis)
//            AbstractDungeon.actionManager.addToBottom(
//                    new ReducePowerAction(p, p, "Runesmith:IgnisPower", curIgnis - savedIgnis));
//
//        if (curTerra < savedTerra)
//            AbstractDungeon.actionManager.addToBottom(
//                    new ApplyElementsPowerAction(p, p, savedTerra - curTerra, 0, 0));
//        else if (savedTerra < curTerra)
//            AbstractDungeon.actionManager.addToBottom(
//                    new ReducePowerAction(p, p, "Runesmith:TerraPower", curTerra - savedTerra));
//
//        if (curAqua < savedAqua)
//            AbstractDungeon.actionManager.addToBottom(
//                    new ApplyElementsPowerAction(p, p, savedAqua - curAqua, 0, 0));
//        else if (savedAqua < curAqua)
//            AbstractDungeon.actionManager.addToBottom(
//                    new ReducePowerAction(p, p, "Runesmith:AquaPower", curAqua - savedAqua));
//    }
//
//    public AbstractCard makeCopy() {
//        return new TimeTravel_old(savedHealth, savedBlock, savedIgnis, savedTerra, savedAqua);
//    }
//
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            upgradeBaseCost(UPG_COST);
//        }
//    }
//
//}
